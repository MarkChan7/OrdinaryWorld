package com.markchan.carrier.panel;

import android.content.Context;
import android.view.View;

import com.markchan.carrier.R;
import com.markchan.carrier.event.PagerViewEventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class TextColorAndAlphaPanel extends AbsPanel {

    public TextColorAndAlphaPanel(Context context, View view) {
        super(context, view);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.text_color_and_alpha_vp_item_btn_color).setOnClickListener(this);
        view.findViewById(R.id.text_color_and_alpha_vp_item_btn_alpha).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_color_and_alpha_vp_item_btn_color:
                EventBus.getDefault().post(new PagerViewEventBus.TextColorEvent(0xffff00));
                break;
            case R.id.text_color_and_alpha_vp_item_btn_alpha:
                EventBus.getDefault().post(new PagerViewEventBus.TextAlphaEvent(127));
                break;
            default:
                break;
        }
    }
}
