package com.chillbox.app.network.services;

import android.widget.Toast;

import com.chillbox.app.MyApp;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aman1 on 14/12/2017.
 */

public class ServerConnection {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;





    public static RequestInterface getServerConnection(String api) {


        // Response logging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!





        if (!MyApp.isDataConnectionAvailable()){
            Toast.makeText(MyApp.getContext(), "Currently Offline .....", Toast.LENGTH_LONG).show();
        }

        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(MyApp.getContext().getCacheDir(), cacheSize);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(api)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(RequestInterface.class);

    }
}
