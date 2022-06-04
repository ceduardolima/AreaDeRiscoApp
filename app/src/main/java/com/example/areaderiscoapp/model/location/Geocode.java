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

    private String URL;
    private LatLng latLng;
    private String street;
    private String neighborhood;
    private String number;
    private Context context;

    public Geocode(Context context) {
        this.context = context;
    }

    public void request() {
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
                    Toast.makeText(this.context, "Erro", Toast.LENGTH_SHORT).show();
                }
        );
        
        requestQueue.add(request);
    }
}
