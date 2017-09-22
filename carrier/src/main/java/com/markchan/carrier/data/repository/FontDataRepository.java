package com.markchan.carrier.data.repository;

import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.mapper.FontEntityDataMapper;
import com.markchan.carrier.data.repository.datasource.FontDataSourceFactory;
import com.markchan.carrier.data.repository.datasource.FontDataStore;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.repository.FontRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

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
    public Observable<List<Font>> getFonts(int dataSource) {
        return mFontDataSourceFactory.createByDataSource(dataSource).getFontEntities().map(
                new Function<List<FontEntity>, List<Font>>() {

                    @Override
                    public List<Font> apply(@NonNull List<FontEntity> fontEntities)
                            throws Exception {
                        return mFontEntityDataMapper.transform(fontEntities);
                    }
                });

//        final FontDataStore diskDataSource = mFontDataSourceFactory.createDiskDataSource();
//        final FontDataStore cloudDataSource = mFontDataSourceFactory.createCloudDataSource();
//        return Observable.zip(cloudDataSource.getFontEntities(), diskDataSource.getFontEntities(),
//                new BiFunction<List<FontEntity>, List<FontEntity>, List<FontEntity>>() {
//
//                    @Override
//                    public List<FontEntity> apply(@NonNull List<FontEntity> clouds,
//                                                  @NonNull List<FontEntity> disks)
//                            throws Exception {
//                        for (FontEntity disk : disks) {
//                            for (FontEntity cloud : clouds) {
//                                if (disk.getId() == cloud.getId()) {
//                                    clouds.set(clouds.indexOf(cloud), disk);
//                                    break;
//                                } else {
//                                    cloud.save();
//                                }
//                            }
//                        }
//                        return clouds;
//                    }
//                })
//                .map(new Function<List<FontEntity>, List<Font>>() {
//
//                    @Override
//                    public List<Font> apply(@NonNull List<FontEntity> fontEntities)
//                            throws Exception {
//                        return mFontEntityDataMapper.transform(fontEntities);
//                    }
//                });
    }
}
