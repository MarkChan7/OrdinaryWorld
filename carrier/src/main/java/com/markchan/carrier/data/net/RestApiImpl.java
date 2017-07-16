package com.markchan.carrier.data.net;

import android.content.Context;
import com.markchan.carrier.Constants;
import com.markchan.carrier.data.entity.FontEntity;
import java.io.IOException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mark on 2017/7/16.
 */
public class RestApiImpl implements RestApi {

    private Context mContext;

    private Retrofit mRetrofit;
    private FontService mFontService;

    public RestApiImpl(Context context) {
        mContext = context;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mFontService = mRetrofit.create(FontService.class);
    }

    @Override
    public List<FontEntity> getFontEntities() {
        Call<List<FontEntity>> call = mFontService.getFontEntities();
        try {
            Response<List<FontEntity>> response = call.execute();
            if (response.isSuccessful()) {
                List<FontEntity> fontEntities = response.body();
                if (fontEntities != null && !fontEntities.isEmpty()) {
                    return fontEntities;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
