package com.example.areaderiscoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import com.example.areaderiscoapp.model.map.Place;

import com.google.android.gms.maps.SupportMapFragment;


import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private ArrayList<Place> places;
    Long downloadId1;
    Long downloadId2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(hasExternalStoragePrivateFile("CHAMADOS.json")==true) {
            deleteExternalStoragePrivateFile("CHAMADOS.json");
        }
        beginDownload1();
        if(hasExternalStoragePrivateFile("TIPOS.json")==true) {
            deleteExternalStoragePrivateFile("TIPOS.json");
        }
        beginDownload2();
    }


    private void init(){
        /* Inicia os componentes da activity */
        // inicializando componentes do mapa
        places = new ArrayList<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void beginDownload1(){
        //metodo para baixar json (SEDEC Chamados)
        File file = new File(getExternalFilesDir(null),"CHAMADOS.json");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("" +
                "http://dados.recife.pe.gov.br/datastore/dump/5eaed1e8-aa7f-48d7-9512-638f80874870?format=json"))
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
        //metodo para baixar json (Tipos de ocorrencia)
        File file = new File(getExternalFilesDir(null),"TIPOS.json");
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse("" +
                "http://dados.recife.pe.gov.br/datastore/dump/7a22d871-250e-419a-9b5a-1cab19db7be5?format=json"))
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