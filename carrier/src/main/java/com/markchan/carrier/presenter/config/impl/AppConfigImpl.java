package com.markchan.carrier.presenter.config.impl;

import com.blankj.utilcode.util.SPUtils;
import com.markchan.carrier.presenter.CarrierPresenterConstant.SP_KEY;
import com.markchan.carrier.presenter.config.AppConfig;

/**
 * Created by Mark on 2017/7/16.
 */
public class AppConfigImpl implements AppConfig {

    private static final String CONFIG_NAME = "jiantu";

    private SPUtils mSp;

    public AppConfigImpl() {
        mSp = SPUtils.getInstance(CONFIG_NAME);
    }

    @Override
    public void setAddWatermark(boolean isAddWatermark) {
        mSp.put(SP_KEY.IS_ADD_WATERMARK, isAddWatermark);
    }

    @Override
    public boolean isAddWatermark() {
        return mSp.getBoolean(SP_KEY.IS_ADD_WATERMARK);
    }

    @Override
    public void setAutoSave(boolean isAutoSave) {
        mSp.put(SP_KEY.IS_AUTO_SAVE, isAutoSave);
    }

    @Override
    public boolean isAutoSave() {
        return mSp.getBoolean(SP_KEY.IS_AUTO_SAVE);
    }
}
