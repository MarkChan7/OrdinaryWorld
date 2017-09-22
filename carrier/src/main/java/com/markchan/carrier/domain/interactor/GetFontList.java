package com.markchan.carrier.domain.interactor;

import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.executor.PostExecutionThread;
import com.markchan.carrier.domain.executor.ThreadExecutor;
import com.markchan.carrier.domain.repository.FontRepository;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Mark on 2017/7/16.
 */
public class GetFontList extends UseCase<List<Font>, GetFontList.Params> {

    private final FontRepository mFontRepository;

    public GetFontList(FontRepository fontRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFontRepository = fontRepository;
    }

    @Override
    Observable<List<Font>> buildUseCaseObservable(Params params) {
        return mFontRepository.getFonts(params.dataSource);
    }

    public static final class Params {

        public static Params create(int dataSource) {
            return new Params(dataSource);
        }

        private final int dataSource;

        private Params(int dataSource) {
            this.dataSource = dataSource;
        }
    }
}
