package com.example.areaderiscoapp.model.map;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.example.areaderiscoapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class MapManager {
    /* Faz toda a inicialização do mapa no aplicativo */

    private SupportMapFragment mapFragment;
    private ArrayList<Place> places;
    private Context context;
    private ArrayList<Place> neighborhood;

    public MapManager(Context context, SupportMapFragment mapFragment, ArrayList<Place> places){
        this.mapFragment = mapFragment;
        this.context = context;
        this.places = places;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createMap(){
        mapFragment.getMapAsync(googleMap -> {
            addMarkers(googleMap);
            setMapInitialLimit(googleMap);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMapInitialLimit(GoogleMap googleMap) {
        ArrayList<Place> buff;

        if(this.neighborhood == null) buff = this.places;
        else buff = this.neighborhood;

        googleMap.setOnMapLoadedCallback(() -> {
            LatLngBounds.Builder bounds = LatLngBounds.builder();
            buff.forEach(place -> bounds.include(place.getPosition()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100));
        });

    }

    public void setNeighborhood(ArrayList<Place> neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void addPlace(Place place) {
        /* Adiciona um novo local no places */
        this.places.add(place);
    }

    public void updateCamera(LatLng latLng) {
        this.mapFragment.getMapAsync(map -> map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15)));
    }

}
