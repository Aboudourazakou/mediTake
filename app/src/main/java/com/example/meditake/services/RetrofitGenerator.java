package com.example.meditake.services;

import com.example.meditake.utils.NullOnEmptyConverterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGenerator {

    private static Retrofit instance;

    private RetrofitGenerator(){

    }

    synchronized
    public static Retrofit getRetrofit(){
        if (instance==null){
            instance  = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.8:8080/api/")
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}
