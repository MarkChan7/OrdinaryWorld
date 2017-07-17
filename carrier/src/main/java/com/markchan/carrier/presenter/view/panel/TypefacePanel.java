package com.markchan.carrier.presenter.view.panel;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.markchan.carrier.Middleware;
import com.markchan.carrier.R;
import com.markchan.carrier.core.PagerView;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.interactor.DefaultObserver;
import com.markchan.carrier.domain.interactor.GetFontList;
import com.markchan.carrier.domain.interactor.GetFontList.Params;
import com.markchan.carrier.event.PagerViewEventBus;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.presenter.view.activity.FontManagerActivity;
import com.markchan.carrier.widget.wheelpicker.WheelPicker;
import com.markchan.carrier.widget.wheelpicker.WheelPicker.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class TypefacePanel extends AbsPanel implements OnItemSelectedListener {

    private WheelPicker mWheelPicker;
    private List<FontModel> mFontModels;

    public TypefacePanel(Context context, View view) {
        super(context, view);
    }

    @Override
    protected void initView(View view) {
        mWheelPicker = (WheelPicker) view.findViewById(R.id.typeface_vp_item_wp);
        mWheelPicker.setOnItemSelectedListener(this);
    }

    @Override
    protected void initData() {
        GetFontList useCase = new GetFontList(Middleware.getDefault().getFontRepository(),
                Middleware.getDefault().getThreadExecutor(),
                Middleware.getDefault().getPostExecutionThread());

        useCase.execute(new FontListObserver(), Params.forFonts(Params.STATUS_DOWNLOADED));
    }

    private final class FontListObserver extends DefaultObserver<List<Font>> {

        @Override
        public void onNext(List<Font> fonts) {
            if (fonts != null && !fonts.isEmpty()) {
                FontModelDataMapper mapper = Middleware.getDefault()
                        .getFontModelDataMapper();
                mFontModels = mapper.transform(fonts);

                List<String> fontNames = new ArrayList<>();
                fontNames.add("默认");
                for (FontModel fontModel : mFontModels) {
                    fontNames.add(fontModel.getDisplayName());
                }
                fontNames.add("字体下载与管理");
                mWheelPicker.setData(fontNames);
            }
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
            mContext.startActivity(new Intent(mContext, FontManagerActivity.class));
        } else {
            EventBus.getDefault()
                    .post(new PagerViewEventBus.TypefaceEvent(
                            mFontModels.get(position - 1).getUri()));
        }
    }
}