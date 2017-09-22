package com.markchan.carrier.presenter.config.impl;

import com.blankj.utilcode.util.SPUtils;
import com.markchan.carrier.presenter.Constants.SP_KEY;
import com.markchan.carrier.presenter.config.DeviceConfig;

/**
 * Created by Mark on 2017/7/16.
 */
public class DeviceConfigImpl implements DeviceConfig {

    private static final String CONFIG_NAME = "device";

    private SPUtils mSp;

    public DeviceConfigImpl() {
        mSp = SPUtils.getInstance(CONFIG_NAME);
    }

    @Override
    public void setKeyboardHeight(int keyboardHeight) {
        mSp.put(SP_KEY.KEY_BOARD_HEIGHT, keyboardHeight);
    }

    @Override
    public int getKeyboardHeight() {
        return mSp.getInt(SP_KEY.KEY_BOARD_HEIGHT, 0);
    }
}
