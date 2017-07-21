package com.markchan.carrier.data.net;

import com.markchan.carrier.data.entity.FontEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mark on 2017/7/16.
 */
public interface RestApi {

    Observable<List<FontEntity>> getFontEntities();
}
