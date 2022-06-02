package com.example.areaderiscoapp.json_java;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadTextAsString {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String readFileAsString(String path)throws Exception
    {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(path)));
        return data;
    }
}
