package com.markchan.carrier;

import android.annotation.SuppressLint;
import android.content.Context;
import com.markchan.carrier.data.cache.FontCacheImpl;
import com.markchan.carrier.data.database.FontDapImpl;
import com.markchan.carrier.data.entity.FontEntityDataMapper;
import com.markchan.carrier.data.executor.JobExecutor;
import com.markchan.carrier.data.net.RestApiImpl;
import com.markchan.carrier.data.repository.FontDataRepository;
import com.markchan.carrier.data.repository.datasource.FontDataSourceFactory;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.executor.PostExecutionThread;
import com.markchan.carrier.domain.executor.ThreadExecutor;
import com.markchan.carrier.domain.repository.FontRepository;
import com.markchan.carrier.presenter.UiThread;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;
import io.reactivex.Observable;
import java.util.List;

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
     * domain layer
     */
    private FontRepository mFontRepository;
    private PostExecutionThread mPostExecutionThread;
    private ThreadExecutor mThreadExecutor;

    /**
     * presenter layer
     */
    private FontModelDataMapper mFontModelDataMapper;

    public Middleware(Context context) {
        mContext = context.getApplicationContext();
        mFontRepository = new FontDataRepository(new FontDataSourceFactory(mContext,
                new FontCacheImpl(mContext, new FontDapImpl()),
                new RestApiImpl()),
                new FontEntityDataMapper());

        mPostExecutionThread = new UiThread();
        mThreadExecutor = new JobExecutor();

        mFontModelDataMapper = new FontModelDataMapper();
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

    // ---------------------------------------------------------------------------------------------

    public Observable<List<Font>> getFontModels() {
        return mFontRepository.getFonts();
    }
}
