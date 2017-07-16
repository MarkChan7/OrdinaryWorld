package com.markchan.carrier.data.repository.datasource;

import android.content.Context;
import com.markchan.carrier.data.cache.FontCache;
import com.markchan.carrier.data.net.RetrofitHelper;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontDataSourceFactory {

    private final Context mContext;
    private final FontCache mFontCache;

    public FontDataSourceFactory(Context context, FontCache fontCache) {
        mContext = context;
        mFontCache = fontCache;
    }

    public FontDataStore create(int fontId) {
        FontDataStore fontDataStore = null;

        if (mFontCache.isCached(fontId)) {
            fontDataStore = new DiskFontDataSource(mFontCache);
        } else {
            fontDataStore = new CloudFontDataSource(RetrofitHelper.getInstance(), mFontCache);
        }

        return fontDataStore;
    }
}
