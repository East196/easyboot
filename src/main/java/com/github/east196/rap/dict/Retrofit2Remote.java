package com.github.east196.rap.dict;

import com.github.east196.core.boon.Glue;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Remote {

    private static final String BASE_URL = "http://172.20.105.186:9909/";

    public static OkHttpClient okHttpClient;
    public static Retrofit retrofit;

    public static <T> T getService(Class<T> klass) {
        return retrofit.create(klass);
    }

    static {

        okHttpClient = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Glue.GSON))
                .client(okHttpClient).build();

    }

}