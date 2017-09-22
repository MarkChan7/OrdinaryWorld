package com.markchan.carrier.data.net.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.markchan.carrier.data.CarrierDataConstant;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.data.exception.NetworkConnectionException;
import com.markchan.carrier.data.net.RestApi;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Mark on 2017/7/16.
 */
public class RestApiImpl implements RestApi {

    interface FontService {

        @GET("v1/jiantu_font.json")
        Call<List<FontEntity>> getFontEntities();
    }

    private Context mContext;

    private Retrofit mRetrofit;
    private FontService mFontService;

    public RestApiImpl(Context context) {
        mContext = context;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(CarrierDataConstant.BASE_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mFontService = mRetrofit.create(FontService.class);
    }

    @Override
    public Observable<List<FontEntity>> getFontEntities() {
        return Observable.create(new ObservableOnSubscribe<List<FontEntity>>() {

            @Override
            public void subscribe(ObservableEmitter<List<FontEntity>> e) throws Exception {
                if (isNetworkConnected()) {
                    Call<List<FontEntity>> call = mFontService.getFontEntities();
                    try {
                        Response<List<FontEntity>> response = call.execute();
                        if (response.isSuccessful()) {
                            List<FontEntity> fontEntities = response.body();
                            if (fontEntities != null && !fontEntities.isEmpty()) {
                                e.onNext(fontEntities);
                                e.onComplete();
                            } else {
                                e.onError(new NetworkConnectionException());
                            }
                        }
                    } catch (Exception exception) {
                        e.onError(new NetworkConnectionException(exception.getCause()));
                    }
                } else {
                    e.onError(new NetworkConnectionException());
                }
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
