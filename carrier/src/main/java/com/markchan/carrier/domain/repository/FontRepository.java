package com.markchan.carrier.domain.repository;

import com.markchan.carrier.domain.Font;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontRepository {

    Observable<Font> getFont(final int fontId);

    Observable<List<Font>> getFonts(int dataSource);
}
