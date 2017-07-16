package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.cache.FontCache;
import com.markchan.carrier.data.entity.FontEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Mark on 2017/7/16
 */
public class DiskFontDataSource implements FontDataStore {

    private final FontCache mFontCache;

    public DiskFontDataSource(FontCache fontCache) {
        mFontCache = fontCache;
    }

    @Override
    public Observable<FontEntity> getFontEntity(int fontId) {
        return mFontCache.getFontEntity(fontId);
    }

    @Override
    public Observable<List<FontEntity>> getFontEntities() {
        return mFontCache.getFontEntities();
    }
}
