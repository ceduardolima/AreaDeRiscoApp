package com.example.areaderiscoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BuilderDB extends SQLiteOpenHelper {

    public BuilderDB(Context context){
        super(context, "exemplo_app.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE enderecos" +
                " (id integer primary key autoincrement not null," +
                " cep text not null," +
                " logradouro text not null," +
                " bairro text not null," +
                " localidade text not null," +
                " uf text not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS enderecos");
        onCreate(db);
    }
}
