package com.markchan.carrier.data.cache;

import com.markchan.carrier.data.entity.FontEntity;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontCache {

    FontEntity getDownloadedFontEntity(final int fontId);

    List<FontEntity> getDownloadedFontEntities();

    void setFontEntityDownloaded(FontEntity fontEntity);

    boolean isDownloaded(final int fontId);

    void evictAll();
}