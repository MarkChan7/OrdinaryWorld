package com.markchan.carrier.presenter.config;

import com.blankj.utilcode.util.SPUtils;
import com.markchan.carrier.Constants.SP_KEY;

/**
 * Created by Mark on 2017/7/16.
 */
class AppConfigImpl implements AppConfig {

    private static final String CONFIG_NAME = "jiantu";

    private SPUtils mSP;

    AppConfigImpl() {
        mSP = SPUtils.getInstance(CONFIG_NAME);
    }

    @Override
    public void setAddWatermark(boolean isAddWatermark) {
        mSP.put(SP_KEY.IS_ADD_WATERMARK, isAddWatermark);
    }

    @Override
    public boolean isAddWatermark() {
        return mSP.getBoolean(SP_KEY.IS_ADD_WATERMARK);
    }

    @Override
    public void setAutoSave(boolean isAutoSave) {
        mSP.put(SP_KEY.IS_AUTO_SAVE, isAutoSave);
    }

    @Override
    public boolean isAutoSave() {
        return mSP.getBoolean(SP_KEY.IS_AUTO_SAVE);
    }
}
