package com.markchan.carrier.presenter.view.panel;

import android.content.Context;
import android.view.View;

import com.markchan.carrier.Middleware;
import com.markchan.carrier.R;
import com.markchan.carrier.domain.Font;
import com.markchan.carrier.domain.interactor.DefaultObserver;
import com.markchan.carrier.domain.interactor.GetFontList;
import com.markchan.carrier.domain.interactor.GetFontList.Params;
import com.markchan.carrier.event.PagerViewEventBus;
import com.markchan.carrier.presenter.mapper.FontModelDataMapper;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.widget.wheelpicker.WheelPicker;
import com.markchan.carrier.widget.wheelpicker.WheelPicker.OnItemSelectedListener;

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
        mFontModels = new ArrayList<>();

        GetFontList useCase = new GetFontList(Middleware.getDefault().getFontRepository(),
                Middleware.getDefault().getThreadExecutor(),
                Middleware.getDefault().getPostExecutionThread());

        useCase.execute(new FontListObserver(), Params.forFonts(Params.STATUS_DOWNLOADED));
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

                mFontModels.addAll(fontModels);

                List<String> fontNames = new ArrayList<>();
                for (FontModel fontModel : fontModels) {
                    fontNames.add(fontModel.getDisplayName());
                }
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
        EventBus.getDefault()
                .post(new PagerViewEventBus.TypefaceEvent(mFontModels.get(position).getUri()));
    }
}