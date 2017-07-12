package com.markchan.carrier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.markchan.carrier.panel.BackgroundColorPanelFragment;
import com.markchan.carrier.panel.TextPanelFragment;

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

    private boolean mIntoPanel;

    private FragmentManager mFragmentManager;

    private TextPanelFragment mTextPanelFragment;
    private BackgroundColorPanelFragment mBackgroundColorPanelFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
    }

    @OnClick({R.id.pager_aty_acib_discard, R.id.pager_aty_acib_save, R.id.pager_aty_ib_text_panel,
            R.id.pager_aty_ib_bg_color_panel, R.id.pager_aty_ib_bg_photo_panel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pager_aty_acib_discard:
                finish();
                break;
            case R.id.pager_aty_acib_save:
                ToastUtils.showShortToast("Wait for minute");
                break;
            case R.id.pager_aty_ib_text_panel:
                mPanelsLinearLayout.setVisibility(View.INVISIBLE);
                mPanelContainerFrameLayout.setVisibility(View.VISIBLE);
                if (mTextPanelFragment == null) {
                    mTextPanelFragment = new TextPanelFragment();
                }
                showPanel(mTextPanelFragment);
                break;
            case R.id.pager_aty_ib_bg_color_panel:
                mPanelsLinearLayout.setVisibility(View.INVISIBLE);
                mPanelContainerFrameLayout.setVisibility(View.VISIBLE);
                if (mBackgroundColorPanelFragment != null) {
                    mBackgroundColorPanelFragment = new BackgroundColorPanelFragment();
                }
                showPanel(mBackgroundColorPanelFragment);
                break;
            case R.id.pager_aty_ib_bg_photo_panel:

                break;
            default:
                break;
        }
    }

    private void showPanel(Fragment fragment) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        Fragment exitFragment = mFragmentManager
                .findFragmentById(R.id.pager_aty_fl_panel_container);
        if (exitFragment == null) {
            ft.add(R.id.pager_aty_fl_panel_container, fragment);
        } else {
            ft.replace(R.id.pager_aty_fl_panel_container, fragment);
        }
        ft.addToBackStack(null);
        ft.commit();

        mIntoPanel = true;
    }

    @Override
    public void onBackPressed() {
        if (mIntoPanel) {
            mPanelContainerFrameLayout.setVisibility(View.GONE);
            mPanelsLinearLayout.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
