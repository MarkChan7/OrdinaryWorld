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
public class GetFontList extends UseCase<List<Font>, GetFontList.Params> {

    private final FontRepository mFontRepository;

    public GetFontList(FontRepository fontRepository, ThreadExecutor threadExecutor,
            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mFontRepository = fontRepository;
    }

    @Override
    Observable<List<Font>> buildUseCaseObservable(Params params) {
        if (params.downloadStatus == Params.STATUS_DOWNLOADED) {
            return mFontRepository.getDownloadedFonts();
        } else {
            return mFontRepository.getFonts();
        }
    }

    public static final class Params {

        public static final int STATUS_ALL = 0;
        public static final int STATUS_DOWNLOADED = 1;
        public static final int STATUS_ONLINE = 2;

        private final int downloadStatus;

        private Params(int downloadStatus) {
            this.downloadStatus = downloadStatus;
        }

        public static Params forFonts(int downloadStatus) {
            return new Params(downloadStatus);
        }
    }
}
