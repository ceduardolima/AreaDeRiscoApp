package com.example.areaderiscoapp;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
import android.widget.Toast;

import com.example.areaderiscoapp.TSV_java.Chamado;
import com.example.areaderiscoapp.TSV_java.TSVReader;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.N)
public class TelaSplash extends AppCompatActivity {
    private Long downloadId1;
    DownloadManager manager;
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

       if(!hasExternalStoragePrivateFile("CHAMADOS")) {
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
            //beginDownload1();
       downloadId1= downloadFile(Uri.parse("http://dados.recife.pe.gov.br/datastore/dump/5eaed1e8-aa7f-48d7-9512-638f80874870?bom=True&format=tsv")
                ,getExternalFilesDir(null).toString(), "CHAMADOS");
    }

    // função para ler o arquivo, chama classes do package TSV_java
    private void reader(){
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                TSVReader reader = new TSVReader(
                        getExternalCacheDir()
                        ,"CHAMADOS");
                this.dataChamados=reader.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //função para baixar o arquivo
    @RequiresApi(api = Build.VERSION_CODES.N)
    private long downloadFile(Uri uri, String fileStorageDestinationUri, String fileName) {

        long downloadReference = 0;

        manager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        try {
            DownloadManager.Request request = new DownloadManager.Request(uri);

            //Setting title of request
            request.setTitle(fileName);

            //Setting description of request
            request.setDescription("Your file is downloading");

            //set notification when download completed
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            //Set the local destination for the downloaded file to a path within the application's external files directory
            request.setDestinationInExternalFilesDir(getApplicationContext(),"", fileName);

            request.allowScanningByMediaScanner();

            //Enqueue download and save the referenceId
            downloadReference = manager.enqueue(request);
        } catch (IllegalArgumentException e) {
            Toast.makeText(TelaSplash.this, "Download link is broken or not availale for download",Toast.LENGTH_SHORT)
                    .show();
            Log.e(TAG, "Line no: 455,Method: downloadFile: Download link is broken");

        }
        return downloadReference;
    }
    /*
    private void beginDownload1(){
        //metodo para baixar TSV (SEDEC Chamados)
        File file = new File(getExternalCacheDir(),"CHAMADOS");
        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("http://dados.recife.pe.gov.br/datastore/dump/5eaed1e8-aa7f-48d7-9512-638f80874870?bom=True&format=tsv");

        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("CHAMADOS");
        request.setDestinationUri(Uri.fromFile(file));
        downloadId1=manager.enqueue(request);
    }
*/
    //Quando o download termina essa "função" é chamada
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            long id=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
            if(downloadId1==id){
                Toast.makeText(TelaSplash.this,"download completed",Toast.LENGTH_SHORT).show();
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
        File file = new File(
               // getExternalCacheDir(),s
                getExternalFilesDir(null), s
        );
        return file.exists();
    }
}