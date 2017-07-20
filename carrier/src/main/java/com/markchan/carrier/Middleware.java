package com.markchan.carrier;

import android.annotation.SuppressLint;
import android.content.Context;

import com.markchan.carrier.data.cache.FontEntityCache;
import com.markchan.carrier.data.cache.FontEntityCacheImpl;
import com.markchan.carrier.data.dao.FontEntityDao;
import com.markchan.carrier.data.dao.FontEntityDapImpl;
import com.markchan.carrier.data.entity.FontEntityDataMapper;
import com.markchan.carrier.data.executor.JobExecutor;
import com.markchan.carrier.data.net.RestApi;
import com.markchan.carrier.data.net.RestApiImpl;
import com.markchan.carrier.data.repository.FontDataRepository;
import com.markchan.carrier.data.repository.datasource.FontDataSourceFactory;
import com.markchan.carrier.domain.dao.FontDao;
import com.markchan.carrier.domain.executor.PostExecutionThread;
import com.markchan.carrier.domain.executor.ThreadExecutor;
import com.markchan.carrier.domain.repository.FontRepository;
import com.markchan.carrier.presenter.UiThread;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;

/**
 * Created by Mark on 2017/7/16.
 */
public class Middleware {

    @SuppressLint("StaticFieldLeak")
    private static Middleware INSTANCE;

    public static void handleApplicationContext(Context context) {
        if (INSTANCE == null) {
            synchronized (Middleware.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Middleware(context);
                }
            }
        }
    }

    public static Middleware getDefault() {
        return INSTANCE;
    }

    private Context mContext;

    /**
     * data layer
     */
    private final FontEntityDataMapper mFontEntityDataMapper;
    private final FontEntityDao mFontEntityDao;
    private final RestApi mRestApi;
    private final FontEntityCache mFontEntityCache;
    private final FontDataSourceFactory mFontDataSourceFactory;

    /**
     * domain layer
     */
    private FontRepository mFontRepository;
    private FontDao mFontDao;
    private PostExecutionThread mPostExecutionThread;
    private ThreadExecutor mThreadExecutor;

    /**
     * presenter layer
     */
    private FontModelDataMapper mFontModelDataMapper;

    private Middleware(Context context) {
        mContext = context.getApplicationContext();

        mFontEntityDataMapper = new FontEntityDataMapper();
        mFontEntityDao = new FontEntityDapImpl(mFontEntityDataMapper);
        mRestApi = new RestApiImpl(mContext);
        mFontEntityCache = new FontEntityCacheImpl(mFontEntityDao);
        mFontDataSourceFactory = new FontDataSourceFactory(mContext, mFontEntityCache, mRestApi, mFontEntityDao);

        mFontRepository = new FontDataRepository(mFontDataSourceFactory, mFontEntityDataMapper);
        mFontDao = mFontEntityDao;

        mPostExecutionThread = new UiThread();
        mThreadExecutor = new JobExecutor();

        mFontModelDataMapper = new FontModelDataMapper();
    }

    public FontEntityDao getFontEntityDao() {
        return mFontEntityDao;
    }

    public FontRepository getFontRepository() {
        return mFontRepository;
    }

    public PostExecutionThread getPostExecutionThread() {
        return mPostExecutionThread;
    }

    public ThreadExecutor getThreadExecutor() {
        return mThreadExecutor;
    }

    public FontModelDataMapper getFontModelDataMapper() {
        return mFontModelDataMapper;
    }
}
