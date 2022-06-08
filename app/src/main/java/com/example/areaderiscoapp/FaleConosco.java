package com.example.areaderiscoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class FaleConosco extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fale_conosco);

        final EditText editText = findViewById(R.id.editText);
        final EditText editText2 = findViewById(R.id.editText2);
        final Button button = findViewById(R.id.button);

        LinearLayout contato = findViewById(R.id.Contato);

        Button switch1 = findViewById(R.id.switch1);
        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = contato.getVisibility();
                if (visibility == View.GONE) {
                    contato.setVisibility(View.VISIBLE);
                } else if (visibility == View.VISIBLE) {
                    contato.setVisibility(View.GONE);
                }
            }
        });

        //Intent intentTela = new Intent(getApplicationContext(), Confirmar.class);
        //intentTela.putExtra("Descricao", editText2.getText().toString());

        button.setEnabled(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() == 8 && editText.getText().toString().matches("\\d+")){
                    EnderecoController enderecoDB = new EnderecoController(getBaseContext());
                    Resultado resultado = enderecoDB.getByCep(editText.getText().toString());

                    if (resultado==null) {
                        Requestor.getCEP(FaleConosco.this, editText, editText2, button);
                    }else{
                        Toast.makeText(FaleConosco.this, "Solicitação enviada com sucesso. Aguarde autoridade fazer vistoria!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(FaleConosco.this,
                            "CEP inválido!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
