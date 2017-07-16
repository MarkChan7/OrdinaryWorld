package com.markchan.carrier.data.database;

import com.markchan.carrier.data.entity.FontEntity;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontDao {

    FontEntity queryFontEntityById(final int fontId);

    FontEntity queryDownloadedFontEntityById(final int fontId);

    FontEntity queryOnlineFontEntityById(final int fontId);

    List<FontEntity> queryFontEntities();

    List<FontEntity> queryDownloadedFontEntities();

    List<FontEntity> queryOnlineFontEntities();
}
