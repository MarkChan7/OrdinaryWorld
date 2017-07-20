package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.dao.FontEntityDao;
import com.markchan.carrier.data.entity.FontEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/20
 */
public class DatabaseFontDataSource implements FontDataStore {

    private final FontEntityDao mFontEntityDao;

    public DatabaseFontDataSource(FontEntityDao fontEntityDao) {
        mFontEntityDao = fontEntityDao;
    }

    @Override
    public Observable<FontEntity> getFontEntity(final int fontId) {
        return Observable.create(new ObservableOnSubscribe<FontEntity>() {

            @Override
            public void subscribe(ObservableEmitter<FontEntity> e) throws Exception {
                e.onNext(mFontEntityDao.queryFontEntityById(fontId));
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<FontEntity>> getFontEntities() {
        return Observable.create(new ObservableOnSubscribe<List<FontEntity>>() {

            @Override
            public void subscribe(ObservableEmitter<List<FontEntity>> e) throws Exception {
                e.onNext(mFontEntityDao.queryFontEntities());
                e.onComplete();
            }
        });
    }
}
