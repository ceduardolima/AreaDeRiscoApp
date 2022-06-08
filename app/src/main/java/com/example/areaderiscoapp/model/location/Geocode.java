package com.example.areaderiscoapp.model.location;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Geocode {
    /* Classe responsável por obter a geolocalização do endereço dado */

    private String URL = "https://api.geoapify.com/v1/geocode/search?text=";
    private String key = "9cee7b25c20a4357a418a38f81689d72";
    private final String state = "pernambuco";
    private LatLng latLng;

    public void urlBuilder(String city, String street, String houseNumber) {
        /* função criada para construir a url */
        String connector = "%2C%20";
        String bufferState = this.state + connector;
        String bufferCity = city + connector;
        String bufferStreet = urlStreetBuilder(street, houseNumber);
        String urlBuffer = "&lang=pt&limit=1&format=json&filter=countrycode:br&apiKey=";

        this.URL = this.URL +
                bufferState +
                bufferCity +
                bufferStreet +
                urlBuffer +
                this.key;
    }

    private String urlStreetBuilder(String street, String houseNumber) {
        /* Constroi uma parte especifica da url, a parte da 'rua' */
        String[] string_splited = street.split(" ");
        StringBuilder buffer = new StringBuilder();
        String connector = "%20";

        int i;
        for (i = 0; i < string_splited.length; i++) {
            buffer.append(string_splited[i]);
            if(i < (string_splited.length - 1))
                buffer.append(connector);
        }
        if( houseNumber != null) {
            String bufferHouseNumber = "%2C%20" + houseNumber;
            buffer.append(houseNumber);
        }

        return buffer.toString();
    }

    public void request(Context context) {
        /* função responsável por fazer a requisição ao geocode api */

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                this.URL,
                null,

                (Response.Listener<JSONObject>) response -> {
                    try {

                        JSONArray jsonArray = response.getJSONArray("results");
                        // Obtem a lat e long do local dado
                        double lat = jsonArray.getJSONObject(0).getDouble("lat");
                        double lng = jsonArray.getJSONObject(0).getDouble("lon");
                        this.latLng = new LatLng(lat, lng);
                        } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                },
                (Response.ErrorListener) error -> {
                    Toast.makeText(context, "Erro", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(request);
    }

    public LatLng getLatLng() {
        return latLng;
    }
}
