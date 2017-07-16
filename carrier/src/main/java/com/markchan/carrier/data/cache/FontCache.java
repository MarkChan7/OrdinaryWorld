package com.markchan.carrier.data.cache;

import com.markchan.carrier.data.entity.FontEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontCache {

    Observable<FontEntity> getFontEntity(final int fontId);

    Observable<List<FontEntity>> getFontEntities();

    void putFontEntity(FontEntity fontEntity);

    boolean isCached(final int fontId);

    void evictAll();
}