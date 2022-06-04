package com.example.areaderiscoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import com.example.areaderiscoapp.TSV_java.Chamado;
import com.example.areaderiscoapp.TSV_java.TSVReader;
import com.example.areaderiscoapp.model.map.Place;

import com.google.android.gms.maps.SupportMapFragment;


import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private ArrayList<Place> places;
    private ArrayList<Chamado> dataChamados;
    Long downloadId1;
    Long downloadId2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.Theme_Splash);
        initProcess();
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        /* Inicia os componentes da activity */
        // inicializando componentes do mapa
        places = new ArrayList<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initProcess(){
        if(hasExternalStoragePrivateFile("CHAMADOS")) {
            //   deleteExternalStoragePrivateFile("CHAMADOS");
        } else {
            beginDownload1();
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
      /*  if(hasExternalStoragePrivateFile("TIPOS")) {
            //  deleteExternalStoragePrivateFile("TIPOS");
        }else {
            //   beginDownload2();
        }
       */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void beginDownload2(){
        //metodo para baixar TSV (Tipos de ocorrencia)
        File file = new File(getExternalFilesDir(null),"TIPOS");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("" +
                "http://dados.recife.pe.gov.br/datastore/dump/7a22d871-250e-419a-9b5a-1cab19db7be5?bom=True&format=tsv"))
                .setTitle("TIPOS")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationUri(Uri.fromFile(file))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true);
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        downloadId2=downloadManager.enqueue(request);
    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}