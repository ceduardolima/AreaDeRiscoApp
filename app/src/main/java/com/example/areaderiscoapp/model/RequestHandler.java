package com.example.areaderiscoapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RequestHandler extends Thread{

    String URL;
    JSONObject requisitosalvo;
    RequestHandler(String s){
        this.URL = s;
    }

@Override
    public void run(){
    try{
        HttpHandler sh = new HttpHandler();
       String jsonStr1 = sh.makeServiceCall(URL);
        JSONObject reader = new JSONObject(jsonStr1);
        requisitosalvo=reader;
    } catch (JSONException e){
        e.printStackTrace();
    }
}
}
