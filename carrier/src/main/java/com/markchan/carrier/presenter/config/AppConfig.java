package com.markchan.carrier.presenter.config;

/**
 * Created by Mark on 2017/7/16.
 */
public interface AppConfig {

    void setAddWatermark(boolean isAddWatermark);

    boolean isAddWatermark();

    void setAutoSave(boolean isAutoSave);

    boolean isAutoSave();
}
