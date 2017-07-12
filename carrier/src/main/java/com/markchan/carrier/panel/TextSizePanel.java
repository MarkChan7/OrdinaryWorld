package com.markchan.carrier.panel;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.markchan.carrier.R;
import com.markchan.carrier.event.PagerViewEventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class TextSizePanel extends AbsPanel {

    private Button mTextSizeVpItemBtnSmall;
    private Button mTextSizeVpItemBtnMedium;
    private Button mTextSizeVpItemBtnStandard;
    private Button mTextSizeVpItemBtnLarge;

    private float mSmallTextSize;
    private float mMediumTextSize;
    private float mStandardTextSize;
    private float mLargeTextSize;

    public TextSizePanel(Context context, View view) {
        super(context, view);
        mSmallTextSize = context.getResources().getDimension(R.dimen.text_size_small);
        mMediumTextSize = context.getResources().getDimension(R.dimen.text_size_medium);
        mStandardTextSize = context.getResources().getDimension(R.dimen.text_size_standard);
        mLargeTextSize = context.getResources().getDimension(R.dimen.text_size_large);
    }

    @Override
    protected void initView(View view) {
        mTextSizeVpItemBtnSmall = (Button) view.findViewById(R.id.text_size_vp_item_btn_small);
        mTextSizeVpItemBtnMedium = (Button) view.findViewById(R.id.text_size_vp_item_btn_medium);
        mTextSizeVpItemBtnStandard = (Button) view
                .findViewById(R.id.text_size_vp_item_btn_standard);
        mTextSizeVpItemBtnLarge = (Button) view.findViewById(R.id.text_size_vp_item_btn_large);

        mTextSizeVpItemBtnSmall.setOnClickListener(this);
        mTextSizeVpItemBtnMedium.setOnClickListener(this);
        mTextSizeVpItemBtnStandard.setOnClickListener(this);
        mTextSizeVpItemBtnLarge.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_size_vp_item_btn_small:
                EventBus.getDefault().post(new PagerViewEventBus.TextSizeEvent(mSmallTextSize));
                break;
            case R.id.text_size_vp_item_btn_medium:
                EventBus.getDefault().post(new PagerViewEventBus.TextSizeEvent(mMediumTextSize));
                break;
            case R.id.text_size_vp_item_btn_standard:
                EventBus.getDefault().post(new PagerViewEventBus.TextSizeEvent(mStandardTextSize));
                break;
            case R.id.text_size_vp_item_btn_large:
                EventBus.getDefault().post(new PagerViewEventBus.TextSizeEvent(mLargeTextSize));
                break;
            default:
                break;
        }
    }
}