package com.truedreamz.network;

import org.w3c.dom.ProcessingInstruction;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JAYAPRAKASH-FGTS on 15-03-2018.
 */

public class RetrofitAPIClient {
    public static final String BASE_URL="http://jsonplaceholder.typicode.com/";
    private static Retrofit mRetrofit=null;

    public static Retrofit getRetroClient() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }


}