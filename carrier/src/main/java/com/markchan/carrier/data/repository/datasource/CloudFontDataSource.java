package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.cache.FontCache;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.net.RestApi;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
    public Observable<FontEntity> getFontEntity(int fontId) {
        return null;
    }

    @Override
    public Observable<List<FontEntity>> getFontEntities() {
        return Observable.create(new ObservableOnSubscribe<List<FontEntity>>() {

            @Override
            public void subscribe(ObservableEmitter<List<FontEntity>> e) throws Exception {
                final List<FontEntity> fontEntities = mRestApi.getFontEntities();
                if (fontEntities != null && !fontEntities.isEmpty()) {
//                    Iterator<FontEntity> iterator = fontEntities.iterator();
//                    while (iterator.hasNext()) {
//                        FontEntity fontEntity = iterator.next();
//                        FontEntity cachedFontEntity = mFontCache.getFontEntity(fontEntity.getId());
//                        if (cachedFontEntity == null) {
//                            fontEntity.insert();
//                        } else {
//                            iterator.remove();
//                        }
//                    }

                    e.onNext(fontEntities);
                    e.onComplete();
                } else {
                    e.onError(new Exception("Font enities is null or empty"));
                }
            }
        });
    }
}
