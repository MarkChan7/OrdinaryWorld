package com.markchan.carrier.data.repository.datasource;

import android.content.Context;

import com.markchan.carrier.data.cache.FontEntityCache;
import com.markchan.carrier.data.dao.FontEntityDao;
import com.markchan.carrier.data.net.RestApi;
import com.markchan.carrier.domain.interactor.GetFontList.Params;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontDataSourceFactory {

    private final FontEntityDao mFontEntityDao;
    private final RestApi mRestApi;
    private final FontEntityCache mFontEntityCache;

    public FontDataSourceFactory(Context context, FontEntityCache fontEntityCache, RestApi restApi,
                                 FontEntityDao fontEntityDao) {
        mFontEntityCache = fontEntityCache;
        mRestApi = restApi;
        mFontEntityDao = fontEntityDao;
    }

    public FontDataStore create(final int fontId) {
        FontDataStore fontDataStore;

        if (mFontEntityCache.isFontEntityDownloaded(fontId)) {
            fontDataStore = createDiskDataSource();
        } else if (mFontEntityDao.queryFontEntityById(fontId) != null) {
            fontDataStore = createDatabaseSource();
        } else {
            fontDataStore = createCloudDataSource();
        }

        return fontDataStore;
    }

    public FontDataStore createByDataSource(final int dataSource) {
        if (dataSource == Params.DATA_SOURCE_DOWNLOADED) {
            return createDiskDataSource();
        } else if (dataSource == Params.DATA_SOURCE_ONLINE) {
            return createCloudDataSource();
        } else {
            return new DatabaseFontDataSource(mFontEntityDao);
        }
    }

    private FontDataStore createDatabaseSource() {
        return new DatabaseFontDataSource(mFontEntityDao);
    }

    private FontDataStore createDiskDataSource() {
        return new DiskFontDataSource(mFontEntityCache);
    }

    private FontDataStore createCloudDataSource() {
        return new CloudFontDataSource(mRestApi, mFontEntityCache, mFontEntityDao);
    }
}
