package com.markchan.carrier.data.database;

import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.entity.FontEntity_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontEntityDapImpl implements FontEntityDao {

    @Override
    public FontEntity queryFontEntityById(int fontId) {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.id.eq(fontId))
                .querySingle();
    }

    @Override
    public FontEntity queryDownloadedFontEntityById(int fontId) {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.id.eq(fontId))
                .and(FontEntity_Table.isDownloaded.eq(true))
                .querySingle();
    }

    @Override
    public FontEntity queryOnlineFontEntityById(int fontId) {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.id.eq(fontId))
                .and(FontEntity_Table.isDownloaded.eq(false))
                .querySingle();
    }

    @Override
    public List<FontEntity> queryFontEntities() {
        return SQLite.select()
                .from(FontEntity.class)
                .queryList();
    }

    @Override
    public List<FontEntity> queryDownloadedFontEntities() {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.isDownloaded.eq(true))
                .queryList();
    }

    @Override
    public List<FontEntity> queryOnlineFontEntities() {
        return SQLite.select()
                .from(FontEntity.class)
                .where(FontEntity_Table.isDownloaded.eq(false))
                .queryList();
    }
}
