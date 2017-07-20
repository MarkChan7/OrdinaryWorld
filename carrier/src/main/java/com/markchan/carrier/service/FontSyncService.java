package com.markchan.carrier.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.markchan.carrier.Middleware;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.interactor.DefaultObserver;
import com.markchan.carrier.domain.interactor.GetFontList;
import com.markchan.carrier.domain.interactor.GetFontList.Params;

import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class FontSyncService extends Service {

    public static void startFontSyncService(Context context) {
        Intent intent = new Intent(context, FontSyncService.class);
        context.startService(intent);
    }

    private GetFontList mGetFontList;

    @Override
    public void onCreate() {
        mGetFontList = new GetFontList(Middleware.getDefault().getFontRepository(),
                Middleware.getDefault().getThreadExecutor(),
                Middleware.getDefault().getPostExecutionThread());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mGetFontList.execute(new DefaultObserver<List<Font>>() {

                                 @Override
                                 public void onNext(List<Font> fonts) {
                                     // no-op by default
                                 }
                             },
                GetFontList.Params.forFonts(Params.DATA_SOURCE_ONLINE));

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
