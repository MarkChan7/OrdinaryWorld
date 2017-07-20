package com.markchan.carrier.data.cache;

import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.domain.cache.FontCache;

import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontEntityCache extends FontCache {

    FontEntity getDownloadedFontEntity(final int fontId);

    List<FontEntity> getDownloadedFontEntities();

    boolean setFontEntityDownloaded(FontEntity fontEntity);

    boolean deleteDownloadedFontEntity(FontEntity fontEntity);

    boolean isDownloaded(final int fontId);

    void deleteAllDownloadedFontEntities();
}