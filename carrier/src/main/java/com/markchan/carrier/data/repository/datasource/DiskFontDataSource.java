package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.cache.FontCache;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.exception.FontDeletedException;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
    public Observable<FontEntity> getFontEntity(final int fontId) {
        return Observable.create(new ObservableOnSubscribe<FontEntity>() {

            @Override
            public void subscribe(ObservableEmitter<FontEntity> e) throws Exception {
                FontEntity fontEntity = mFontCache.getDownloadedFontEntity(fontId);
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
                List<FontEntity> fontEntities = mFontCache.getDownloadedFontEntities();
                e.onNext(fontEntities);
                e.onComplete();
            }
        });
    }
}
