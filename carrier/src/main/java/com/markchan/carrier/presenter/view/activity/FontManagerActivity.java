package com.markchan.carrier.presenter.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.markchan.carrier.Middleware;
import com.markchan.carrier.R;
import com.markchan.carrier.Scheme;
import com.markchan.carrier.data.entity.FontEntity;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.interactor.DefaultObserver;
import com.markchan.carrier.domain.interactor.GetFontList;
import com.markchan.carrier.domain.interactor.GetFontList.Params;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.presenter.view.adapter.FontManagerAdapter;
import com.markchan.carrier.util.CacheDirHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FontManagerActivity extends AppCompatActivity {

    @BindView(R.id.font_manager_aty_acib_back)
    AppCompatImageButton mBackImageBtn;
    @BindView(R.id.font_manager_aty_rv)
    RecyclerView mRecyclerView;

    private FontManagerAdapter mAdapter;
    private List<FontModel> mFontModels;

    private FontListObserver mFontListObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_manager);
        ButterKnife.bind(this);

        mFontModels = new ArrayList<>();
        mAdapter = new FontManagerAdapter(mFontModels, Glide.with(this));

        mAdapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final FontModel fontModel = mFontModels.get(position);
                String uri = fontModel.getUri();
                Scheme scheme = Scheme.ofUri(uri);
                if (scheme == Scheme.FILE) {
                    // TODO: 2017/7/16 Changing typeface of PagerView
                } else {
                    FileDownloader.getImpl()
                            .create(fontModel.getUri())
                            .setPath(CacheDirHelper
                                    .getExternalCacheDirectory(FontManagerActivity.this, "font")
                                    + File.separator + fontModel.getPostscriptName())
                            .setListener(new FileDownloadSampleListener() {

                                @Override
                                protected void error(BaseDownloadTask task, Throwable e) {
                                    super.error(task, e);
                                    ToastUtils.showShort("Errorrrrrr");
                                }

                                @Override
                                protected void completed(BaseDownloadTask task) {
                                    String targetFilePath = task.getTargetFilePath();
                                    FontModel f = getFontModelByUrlAndFilePath(task.getUrl(),
                                            task.getTargetFilePath());
                                    if (f != null) {
                                        FontEntity fontEntity = Middleware.getDefault()
                                                .getFontEntityDao()
                                                .queryFontEntityById(f.getId());
                                        String uri = Scheme.FILE.wrap(targetFilePath);
                                        if (fontEntity != null) {
                                            fontEntity.setUri(uri);
                                        } else {
                                            fontEntity = new FontEntity();
                                            fontEntity.setId(fontModel.getId());
                                            fontEntity.setDisplayName(fontModel.getDisplayName());
                                            fontEntity.setPostscriptName(
                                                    fontModel.getPostscriptName());
                                            fontEntity.setThumbUrl(fontModel.getThumbUrl());
                                            fontEntity.setUrl(fontModel.getUri());
                                            fontEntity.setUri(uri);
                                        }
                                        if (fontEntity.save()) {
                                            int index = mFontModels.indexOf(f);
                                            f.setUri(uri);
                                            mFontModels.set(index, f);
                                            mAdapter.notifyDataSetChanged();

                                            ToastUtils.showShort("Download Success");
                                        }
                                    }
                                }
                            })
                            .start();
                }
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        GetFontList useCase = new GetFontList(Middleware.getDefault().getFontRepository(),
                Middleware.getDefault().getThreadExecutor(),
                Middleware.getDefault().getPostExecutionThread());

        useCase.execute(new FontListObserver(), Params.forFonts(Params.STATUS_ALL));
    }

    private FontModel getFontModelByUrlAndFilePath(String url, String filePath) {
        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(filePath)) {
            for (FontModel fontModel :
                    mFontModels) {
                String destFilePath = CacheDirHelper
                        .getExternalCacheDirectory(FontManagerActivity.this, "font")
                        + File.separator + fontModel.getPostscriptName();
                if (url.equals(fontModel.getUri()) && filePath.equals(destFilePath)) {
                    return fontModel;
                }
            }
        }
        return null;
    }

    @OnClick(R.id.font_manager_aty_acib_back)
    public void back() {
        finish();
    }

    private final class FontListObserver extends DefaultObserver<List<Font>> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(List<Font> fonts) {
            if (fonts != null && !fonts.isEmpty()) {
                FontModelDataMapper mapper = Middleware.getDefault()
                        .getFontModelDataMapper();
                List<FontModel> fontModels = mapper.transform(fonts);
                mFontModels.clear();
                mFontModels.addAll(fontModels);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
