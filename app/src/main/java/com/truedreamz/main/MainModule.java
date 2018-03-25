package com.truedreamz.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by JAYAPRAKASH on 20-03-2018.
 */
@Module
public class MainModule {
    @Provides
    @Singleton
    public Gson provideGson() {
        Gson gson = new GsonBuilder().create();
        return gson;
    }

    @Provides
    public CompositeDisposable provideCompositeSubscription() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        return compositeDisposable;
    }
}
