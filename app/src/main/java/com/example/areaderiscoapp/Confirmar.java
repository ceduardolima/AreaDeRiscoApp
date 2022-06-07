package com.example.areaderiscoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Confirmar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar);
        //Toast.makeText(this, "executou o onCreate() de ResultadoActivity", Toast.LENGTH_SHORT).show();

        TextView DescZona = (TextView) findViewById(R.id.DescZona);
        Intent intent = getIntent();
        String desc = intent.getStringExtra("Descricao");
        DescZona.setText(desc);

        final TextView textViewLogradouroValor = findViewById(R.id.textViewLogradouroValor);
        final TextView textViewBairroValor = findViewById(R.id.textViewBairroValor);
        final TextView textViewCidadeValor = findViewById(R.id.textViewCidadeValor);
        final TextView textViewUFValor = findViewById(R.id.textViewUFValor);

        Resultado resultado = null;
        if (getIntent()!=null && getIntent().getExtras()!=null) {
            try {
                resultado = (Resultado) getIntent().getExtras().getSerializable("resultado");
            }catch (Exception e){
            }
        }

        if (resultado!=null) {
            textViewLogradouroValor.setText(resultado.getLogradouro());
            textViewBairroValor.setText(resultado.getBairro());
            textViewCidadeValor.setText(resultado.getLocalidade());
            textViewUFValor.setText(resultado.getUf());
        }

        final Button button = findViewById(R.id.buttonVoltar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ResultadoActivity.this, MainActivity.class);
                //startActivity(intent);
                onBackPressed();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "executou o onStart() de ResultadoActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "executou o onResume() de ResultadoActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this, "executou o onPause() de ResultadoActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "executou o onStop() de ResultadoActivity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "executou o onDestroy() de ResultadoActivity", Toast.LENGTH_SHORT).show();
    }
}