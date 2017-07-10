package com.markchan.ordinaryworld.panel;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.markchan.ordinaryworld.PagerView;
import com.markchan.ordinaryworld.R;

/**
 * Created by Mark on 2017/7/11.
 */
public class TextAlignmentPanel implements OnClickListener {

    private final PagerView mPagerView;
    private final View mRootView;

    private Button mLeftBtn;
    private Button mCenterBtn;
    private Button mRightBtn;

    public TextAlignmentPanel(PagerView pagerView, View rootView) {
        mPagerView = pagerView;
        mRootView = rootView;
        mLeftBtn = (Button) rootView.findViewById(R.id.text_alignment_vp_item_btn_left);
        mLeftBtn.setOnClickListener(this);
        mCenterBtn = (Button) rootView.findViewById(R.id.text_alignment_vp_item_btn_center);
        mCenterBtn.setOnClickListener(this);
        mRightBtn = (Button) rootView.findViewById(R.id.text_alignment_vp_item_btn_right);
        mRightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_alignment_vp_item_btn_left:
                mPagerView.setTextAlignment(PagerView.TEXT_ALIGNMENT_LEFT);
                break;
            case R.id.text_alignment_vp_item_btn_center:
                mPagerView.setTextAlignment(PagerView.TEXT_ALIGNMENT_CENTER);
                break;
            case R.id.text_alignment_vp_item_btn_right:
                mPagerView.setTextAlignment(PagerView.TEXT_ALIGNMENT_RIGHT);
                break;
            default:
                break;
        }
    }
}
