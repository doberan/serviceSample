package com.doberan.study.servicesample;

import android.app.Application;
import android.widget.Toast;

/*
*/
public class ApplicationController extends Application {
    private static ApplicationController sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(),"Application Context onCreate",Toast.LENGTH_SHORT).show();
        sInstance = this;
    }

    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }
}
