package com.markchan.carrier.data.cache;

import android.text.TextUtils;

import com.blankj.utilcode.util.FileUtils;
import com.markchan.carrier.domain.CarrierDomainConstant.DATA_SOURCE;
import com.markchan.carrier.domain.Scheme;
import com.markchan.carrier.data.dao.FontEntityDao;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.mapper.FontEntityDataMapper;
import com.markchan.carrier.domain.Font;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontEntityCacheImpl implements FontEntityCache {

    private final FontEntityDao mFontEntityDao;
    private final FontEntityDataMapper mFontEntityDataMapper;

    public FontEntityCacheImpl(FontEntityDao fontEntityDao,
            FontEntityDataMapper fontEntityDataMapper) {
        if (fontEntityDao == null) {
            throw new NullPointerException("Context or Font DAO can't be null");
        }
        mFontEntityDao = fontEntityDao;
        mFontEntityDataMapper = fontEntityDataMapper;
    }

    @Override
    public boolean setFontDownloaded(Font font) {
        return setFontEntityDownloaded(mFontEntityDataMapper.retransform(font));
    }

    @Override
    public boolean deleteDownloadedFont(Font font) {
        return deleteDownloadedFontEntity(mFontEntityDataMapper.retransform(font));
    }

    @Override
    public void deleteAllDownloadedFonts() {
        deleteAllDownloadedFontEntities();
    }

    // DOMAIN
    // ---------------------------------------------------------------------------------------------
    // DATA

    @Override
    public FontEntity getDownloadedFontEntity(long fontId) {
        final FontEntity fontEntity = mFontEntityDao.queryFontEntityById(fontId);
        if (fontEntity != null) {
            if (!FileUtils.isFileExists(Scheme.FILE.crop(fontEntity.getUri()))) {
                fontEntity.setUri(fontEntity.getUri());
//                fontEntity.save();
                return null;
            }
        }
        return fontEntity;
    }

    @Override
    public List<FontEntity> getDownloadedFontEntities() {
        final List<FontEntity> fontEntities = mFontEntityDao
                .queryFontEntities(DATA_SOURCE.ASSET_AND_SDCARD);
        Iterator<FontEntity> iterator = fontEntities.iterator();
        while (iterator.hasNext()) {
            FontEntity fontEntity = iterator.next();
            if (!FileUtils.isFileExists(Scheme.FILE.crop(fontEntity.getUri()))) {
                fontEntity.setUri(fontEntity.getUri());
                mFontEntityDao.updateFontEntity(fontEntity);
                iterator.remove();
            }
        }
        return fontEntities;
    }

    @Override
    public boolean setFontEntityDownloaded(final FontEntity fontEntity) {
        if (!isFontEntityExists(fontEntity)) {
            fontEntity.setUri(fontEntity.getUri());
        }
        return mFontEntityDao.save(fontEntity);
    }

    @Override
    public boolean deleteDownloadedFontEntity(FontEntity fontEntity) {
        if (fontEntity != null) {
            try {
                if (FileUtils.deleteFile(Scheme.FILE.crop(fontEntity.getUri()))) {
                    return mFontEntityDao.deleteFontEntity(fontEntity);
                }
            } catch (Exception ignored) {
                ;
            }
        }
        return false;
    }

    private boolean isFontEntityExists(FontEntity fontEntity) {
        if (fontEntity != null) {
            String uri = fontEntity.getUri();
            if (TextUtils.isEmpty(uri)) {
                if (Scheme.ofUri(uri) == Scheme.FILE) {
                    return FileUtils.isFileExists(Scheme.FILE.crop(uri));
                }
            }
        }
        return false;
    }

    @Override
    public boolean isFontEntityDownloaded(long fontId) {
        FontEntity fontEntity = mFontEntityDao.queryFontEntityById(fontId);
        return isFontEntityExists(fontEntity);
    }

    @Override
    public void deleteAllDownloadedFontEntities() {
        List<FontEntity> fontEntities = mFontEntityDao
                .queryFontEntities(DATA_SOURCE.ASSET_AND_SDCARD);
        if (fontEntities != null && !fontEntities.isEmpty()) {
            for (FontEntity fontEntity : fontEntities) {
                if (FileUtils.deleteFile(Scheme.FILE.crop(fontEntity.getUri()))) {
                    fontEntity.setUri(fontEntity.getUri());
                    fontEntity.update();
                }
            }
        }
    }
}
