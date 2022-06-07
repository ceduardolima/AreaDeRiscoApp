package com.example.areaderiscoapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class EntityWarpper {

    public static <T extends Serializable> T fromJson(String jsonString, Class<T> beanType) {
        GsonBuilder gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = gsonb.create();
        return gson.fromJson(jsonString, beanType);
    }

    public static <T extends Serializable> List<T> fromJsonArray(String jsonArrayString, Class<T> beanType) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return new Gson().fromJson(jsonArrayString, type);
    }

    public static <T extends Serializable> String toString(T entity) {
        return new Gson().toJson(entity);
    }

    public static <T extends Serializable> String toArray(List<T> list) {
        return new Gson().toJson(list);
    }
}
