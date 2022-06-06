package com.example.areaderiscoapp.TSV_java;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadTSVAsClassChamado {

    //Funcao para ler o arquivo
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<Chamado> readFileAsList(String path)throws Exception
    {
        File file = new File(path);
        Scanner sc = new Scanner(file);
        sc.nextLine();
        ArrayList<Chamado> lista = new ArrayList<Chamado>();
        while(sc.hasNext()){
            String[] str = sc.nextLine().split("\t");

            //filtros aqui\/
            if(str[1].equals("2022") && str[2].equals("05-maio")
                    && str[6].toLowerCase().contains("alaga")
             ){

                Chamado chamada = new Chamado(Integer.parseInt(str[0])
                        ,Integer.parseInt(str[1]),str[2],str[3]
                        ,str[6],str[8],str[9],str[10]);

                lista.add(chamada);
            }
        }
        return lista;
    }

}
