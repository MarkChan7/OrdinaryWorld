package com.markchan.carrier.presenter.view.panel;

import android.content.Context;
import android.view.View;
import com.blankj.utilcode.util.ToastUtils;
import com.markchan.carrier.R;
import com.markchan.carrier.presenter.event.PagerViewEventBus;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.presenter.util.Scheme;
import com.markchan.carrier.presenter.widget.wheelpicker.WheelPicker;
import com.markchan.carrier.presenter.widget.wheelpicker.WheelPicker.OnItemSelectedListener;
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
        mFontModels = new ArrayList<>();
        String url = Scheme.ASSETS.wrap("font/code_light.otf");
        for (int i = 1; i <= 5; i++) {
            FontModel fontModel = new FontModel();
            mFontModels.add(fontModel);
        }
        List<String> mFontNames = new ArrayList<>();
        for (int i = 0; i < mFontModels.size(); i++) {
            mFontNames.add(mFontModels.get(i).getDisplayName());
        }
        mWheelPicker.setData(mFontNames);
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
        EventBus.getDefault().post(new PagerViewEventBus.TypefaceEvent(
                Scheme.ASSETS.wrap("font/code_light.otf" + (position % 2 == 0 ? "" : "x"))));
        ToastUtils.showShort(position + "");
    }
}