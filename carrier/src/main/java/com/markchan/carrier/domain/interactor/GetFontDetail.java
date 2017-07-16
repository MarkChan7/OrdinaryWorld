package com.markchan.carrier.domain.interactor;

import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.executor.PostExecutionThread;
import com.markchan.carrier.domain.executor.ThreadExecutor;
import com.markchan.carrier.domain.repository.FontRepository;
import io.reactivex.Observable;

/**
 * Created by Mark on 2017/7/16.
 */
public class GetFontDetail extends UseCase<Font, GetFontDetail.Params> {

    private final FontRepository mFontRepository;

    GetFontDetail(FontRepository fontRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.mFontRepository = fontRepository;
    }

    @Override
    Observable<Font> buildUseCaseObservable(Params params) {
        if (params == null) {
            throw new NullPointerException();
        }
        return mFontRepository.getFont(params.fontId);
    }

    public static final class Params {

        private final int fontId;

        private Params(int fontId) {
            this.fontId = fontId;
        }

        public static Params forFont(int fontId) {
            return new Params(fontId);
        }
    }
}
