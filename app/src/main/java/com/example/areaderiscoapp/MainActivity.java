package com.example.areaderiscoapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import com.example.areaderiscoapp.TSV_java.Chamado;
import com.example.areaderiscoapp.TSV_java.TSVReader;
import com.example.areaderiscoapp.model.map.Place;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private ArrayList<Place> places;
    private ArrayList<Chamado> dataChamados;
    int REQUEST_LOCATION = 88;
    Long downloadId1;
    Long downloadId2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initProcess();
        setContentView(R.layout.activity_main);

        getLocation();

        Button button = findViewById(R.id.location);
        button.setOnClickListener(v -> {

            //check permission
            if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){

                //when permission is granted
                Location();

            } else {

                //when permission is denied
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 23);
            }
        });

        init();
    }

    private void Location(){

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);

                //do your task here with your location
            } catch (ApiException e) {

                switch (e.getStatusCode())
                {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                            resolvableApiException.startResolutionForResult(MainActivity.this,REQUEST_LOCATION);
                        } catch (IntentSender.SendIntentException sendIntentException) {
                        }
                        break;
                    //when device doesn't have location feature
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }

            }

        });


    }

    private void getLocation() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

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