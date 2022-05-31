package com.example.areaderiscoapp.model.map;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.areaderiscoapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class MapManager {
    /* Faz toda a inicialização do mapa no aplicativo */

    private SupportMapFragment mapFragment;
    private ArrayList<Place> places;
    private Context context;

    public MapManager(Context context, SupportMapFragment mapFragment, ArrayList<Place> places){
        this.mapFragment = mapFragment;
        this.context = context;
        this.places = places;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createMap(){
        mapFragment.getMapAsync(v -> {
            addMarkers(v);
        }
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addMarkers(GoogleMap googleMap) {
        /* Cria os marcadores dentro do mapa */
        places.forEach( place -> googleMap.addMarker(
                new MarkerOptions()
                        .title(place.getProblem())
                        .snippet(place.getAddress())
                        .position(place.getPosition())
                        .icon(
                                BitmapHelper.vectorToBitMap(
                                        context,
                                        R.drawable.ic_danger,
                                        ContextCompat.getColor(context, R.color.alert))
                        )
        ));
    }

    public void addPlace(Place place) {
        /* Adiciona um novo local no places */
        this.places.add(place);
    }
}
