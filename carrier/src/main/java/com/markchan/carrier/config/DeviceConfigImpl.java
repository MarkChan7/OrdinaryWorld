package com.markchan.carrier.config;

import com.blankj.utilcode.util.SPUtils;
import com.markchan.carrier.Constants.SP_KEY;

/**
 * Created by Mark on 2017/7/16.
 */
class DeviceConfigImpl implements DeviceConfig {

    private static final String CONFIG_NAME = "device";

    private SPUtils mSP;

    DeviceConfigImpl() {
        mSP = SPUtils.getInstance(CONFIG_NAME);
    }

    @Override
    public void setKeyboardHeight(int keyboardHeight) {
        mSP.put(SP_KEY.KEY_BOARD_HEIGHT, keyboardHeight);
    }

    @Override
    public int getKeyboardHeight() {
        return mSP.getInt(SP_KEY.KEY_BOARD_HEIGHT, 0);
    }
}
