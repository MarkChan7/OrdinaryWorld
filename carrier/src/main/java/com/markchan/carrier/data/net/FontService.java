package com.markchan.carrier.data.net;

import com.markchan.carrier.data.entity.FontEntity;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontService {

    @GET
    Call<List<FontEntity>> getFontEntities();
}
