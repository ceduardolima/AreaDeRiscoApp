package com.example.areaderiscoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;

import com.example.areaderiscoapp.TSV_java.Chamado;
import com.example.areaderiscoapp.TSV_java.TSVReader;

import java.io.File;
import java.util.ArrayList;

public class TelaSplash extends AppCompatActivity {
    Long downloadId1;
    Long downloadId2;
    private ArrayList<Chamado> dataChamados;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);
        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if(hasExternalStoragePrivateFile("CHAMADOS")==false) {
            initProcess();
        } else {
            reader();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(TelaSplash.this, MainActivity.class);
                    //preciso passar o arraylist dessa classe para a outra
                    startActivity(intent);
                    //ses esqueceram do finish pra destruir a telasplash
                    finish();
                }
            }, 1000);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initProcess(){
        if(hasExternalStoragePrivateFile("CHAMADOS")) {
            //   deleteExternalStoragePrivateFile("CHAMADOS");
        } else {
            beginDownload1();
        }

    }
    private void reader(){
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                TSVReader reader = new TSVReader(getExternalFilesDir(null),"CHAMADOS");
                this.dataChamados=reader.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void beginDownload1(){
        //metodo para baixar TSV (SEDEC Chamados)
        File file = new File(getExternalFilesDir(null),"CHAMADOS");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("" +
                "http://dados.recife.pe.gov.br/datastore/dump/5eaed1e8-aa7f-48d7-9512-638f80874870?bom=True&format=tsv"))
                .setTitle("CHAMADOS")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId1=downloadManager.enqueue(request);
    }
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(downloadId1==id){
                reader();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(TelaSplash.this, MainActivity.class);
                        //preciso passar o arraylist dessa classe para a outra
                        startActivity(intent);
                        //ses esqueceram do finish pra destruir a telasplash
                        finish();
                    }
                }, 1000);
            }
        }
    };
    void deleteExternalStoragePrivateFile(String s) {
        // deletador dado o nome s do arquivo a ser deletador
        File file = new File(getExternalFilesDir(null), s);
        file.delete();
    }

    boolean hasExternalStoragePrivateFile(String s) {
        //pergunta existencia de arquivo com dado nome s
        File file = new File(getExternalFilesDir(null), s);
        return file.exists();
    }
}