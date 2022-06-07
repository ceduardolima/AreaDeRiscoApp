package com.example.areaderiscoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class EnderecoController {

    private SQLiteDatabase db;
    private final BuilderDB helper;

    public EnderecoController(Context context){
        helper = new BuilderDB(context);
    }

    public Resultado getByCep(String cep){
        Cursor cursor;
        String[] campos =  {"id", "cep", "logradouro", "bairro", "localidade", "uf"};
        db = helper.getReadableDatabase();
        cursor = db.query("enderecos",campos,"cep = '" + cep + "'", null, null, null, null, null);

        Resultado resultado = null;
        if(cursor!=null && cursor.moveToFirst()) {
            resultado = new Resultado();
            resultado.setId(cursor.getLong(0));
            resultado.setCep(cursor.getString(1));
            resultado.setLogradouro(cursor.getString(2));
            resultado.setBairro(cursor.getString(3));
            resultado.setLocalidade(cursor.getString(4));
            resultado.setUf(cursor.getString(5));
        }
        db.close();
        return resultado;
    }

    public Resultado save(Resultado resultado){
        db = helper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("cep", resultado.getCep().replace("-",""));
        valores.put("logradouro", resultado.getLogradouro());
        valores.put("bairro", resultado.getBairro());
        valores.put("localidade", resultado.getLocalidade());
        valores.put("uf", resultado.getUf().toUpperCase());
        long id = db.insert("enderecos", null, valores);
        if (id > 0){
            resultado.setId(id);
        }else{
            resultado.setId(null);
        }
        db.close();
        return resultado;
    }

}
