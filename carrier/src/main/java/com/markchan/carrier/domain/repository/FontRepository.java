package com.markchan.carrier.domain.repository;

import com.markchan.carrier.domain.Font;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public interface FontRepository {

    Observable<Font> getFont(final int fontId);

    Observable<List<Font>> getFonts();
}
