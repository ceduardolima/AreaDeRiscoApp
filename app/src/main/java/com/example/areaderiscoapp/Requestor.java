package com.example.areaderiscoapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Requestor {

    public static void getCEP(Activity activity, EditText editText, EditText editText2, Button button){
        button.setEnabled(false);
        RequestQueue queue = Volley.newRequestQueue(activity);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://viacep.com.br/ws/" + editText.getText().toString() + "/json/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        button.setEnabled(true);
                        System.out.println(response);
                        Resultado resultado = EntityWarpper.fromJson(response, Resultado.class);
                        EnderecoController enderecoDB = new EnderecoController(activity);
                        resultado = enderecoDB.save(resultado);
                        if (resultado.getId()!=null){
                            Toast.makeText(activity, "Cep obtido e inserido no SQLite com sucesso!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(activity, "Cep obtido com sucesso (mas não inserido no SQLite)!", Toast.LENGTH_LONG).show();
                        }
                        Intent intent = new Intent(activity, Confirmar.class);
                        intent.putExtra("resultado", resultado);
                        intent.putExtra("Descricao", editText2.getText().toString());
                        activity.startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                button.setEnabled(true);
                Toast.makeText(activity, "Erro ao tentar obter o CEP => " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

}
