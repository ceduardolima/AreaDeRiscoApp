package com.example.areaderiscoapp.TSV_java;

import android.os.Build;

import androidx.annotation.RequiresApi;


import java.io.File;
import java.util.ArrayList;

public class TSVReader {
    //o arquivo proprio dito\/
    File arquivo;

    //caminho para o arquivo\/
    String s;

    // dados salvos \/
    ArrayList<Chamado> data;


    //construtor cria,lê e salva a base de dados filtrada
    @RequiresApi(api = Build.VERSION_CODES.O)
    public TSVReader(File externalFilesDir, String s) throws Exception {
        this.arquivo=externalFilesDir;
        String path = externalFilesDir.getPath()+"/"+s;
        this.s=s;
        this.data= ReadTSVAsClassChamado.readFileAsList(path);
    }
    public ArrayList<Chamado> getData() {
        return data;
    }

    public void setData(ArrayList<Chamado> data) {
        this.data = data;
    }
}
