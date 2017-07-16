package com.markchan.carrier.data.repository;

import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.entity.FontEntityDataMapper;
import com.markchan.carrier.data.repository.datasource.FontDataSourceFactory;
import com.markchan.carrier.data.repository.datasource.FontDataStore;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.repository.FontRepository;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontDataRepository implements FontRepository {

    private final FontDataSourceFactory mFontDataSourceFactory;
    private final FontEntityDataMapper mFontEntityDataMapper;

    public FontDataRepository(FontDataSourceFactory fontDataSourceFactory,
            FontEntityDataMapper fontEntityDataMapper) {
        mFontDataSourceFactory = fontDataSourceFactory;
        mFontEntityDataMapper = fontEntityDataMapper;
    }

    @Override
    public Observable<Font> getFont(final int fontId) {
        final FontDataStore fontDataStore = mFontDataSourceFactory.create(fontId);
        return fontDataStore.getFontEntity(fontId).map(new Function<FontEntity, Font>() {

            @Override
            public Font apply(@NonNull FontEntity fontEntity) throws Exception {
                return mFontEntityDataMapper.transform(fontEntity);
            }
        });
    }

    @Override
    public Observable<List<Font>> getFonts() {
        final FontDataStore dataSource = mFontDataSourceFactory.createCloudDataSource();
        return dataSource.getFontEntities().map(new Function<List<FontEntity>, List<Font>>() {

            @Override
            public List<Font> apply(@NonNull List<FontEntity> fontEntities) throws Exception {
                return mFontEntityDataMapper.transform(fontEntities);
            }
        });
    }
}
