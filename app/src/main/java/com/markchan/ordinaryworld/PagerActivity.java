package com.markchan.ordinaryworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PagerActivity extends AppCompatActivity {

    @BindView(R.id.pager_aty_acib_discard)
    AppCompatImageButton mImageBtn;
    @BindView(R.id.pager_aty_tv_title)
    TextView mTitleTextView;
    @BindView(R.id.pager_aty_acib_save)
    AppCompatImageButton mSaveImageBtn;
    @BindView(R.id.pager_aty_pager_view)
    PagerView mPagerAtyPagerView;
    @BindView(R.id.pager_aty_ib_text_panel)
    AppCompatImageButton mTextPanelImageBtn;
    @BindView(R.id.pager_aty_ib_bg_color_panel)
    AppCompatImageButton mBackgroundColorPanelImageBtn;
    @BindView(R.id.pager_aty_ib_bg_photo_panel)
    AppCompatImageButton mBackgroundPhotoPanelImageBtn;
    @BindView(R.id.pager_aty_ll_panels)
    LinearLayout mPanelsLinearLayout;
    @BindView(R.id.pager_aty_fl_panel_container)
    FrameLayout mPanelContainerFrameLayout;

//    private ViewPager mTextPanelViewPager;
//    private View mTextAlignmentPanelView;

//    private TextAlignmentPanel mTextAlignmentPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);
//        mPagerView = (PagerView) findViewById(R.id.pager_aty_pager_view);
//        mTextAlignmentPanelView = findViewById(R.id.pager_aty_view_text_alignment_panel);
//        mTextAlignmentPanel = new TextAlignmentPanel(mPagerView, mTextAlignmentPanelView);
    }

    @OnClick({R.id.pager_aty_acib_discard, R.id.pager_aty_acib_save, R.id.pager_aty_ib_text_panel,
            R.id.pager_aty_ib_bg_color_panel, R.id.pager_aty_ib_bg_photo_panel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pager_aty_acib_discard:
                break;
            case R.id.pager_aty_acib_save:
                break;
            case R.id.pager_aty_ib_text_panel:

                break;
            case R.id.pager_aty_ib_bg_color_panel:

                break;
            case R.id.pager_aty_ib_bg_photo_panel:

                break;
            default:
                break;
        }
    }
}
