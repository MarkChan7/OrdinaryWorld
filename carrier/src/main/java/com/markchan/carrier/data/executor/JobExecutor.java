package com.markchan.carrier.data.executor;

import android.support.annotation.NonNull;
import com.markchan.carrier.domain.executor.ThreadExecutor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by Mark on 2017/7/16.
 */
public class JobExecutor implements ThreadExecutor {

    private final ExecutorService mThreadPoolExecutor;

    public JobExecutor() {
        mThreadPoolExecutor = Executors.newCachedThreadPool(new JobThreadFactory());
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        mThreadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {

        private int counter = 0;

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "jiantu_" + counter++);
        }
    }
}

