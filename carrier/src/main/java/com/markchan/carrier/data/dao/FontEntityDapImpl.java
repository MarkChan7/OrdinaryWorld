package com.markchan.carrier.data.dao;

import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.mapper.FontEntityDataMapper;
import com.markchan.carrier.data.entity.FontEntity_Table;
import com.markchan.carrier.domain.Font;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontEntityDapImpl implements FontEntityDao {

    private final FontEntityDataMapper mFontEntityDataMapper;

    public FontEntityDapImpl(FontEntityDataMapper fontEntityDataMapper) {
        mFontEntityDataMapper = fontEntityDataMapper;
    }

    @Override
    public boolean updateFont(Font font) {
        SQLite.update(FontEntity.class)
                .set(FontEntity_Table.uri.eq(font.getUri()))
                .where(FontEntity_Table.id.eq(font.getId()))
                .execute();
        return true;
    }

    @Override
    public boolean deleteFont(Font font) {
        return mFontEntityDataMapper.retransform(font).delete();
    }

    // DOMAIN
    // ---------------------------------------------------------------------------------------------
    // DATA

    @Override
    public long insertFontEntity(FontEntity fontEntity) {
        return fontEntity.insert();
    }

    @Override
    public boolean updateFontEntity(FontEntity fontEntity) {
        return fontEntity.update();
    }

    @Override
    public boolean insertOrReplaceFontEntity(FontEntity fontEntity) {
        if (fontEntity.exists()) {
            fontEntity.delete();
        }
        return fontEntity.insert() > -1;
    }

    @Override
    public boolean insertOrUpdateFontEntity(FontEntity fontEntity) {
        return fontEntity.save();
    }

    @Override
    public boolean deleteFontEntity(FontEntity fontEntity) {
        return fontEntity.delete();
    }

    @Override
    public FontEntity queryFontEntityById(int fontId) {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.id.eq(fontId))
                .querySingle();
    }

    @Override
    public List<FontEntity> queryFontEntities() {
        return SQLite.select()
                .from(FontEntity.class)
                .orderBy(FontEntity_Table.order.getNameAlias(), true)
                .queryList();
    }

    @Override
    public FontEntity queryDownloadedFontEntityById(int fontId) {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.id.eq(fontId))
                .and(FontEntity_Table.uri.like("file%"))
                .querySingle();
    }

    @Override
    public List<FontEntity> queryDownloadedFontEntities() {
        return queryFontEntitiesByScheme("file");
    }

    @Override
    public List<FontEntity> queryOnlineFontEntities() {
        return queryFontEntitiesByScheme("http");
    }

    private List<FontEntity> queryFontEntitiesByScheme(String scheme) {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.uri.like(scheme + "%"))
                .orderBy(FontEntity_Table.order.getNameAlias(), true)
                .queryList();
    }
}
