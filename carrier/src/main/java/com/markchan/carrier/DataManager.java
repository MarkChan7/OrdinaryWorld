package com.markchan.carrier;

import android.content.Context;
import com.markchan.carrier.model.FontModel;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class DataManager {

    private static DataManager INSTANCE;

    public static DataManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataManager(context);
                }
            }
        }
        return INSTANCE;
    }

    private Context mContext;

    public DataManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public List<FontModel> getFontModels() {
        return null;
    }
}
