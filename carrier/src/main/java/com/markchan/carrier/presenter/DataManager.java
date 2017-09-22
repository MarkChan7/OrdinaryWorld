package com.markchan.carrier.presenter;

import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.cache.FontCache;
import com.markchan.carrier.domain.dao.FontDao;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;
import com.markchan.carrier.presenter.model.FontModel;

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

    private final FontDao mFonDao;
    private final FontCache mFontCache;
    private final FontModelDataMapper mFontModelDataMapper;

    private DataManager() {
        mFonDao = Middleware.getDefault().getFontDao();
        mFontModelDataMapper = Middleware.getDefault().getFontModelDataMapper();
        mFontCache = Middleware.getDefault().getFontCache();
    }

    public boolean updateFont(FontModel fontModel) {
        if (fontModel != null) {
            Font font = mFontModelDataMapper.retransform(fontModel);
            if (font != null) {
                return mFonDao.updateFont(font);
            }
        }
        return false;
    }

    public boolean deleteFont(FontModel fontModel) {
        if (fontModel != null) {
            Font font = mFontModelDataMapper.retransform(fontModel);
            if (font != null) {
                return mFontCache.deleteDownloadedFont(font);
            }
        }
        return false;
    }
}