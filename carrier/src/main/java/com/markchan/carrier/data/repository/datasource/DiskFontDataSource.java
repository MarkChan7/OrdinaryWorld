package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.cache.FontEntityCache;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.exception.FontDeletedException;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Mark on 2017/7/16
 */
public class DiskFontDataSource implements FontDataStore {

    private final FontEntityCache mFontEntityCache;

    public DiskFontDataSource(FontEntityCache fontEntityCache) {
        mFontEntityCache = fontEntityCache;
    }

    @Override
    public Observable<FontEntity> getFontEntity(final int fontId) {
        return Observable.create(new ObservableOnSubscribe<FontEntity>() {

            @Override
            public void subscribe(ObservableEmitter<FontEntity> e) throws Exception {
                FontEntity fontEntity = mFontEntityCache.getDownloadedFontEntity(fontId);
                if (fontEntity != null) {
                    e.onNext(fontEntity);
                    e.onComplete();
                } else {
                    e.onError(new FontDeletedException());
                }
            }
        });
    }

    @Override
    public Observable<List<FontEntity>> getFontEntities() {
        return Observable.create(new ObservableOnSubscribe<List<FontEntity>>() {

            @Override
            public void subscribe(ObservableEmitter<List<FontEntity>> e) throws Exception {
                List<FontEntity> fontEntities = mFontEntityCache.getDownloadedFontEntities();
                e.onNext(fontEntities);
                e.onComplete();
            }
        });
    }
}
