package com.example.areaderiscoapp;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class Logradouro extends ListActivity {

    private List<Endereco> logradouros = new ArrayList<Endereco>();
    private ArrayAdapter<Endereco> adapter;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        if (getIntent() != null && getIntent().getExtras() != null) {
            try {
                logradouros = (List<Endereco>) getIntent().getExtras().getSerializable("resultado");
            } catch (Exception e) {
            }
        }

        adapter = new ArrayAdapter<Endereco>(this,
                android.R.layout.simple_list_item_1, logradouros);
        setListAdapter(adapter);

        this.getListView().setLongClickable(true);
        this.getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                Endereco item = (Endereco) getListAdapter().getItem(position);
                new AlertDialog.Builder(Logradouro.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Confirmação")
                        .setMessage("Deseja realmente excluir (" + item.toString() + ") ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logradouros.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
                return true;
            }
        });

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Endereco item = (Endereco) getListAdapter().getItem(position);
        Toast.makeText(this, "selecionado: " + item.toString(), Toast.LENGTH_LONG).show();
    }

}