package com.example.areaderiscoapp.model.map;

import com.google.android.gms.maps.model.LatLng;

public class Place {
    /* Classe usada para armazenar os dados de cada local */

    String problem; // Armazena o tipo do problema
    LatLng position; // Armazena a latitude e longitude
    String address; // Armazena o endereço
    double raiting;

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRaiting() {
        return raiting;
    }

    public void setRaiting(double raiting) {
        this.raiting = raiting;
    }

    public Place(String problem, LatLng position, String address, double raiting) {
        this.problem = problem;
        this.position = position;
        this.address = address;
        this.raiting = raiting;
    }
}