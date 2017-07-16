package com.markchan.carrier.data.repository.datasource;

import android.content.Context;
import com.markchan.carrier.data.cache.FontCache;
import com.markchan.carrier.data.net.RestApi;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontDataSourceFactory {

    private final FontCache mFontCache;
    private final RestApi mRestApi;

    public FontDataSourceFactory(Context context, FontCache fontCache, RestApi restApi) {
        mFontCache = fontCache;
        mRestApi = restApi;
    }

    public FontDataStore create(int fontId) {
        FontDataStore fontDataStore;

        if (mFontCache.isDownloaded(fontId)) {
            fontDataStore = createDiskDataSource();
        } else {
            fontDataStore = createCloudDataSource();
        }

        return fontDataStore;
    }

    public FontDataStore createDiskDataSource() {
        return new DiskFontDataSource(mFontCache);
    }

    public FontDataStore createCloudDataSource() {
        return new CloudFontDataSource(mRestApi, mFontCache);
    }
}
