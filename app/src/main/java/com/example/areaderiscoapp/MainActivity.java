package com.example.areaderiscoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;


import com.example.areaderiscoapp.model.map.Place;

import com.google.android.gms.maps.SupportMapFragment;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SupportMapFragment mapFragment;
    private ArrayList<Place> places;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }


    private void init(){
        /* Inicia os componentes da activity */

        // inicializando componentes do mapa
        places = new ArrayList<>();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
    }
}