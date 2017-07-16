package com.markchan.carrier.data.repository.datasource;

import com.markchan.carrier.data.entity.FontEntity;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontDataStore {

    FontEntity getFontEntity(final int fontId);

    List<FontEntity> getFontEntities();
}
