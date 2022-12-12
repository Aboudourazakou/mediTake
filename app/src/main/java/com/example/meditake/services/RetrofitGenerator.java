package com.example.meditake.services;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import com.example.meditake.utils.NullOnEmptyConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGenerator {

    private static Retrofit instance;
    private  static  Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    private RetrofitGenerator(){

    }

    synchronized
    public static Retrofit getRetrofit(){
        if (instance==null){
            instance  = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.9:8080/api/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }
}


