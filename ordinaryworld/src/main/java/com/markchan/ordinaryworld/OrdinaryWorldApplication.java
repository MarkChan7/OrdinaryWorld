package com.markchan.ordinaryworld;

import android.app.Application;
import com.blankj.utilcode.utils.Utils;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * Created by Mark on 17/1/19.
 */
public class OrdinaryWorldApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(getApplicationContext());
        Timber.plant(new DebugTree());
    }
}
