package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.cache.FontCache;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.net.RestApi;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class CloudFontDataSource implements FontDataStore {

    private final RestApi mRestApi;
    private final FontCache mFontCache;

    public CloudFontDataSource(RestApi restApi, FontCache fontCache) {
        mRestApi = restApi;
        mFontCache = fontCache;
    }

    @Override
    public FontEntity getFontEntity(int fontId) {
        return null;
    }

    @Override
    public List<FontEntity> getFontEntities() {
        List<FontEntity> fontEntities = getFontEntities();
        if (fontEntities != null && fontEntities.isEmpty()) {
            Iterator<FontEntity> iterator = fontEntities.iterator();
            while (iterator.hasNext()) {
                FontEntity fontEntity = iterator.next();
                FontEntity cachedFontEntity = mFontCache.getFontEntity(fontEntity.getId());
                if (cachedFontEntity == null) {
                    fontEntity.insert();
                } else {
                    iterator.remove();
                }
            }
        }
        return null;
    }
}
