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
import java.io.Serializable;
import java.util.ArrayList;

public class TelaSplash extends AppCompatActivity {
    Long downloadId1;
    Long downloadId2;

    /*lista de chamadas filtrada
    passada pra prox activity com a chave "data"
    so visivel após reader();
     */
    private ArrayList<Chamado> dataChamados;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        //registra quando o download termina
        registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));


        if(hasExternalStoragePrivateFile("CHAMADOS")==false) {
            initProcess();
        } else {
            reader();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    indoparaproxtela();
                }
            }, 1000);
        }
    }

    //função para ir pra proxima tela(obvio)
    private void indoparaproxtela(){

        Intent intent = new Intent(TelaSplash.this, MainActivity.class);
        //preciso passar o arraylist dessa classe para a outra
        // precisa de uma classe adapter para Object

        intent.putExtra("data",(Serializable)dataChamados);
        startActivity(intent);

        //ses esqueceram do finish pra destruir a telasplash
        finish();
    }

    //inicia processo de baixar o arquivo
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initProcess(){
            beginDownload1();
    }

    // função para ler o arquivo, chama classes do package TSV_java
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

    //função para baixar o arquivo
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

    //Quando o download termina essa "função" é chamada
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(downloadId1==id){
                reader();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        indoparaproxtela();
                    }
                }, 0);
            }
        }
    };

    //Pergunta se o arquivo existe
    boolean hasExternalStoragePrivateFile(String s) {
        //pergunta existencia de arquivo com dado nome s
        File file = new File(getExternalFilesDir(null), s);
        return file.exists();
    }
}