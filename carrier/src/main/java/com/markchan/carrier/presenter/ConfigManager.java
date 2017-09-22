package com.markchan.carrier.presenter;

import com.markchan.carrier.presenter.config.AppConfig;
import com.markchan.carrier.presenter.config.DeviceConfig;
import com.markchan.carrier.presenter.config.impl.AppConfigImpl;
import com.markchan.carrier.presenter.config.impl.DeviceConfigImpl;

/**
 * Created by Mark on 2017/7/16.
 */
public class ConfigManager implements AppConfig, DeviceConfig {

    private static ConfigManager INSTANCE;

    public static ConfigManager getDefault() {
        if (INSTANCE == null) {
            synchronized (ConfigManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ConfigManager();
                }
            }
        }
        return INSTANCE;
    }

    private final AppConfig mAppConfig;
    private final DeviceConfig mDeviceConfig;

    private ConfigManager() {
        mAppConfig = new AppConfigImpl();
        mDeviceConfig = new DeviceConfigImpl();
    }

    @Override
    public void setAddWatermark(boolean isAddWatermark) {
        mAppConfig.setAddWatermark(isAddWatermark);
    }

    @Override
    public boolean isAddWatermark() {
        return mAppConfig.isAddWatermark();
    }

    @Override
    public void setAutoSave(boolean isAutoSave) {
        mAppConfig.setAutoSave(isAutoSave);
    }

    @Override
    public boolean isAutoSave() {
        return mAppConfig.isAutoSave();
    }

    @Override
    public void setKeyboardHeight(int keyboardHeight) {
        mDeviceConfig.setKeyboardHeight(keyboardHeight);
    }

    @Override
    public int getKeyboardHeight() {
        return mDeviceConfig.getKeyboardHeight();
    }
}
