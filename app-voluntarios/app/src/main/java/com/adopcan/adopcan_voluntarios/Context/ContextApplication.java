package com.adopcan.adopcan_voluntarios.Context;

import android.app.Application;
import android.content.Context;

/**
 * Created by german on 23/8/2017.
 */

public class ContextApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        ContextApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return ContextApplication.context;
    }
}
