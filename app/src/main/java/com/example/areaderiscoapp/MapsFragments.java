package com.example.areaderiscoapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.areaderiscoapp.model.map.MapManager;
import com.example.areaderiscoapp.model.map.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MapsFragments extends Fragment {

    private ArrayList<Place> places;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_fragment);
        places = new ArrayList<>();
        initPlaces();
        MapManager map = new MapManager(view.getContext(), supportMapFragment, places);
        map.createMap();
        return view;
    }

    void initPlaces(){

        places.add(new Place(
                "Alagamento",
                new LatLng(-8.0052224,-35.0257152),
                "Atacadão"
        ));

        places.add(new Place(
                "Alagamento",
                new LatLng(-8.0372836,-34.966506),
                "Hospital Casa Forte"
        ));

        places.add(new Place(
                "Alagamento",
                new LatLng(-8.02925214459181, -34.94015601493397),
                "Detran"
        ));

        places.add(new Place(
                "Alagamento",
                new LatLng(-8.042000336237876, -34.91741088434958),
                "Parque Santana"
        ));

    }
}
