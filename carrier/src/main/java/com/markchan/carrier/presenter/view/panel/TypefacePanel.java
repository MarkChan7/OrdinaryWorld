package com.markchan.carrier.presenter.view.panel;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.markchan.carrier.Middleware;
import com.markchan.carrier.R;
import com.markchan.carrier.presenter.core.PagerView;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.interactor.DefaultObserver;
import com.markchan.carrier.domain.interactor.GetFontList;
import com.markchan.carrier.domain.interactor.GetFontList.Params;
import com.markchan.carrier.presenter.event.PagerViewEventBus;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.presenter.view.activity.FontManagerActivity;
import com.markchan.carrier.presenter.view.adapter.TypefaceAdapter;
import com.markchan.carrier.presenter.widget.wheelpicker.WheelPicker;
import com.markchan.carrier.presenter.widget.wheelpicker.WheelPicker.OnItemSelectedListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class TypefacePanel extends AbsPanel implements OnItemSelectedListener {

    private WheelPicker mWheelPicker;
    private List<FontModel> mFontModels;

    private RecyclerView mRecyclerView;
    private TypefaceAdapter mAdapter;

    public TypefacePanel(Context context, View view) {
        super(context, view);
    }

    @Override
    protected void initView(View view) {
        mWheelPicker = (WheelPicker) view.findViewById(R.id.typeface_vp_item_wp);
        mWheelPicker.setOnItemSelectedListener(this);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.typeface_vp_item_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mFontModels = new ArrayList<>();
        mAdapter = new TypefaceAdapter(mFontModels);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addHeaderView(
                LayoutInflater.from(mContext).inflate(R.layout.item_list_typeface_header, null));
        View footerView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list_typeface_footer, null);
        mAdapter.addFooterView(footerView);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (adapter.getItemViewType(position) == BaseQuickAdapter.FOOTER_VIEW) {
                    FontManagerActivity.navigateToFontManagerActivity(mContext);
                }
            }
        });
    }

    @Override
    protected void initData() {
        GetFontList useCase = new GetFontList(Middleware.getDefault().getFontRepository(),
                Middleware.getDefault().getThreadExecutor(),
                Middleware.getDefault().getPostExecutionThread());

        useCase.execute(new FontListObserver(), Params.forFonts(Params.DATA_SOURCE_DOWNLOADED));
    }

    private final class FontListObserver extends DefaultObserver<List<Font>> {

        @Override
        public void onNext(List<Font> fonts) {
            if (fonts != null && !fonts.isEmpty()) {
                FontModelDataMapper mapper = Middleware.getDefault().getFontModelDataMapper();
                List<FontModel> fontModels = mapper.transform(fonts);
                if (fontModels != null && !fontModels.isEmpty()) {
                    mFontModels.clear();
                    mFontModels.addAll(fontModels);
                    mAdapter.notifyDataSetChanged();
                }
            }

//            if (fonts != null && !fonts.isEmpty()) {
//                FontModelDataMapper mapper = Middleware.getDefault()
//                        .getFontModelDataMapper();
//                mFontModels = mapper.transform(fonts);
//
//                List<String> fontNames = new ArrayList<>();
//                fontNames.add("默认");
//                for (FontModel fontModel : mFontModels) {
//                    fontNames.add(fontModel.getDisplayName());
//                }
//                fontNames.add("字体下载与管理");
//                mWheelPicker.setData(fontNames);
//            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        if (position == 0) { // default
            EventBus.getDefault()
                    .post(new PagerViewEventBus.TypefaceEvent(PagerView.FAKE_DEFAULT_TYPEFACE_URI));
        } else if (position == mFontModels.size() + 1) { // font download and manager
            FontManagerActivity.navigateToFontManagerActivity(mContext);
        } else {
            EventBus.getDefault()
                    .post(new PagerViewEventBus.TypefaceEvent(
                            mFontModels.get(position - 1).getUri()));
        }
    }
}