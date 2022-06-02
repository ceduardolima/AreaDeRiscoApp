package com.example.areaderiscoapp.json_java;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;

public class JsonReader {
    File arquivo;
    String s;
    String data;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public JsonReader(File externalFilesDir, String s) throws Exception {
        this.arquivo=externalFilesDir;
        String path = externalFilesDir.getPath()+"/"+s;
        this.s=s;
        Gson gson = new Gson(); // conversor
        FileReader fr= null;
        fr = new FileReader(path);
        String data = ReadTextAsString.readFileAsString(path);
        this.data=data;
    }
}
