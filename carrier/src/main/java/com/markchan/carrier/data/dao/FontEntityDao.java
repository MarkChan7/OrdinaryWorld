package com.markchan.carrier.data.dao;

import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.domain.dao.FontDao;

import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontEntityDao extends FontDao {

    long insertFontEntity(FontEntity fontEntity);

    boolean updateFontEntity(FontEntity fontEntity);

    boolean save(FontEntity fontEntity);

    boolean deleteFontEntity(FontEntity fontEntity);

    FontEntity queryFontEntityById(final long fontId);

    List<FontEntity> queryFontEntities(final int dataSource);
}
