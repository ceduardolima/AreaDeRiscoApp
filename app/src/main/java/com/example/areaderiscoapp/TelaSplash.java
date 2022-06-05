package com.example.areaderiscoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class TelaSplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(TelaSplash.this, MainActivity.class);
                startActivity(intent);
            }
        }, 4000);
    }
}