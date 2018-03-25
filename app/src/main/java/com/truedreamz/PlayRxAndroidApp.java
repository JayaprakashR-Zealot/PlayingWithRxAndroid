package com.truedreamz;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDexApplication;

import com.truedreamz.main.DaggerIMainComponent;
import com.truedreamz.main.IMainComponent;


public class PlayRxAndroidApp extends MultiDexApplication {
    private IMainComponent mainComponent;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        mainComponent = DaggerIMainComponent.create();
        context = getApplicationContext();
    }

    public IMainComponent getMainComponent() {
        return mainComponent;
    }

    public static Context getContext() {
        return context;
    }

    @VisibleForTesting
    public void setComponent(IMainComponent mainComponent) {
        this.mainComponent = mainComponent;
    }
}
