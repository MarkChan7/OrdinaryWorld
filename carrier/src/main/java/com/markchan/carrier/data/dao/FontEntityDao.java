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

    boolean insertOrReplaceFontEntity(FontEntity fontEntity);

    boolean insertOrUpdateFontEntity(FontEntity fontEntity);

    boolean deleteFontEntity(FontEntity fontEntity);

    FontEntity queryFontEntityById(final int fontId);

    FontEntity queryDownloadedFontEntityById(final int fontId);

    List<FontEntity> queryFontEntities();

    List<FontEntity> queryDownloadedFontEntities();

    List<FontEntity> queryOnlineFontEntities();
}
