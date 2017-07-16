package com.markchan.carrier.data.repository.datasource;

import android.content.Context;
import com.markchan.carrier.data.cache.FontCache;
import com.markchan.carrier.data.net.RestApi;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontDataSourceFactory {

    private final Context mContext;
    private final FontCache mFontCache;
    private final RestApi mRestApi;

    public FontDataSourceFactory(Context context, FontCache fontCache, RestApi restApi) {
        mContext = context;
        mFontCache = fontCache;
        mRestApi = restApi;
    }

    public FontDataStore create(int fontId) {
        FontDataStore fontDataStore = null;

        if (mFontCache.isCached(fontId)) {
            fontDataStore = new DiskFontDataSource(mFontCache);
        } else {
            fontDataStore = createCloudDataSource();
        }

        return fontDataStore;
    }

    public FontDataStore createCloudDataSource() {
        return new CloudFontDataSource(mRestApi, mFontCache);
    }
}
