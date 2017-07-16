package com.markchan.carrier.presenter.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.markchan.carrier.Middleware;
import com.markchan.carrier.R;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.interactor.DefaultObserver;
import com.markchan.carrier.domain.interactor.GetFontList;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.presenter.view.adapter.FontManagerAdapter;
import java.util.ArrayList;
import java.util.List;

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

            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        GetFontList useCase = new GetFontList(Middleware.getDefault().getFontRepository(),
                Middleware.getDefault().getThreadExecutor(),
                Middleware.getDefault().getPostExecutionThread());

        useCase.execute(new FontListObserver(), null);
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
