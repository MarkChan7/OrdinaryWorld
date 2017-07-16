package com.markchan.carrier.domain.interactor;

import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.executor.PostExecutionThread;
import com.markchan.carrier.domain.executor.ThreadExecutor;
import com.markchan.carrier.domain.repository.FontRepository;
import io.reactivex.Observable;
import java.util.List;

/**
 * Created by Mark on 2017/7/16.
 */
public class GetFontList extends UseCase<List<Font>, Void> {

    private final FontRepository mFontRepository;

    public GetFontList(FontRepository fontRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFontRepository = fontRepository;
    }

    @Override
    Observable<List<Font>> buildUseCaseObservable(Void unused) {
        return mFontRepository.getFonts();
    }
}
