package com.markchan.carrier.panel;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.markchan.carrier.R;
import com.markchan.carrier.core.PagerView;
import com.markchan.carrier.event.PagerViewEventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mark on 2017/7/11.
 */
public class TextAlignmentPanel extends AbsPanel {

    private ImageButton mLeftAlignmentBtn;
    private ImageButton mCenterAlignmentBtn;
    private ImageButton mRightAlignmentBtn;

    public TextAlignmentPanel(Context context, View view) {
        super(context, view);
    }

    @Override
    protected void initView(View view) {
        mLeftAlignmentBtn = (ImageButton) view.findViewById(R.id.text_alignment_vp_item_btn_left);
        mLeftAlignmentBtn.setOnClickListener(this);
        mCenterAlignmentBtn = (ImageButton) view
                .findViewById(R.id.text_alignment_vp_item_btn_center);
        mCenterAlignmentBtn.setOnClickListener(this);
        mRightAlignmentBtn = (ImageButton) view.findViewById(R.id.text_alignment_vp_item_btn_right);
        mRightAlignmentBtn.setOnClickListener(this);
        view.findViewById(R.id.text_alignment_vp_item_btn_restore_default_location)
                .setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_alignment_vp_item_btn_left:
                EventBus.getDefault().post(new PagerViewEventBus.TextAlignmentEvent(
                        PagerView.TEXT_ALIGNMENT_LEFT));
                break;
            case R.id.text_alignment_vp_item_btn_center:
                EventBus.getDefault().post(new PagerViewEventBus.TextAlignmentEvent(
                        PagerView.TEXT_ALIGNMENT_CENTER));
                break;
            case R.id.text_alignment_vp_item_btn_right:
                EventBus.getDefault().post(new PagerViewEventBus.TextAlignmentEvent(
                        PagerView.TEXT_ALIGNMENT_RIGHT));
                break;
            case R.id.text_alignment_vp_item_btn_restore_default_location:
                EventBus.getDefault().post(new PagerViewEventBus.TextOffsetEvent());
                break;
            default:
                break;
        }
    }
}
