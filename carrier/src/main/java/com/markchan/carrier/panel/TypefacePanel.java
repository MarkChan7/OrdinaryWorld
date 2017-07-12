package com.markchan.carrier.panel;

import android.content.Context;
import android.view.View;

import com.markchan.carrier.R;
import com.markchan.carrier.event.PagerViewEventBus;
import com.markchan.carrier.util.Scheme;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class TypefacePanel extends AbsPanel {

    public TypefacePanel(Context context, View view) {
        super(context, view);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.type_vp_item_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.type_vp_item_btn:
                EventBus.getDefault().post(new PagerViewEventBus.TypefaceEvent(
                        Scheme.ASSETS.wrap("font/code_light.otf")));
                break;
            default:
                break;
        }
    }
}