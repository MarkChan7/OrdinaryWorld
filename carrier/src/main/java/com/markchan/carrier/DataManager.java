package com.markchan.carrier;

import com.markchan.carrier.data.database.FontEntityDao;
import com.markchan.carrier.presenter.config.ConfigManager;

/**
 * Created by Mark on 2017/7/16.
 */
public class DataManager {

    private static DataManager INSTANCE;

    public static DataManager getDefault() {
        if (INSTANCE == null) {
            synchronized (ConfigManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataManager();
                }
            }
        }
        return INSTANCE;
    }

    private final FontEntityDao mFonDao;

    private DataManager() {
        mFonDao = Middleware.getDefault().getFontEntityDao();
    }
}