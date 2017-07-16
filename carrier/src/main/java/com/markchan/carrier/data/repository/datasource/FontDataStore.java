package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.entity.FontEntity;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontDataStore {

    Observable<FontEntity> getFontEntity(final int fontId);

    Observable<List<FontEntity>> getFontEntities();
}
