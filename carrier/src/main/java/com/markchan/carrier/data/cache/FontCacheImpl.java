package com.markchan.carrier.data.cache;

import com.blankj.utilcode.util.FileUtils;
import com.markchan.carrier.data.database.FontEntityDao;
import com.markchan.carrier.data.entity.FontEntity;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontCacheImpl implements FontCache {

    private final FontEntityDao mFontEntityDao;

    public FontCacheImpl(FontEntityDao fontEntityDao) {
        if (fontEntityDao == null) {
            throw new NullPointerException("Context or Font DAO can't be null");
        }
        mFontEntityDao = fontEntityDao;
    }

    @Override
    public FontEntity getDownloadedFontEntity(int fontId) {
        final FontEntity fontEntity = mFontEntityDao.queryDownloadedFontEntityById(fontId);
        if (fontEntity != null) {
            if (!FileUtils.isFileExists(fontEntity.getFilePath())) {
                fontEntity.setDownloaded(false);
                fontEntity.setFilePath(null);
                fontEntity.save();
                return null;
            }
        }
        return fontEntity;
    }

    @Override
    public List<FontEntity> getDownloadedFontEntities() {
        final List<FontEntity> fontEntities = mFontEntityDao.queryDownloadedFontEntities();
        Iterator<FontEntity> iterator = fontEntities.iterator();
        while (iterator.hasNext()) {
            FontEntity fontEntity = iterator.next();
            if (!FileUtils.isFileExists(fontEntity.getFilePath())) {
                fontEntity.setDownloaded(false);
                fontEntity.setFilePath(null);
                fontEntity.save();
                iterator.remove();
            }
        }
        return fontEntities;
    }

    @Override
    public void setFontEntityDownloaded(final FontEntity fontEntity) {
        if (isFontEntityExists(fontEntity)) {

            fontEntity.setDownloaded(true);
        } else {
            fontEntity.setDownloaded(false);
            fontEntity.setFilePath(null);
        }
        fontEntity.save();
    }

    private boolean isFontEntityExists(FontEntity fontEntity) {
        return fontEntity != null && !FileUtils.isFileExists(fontEntity.getFilePath());
    }

    @Override
    public boolean isDownloaded(int fontId) {
        FontEntity fontEntity = mFontEntityDao.queryDownloadedFontEntityById(fontId);
        return isFontEntityExists(fontEntity);
    }

    @Override
    public void evictAll() {
        List<FontEntity> fontEntities = mFontEntityDao.queryDownloadedFontEntities();
        if (fontEntities != null && !fontEntities.isEmpty()) {
            for (FontEntity fontEntity : fontEntities) {
                if (FileUtils.deleteFile(fontEntity.getFilePath())) {
                    fontEntity.setDownloaded(false);
                    fontEntity.setFilePath(null);
                    fontEntity.update();
                }
            }
        }
    }
}
