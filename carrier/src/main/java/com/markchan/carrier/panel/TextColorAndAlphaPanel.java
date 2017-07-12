package com.markchan.carrier.panel;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.markchan.carrier.R;
import com.markchan.carrier.event.PagerViewEventBus;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class TextColorAndAlphaPanel extends AbsPanel {

    private int[] mTextColorArr;

    public TextColorAndAlphaPanel(Context context, View view) {
        super(context, view);
        mTextColorArr = context.getResources().getIntArray(R.array.text_panel_array_text_color);
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.text_color_and_alpha_vp_item_btn_text_color_1)
                .setOnClickListener(this);
        view.findViewById(R.id.text_color_and_alpha_vp_item_btn_text_color_2)
                .setOnClickListener(this);
        view.findViewById(R.id.text_color_and_alpha_vp_item_btn_text_color_3)
                .setOnClickListener(this);
        view.findViewById(R.id.text_color_and_alpha_vp_item_btn_text_color_4)
                .setOnClickListener(this);
        view.findViewById(R.id.text_color_and_alpha_vp_item_btn_text_color_5)
                .setOnClickListener(this);

        AppCompatSeekBar seekBar = (AppCompatSeekBar) view
                .findViewById(R.id.text_color_and_alpha_vp_item_sb);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                EventBus.getDefault().post(new PagerViewEventBus.TextAlphaEvent(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // no-op by default
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // no-op by default
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_color_and_alpha_vp_item_btn_text_color_1:
                EventBus.getDefault().post(new PagerViewEventBus.TextColorEvent(mTextColorArr[0]));
                break;
            case R.id.text_color_and_alpha_vp_item_btn_text_color_2:
                EventBus.getDefault().post(new PagerViewEventBus.TextColorEvent(mTextColorArr[1]));
                break;
            case R.id.text_color_and_alpha_vp_item_btn_text_color_3:
                EventBus.getDefault().post(new PagerViewEventBus.TextColorEvent(mTextColorArr[2]));
                break;
            case R.id.text_color_and_alpha_vp_item_btn_text_color_4:
                EventBus.getDefault().post(new PagerViewEventBus.TextColorEvent(mTextColorArr[3]));
                break;
            case R.id.text_color_and_alpha_vp_item_btn_text_color_5:
                EventBus.getDefault().post(new PagerViewEventBus.TextColorEvent(mTextColorArr[4]));
                break;
            default:
                break;
        }
    }
}
