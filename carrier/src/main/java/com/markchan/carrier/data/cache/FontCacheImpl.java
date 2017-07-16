package com.markchan.carrier.data.cache;

import android.content.Context;
import com.blankj.utilcode.util.FileUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.markchan.carrier.data.database.FontDao;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.exception.CacheDeletedException;
import com.markchan.carrier.presenter.util.CacheDirHelper;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontCacheImpl implements FontCache {

    private final Context mContext;
    private final FontDao mFontDao;

    public FontCacheImpl(Context context, FontDao fontDao) {
        if (context == null || fontDao == null) {
            throw new NullPointerException("Context or Font DAO can't be null");
        }
        mContext = context;
        mFontDao = fontDao;
    }

    @Override
    public Observable<FontEntity> getFontEntity(int fontId) {
        final FontEntity fontEntity = mFontDao.queryDownloadedFontEntityById(fontId);
        if (fontEntity != null) {
            if (!FileUtils.isFileExists(fontEntity.getFilePath())) {
                fontEntity.setDownloaded(false);
                fontEntity.setFilePath(null);
                fontEntity.save();
                return Observable.create(new ObservableOnSubscribe<FontEntity>() {

                    @Override
                    public void subscribe(ObservableEmitter<FontEntity> e) throws Exception {
                        e.onError(new CacheDeletedException());
                    }
                });
            }
        }
        return Observable.create(new ObservableOnSubscribe<FontEntity>() {

            @Override
            public void subscribe(ObservableEmitter<FontEntity> e) throws Exception {
                e.onNext(fontEntity);
                e.onComplete();
            }
        });
    }

    @Override
    public Observable<List<FontEntity>> getFontEntities() {
        final List<FontEntity> fontEntities = mFontDao.queryDownloadedFontEntities();
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
        return Observable.create(new ObservableOnSubscribe<List<FontEntity>>() {

            @Override
            public void subscribe(ObservableEmitter<List<FontEntity>> e) throws Exception {
                if (!fontEntities.isEmpty()) {
                    e.onNext(fontEntities);
                    e.onComplete();
                } else {
                    e.onComplete();
                }
            }
        });
    }

    @Override
    public void putFontEntity(final FontEntity fontEntity) {
        if (fontEntity != null) {
            File folder = CacheDirHelper.getExternalCacheDirectory(mContext, "font");
            if (folder != null) {
                final String filePath =
                        folder.getAbsolutePath() + File.separator + fontEntity.getPostscriptName();
                if (!isCached(fontEntity.getId())) {
                    FileDownloader.getImpl()
                            .create(fontEntity.getUrl())
                            .setPath(filePath)
                            .setListener(new FileDownloadSampleListener() {

                                @Override
                                protected void completed(BaseDownloadTask task) {
                                    fontEntity.setDownloaded(true);
                                    fontEntity.setFilePath(filePath);
                                    fontEntity.insert();
                                }
                            })
                            .start();
                }
            }
        }
    }

    @Override
    public boolean isCached(int fontId) {
        FontEntity fontEntity = mFontDao.queryDownloadedFontEntityById(fontId);
        return fontEntity != null && !FileUtils.isFileExists(fontEntity.getFilePath());
    }

    @Override
    public void evictAll() {
        List<FontEntity> fontEntities = mFontDao.queryDownloadedFontEntities();
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
