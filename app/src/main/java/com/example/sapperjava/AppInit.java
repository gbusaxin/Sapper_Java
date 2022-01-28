package com.example.sapperjava;

import android.app.Application;

import com.onesignal.OneSignal;

public class AppInit extends Application {

    private static final String ONESIGNAL_APP_ID = "3aea2af5-32ae-4489-9585-ec1c64d38879";

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE,OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
