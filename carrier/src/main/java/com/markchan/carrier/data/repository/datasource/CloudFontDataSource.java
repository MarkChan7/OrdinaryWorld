package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.cache.FontEntityCache;
import com.markchan.carrier.data.dao.FontEntityDao;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.net.RestApi;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Mark on 2017/7/16.
 */
public class CloudFontDataSource implements FontDataStore {

    private final RestApi mRestApi;
    private final FontEntityCache mFontEntityCache;
    private final FontEntityDao mFontEntityDao;

    public CloudFontDataSource(RestApi restApi, FontEntityCache fontEntityCache,
                               FontEntityDao fontEntityDao) {
        mRestApi = restApi;
        mFontEntityCache = fontEntityCache;
        mFontEntityDao = fontEntityDao;
    }

    @Override
    public Observable<FontEntity> getFontEntity(int fontId) {
        return null;
    }

    @Override
    public Observable<List<FontEntity>> getFontEntities() {
        return mRestApi.getFontEntities()
                .map(new Function<List<FontEntity>, List<FontEntity>>() {

                    @Override
                    public List<FontEntity> apply(@NonNull List<FontEntity> fontEntities)
                            throws Exception {
                        if (fontEntities != null && !fontEntities.isEmpty()) {
                            for (FontEntity fontEntity : fontEntities) {
                                if (!mFontEntityCache.isFontEntityDownloaded(fontEntity.getId())) {
                                    mFontEntityDao.insertOrUpdateFontEntity(fontEntity);
                                }
                            }
                        }
                        return fontEntities;
                    }
                });
    }
}
