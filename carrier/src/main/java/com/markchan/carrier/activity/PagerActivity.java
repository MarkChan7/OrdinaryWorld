package com.markchan.carrier.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.markchan.carrier.R;
import com.markchan.carrier.core.PagerView;
import com.markchan.carrier.core.PagerView.OnTextTapListener;
import com.markchan.carrier.event.BackToPanelsEvent;
import com.markchan.carrier.event.PagerViewEventBus;
import com.markchan.carrier.fragment.BgColorAndTexturePanelFragment;
import com.markchan.carrier.fragment.TextPanelFragment;
import com.markchan.carrier.util.Scheme;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    PagerView mPagerView;
    @BindView(R.id.pager_aty_ib_text_panel)
    AppCompatImageButton mTextPanelImageBtn;
    @BindView(R.id.pager_aty_ib_bg_color_panel)
    AppCompatImageButton mBackgroundColorPanelImageBtn;
    @BindView(R.id.pager_aty_ib_bg_photo_panel)
    AppCompatImageButton mBackgroundPhotoPanelImageBtn;
    @BindView(R.id.pager_aty_rl_panels)
    RelativeLayout mPanelsRelativeLayout;
    @BindView(R.id.pager_aty_fl_panel_container)
    FrameLayout mPanelContainerFrameLayout;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private boolean mInConcretePanel;

    private FragmentManager mFragmentManager;

    private TextPanelFragment mTextPanelFragment;
    private BgColorAndTexturePanelFragment mBgColorAndTexturePanelFragment;

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);

        mPagerView.setOnTextTapListener(new OnTextTapListener() {

            @Override
            public void onTextTap(String text) {
                mUiHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mPagerView.setText("Hi Eleanor!");
                    }
                }, 2000);
            }
        });

        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewTypefaceEvent(PagerViewEventBus.TypefaceEvent event) {
        mPagerView.setTypefaceUrl(event.typefaceUrl);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewTextSizeEvent(PagerViewEventBus.TextSizeEvent event) {
        mPagerView.setTextSize(event.textSize);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewTextColorEvent(PagerViewEventBus.TextColorEvent event) {
        mPagerView.setTextColor(event.textColor);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewTextAlphaEvent(PagerViewEventBus.TextAlphaEvent event) {
        mPagerView.setTextAlpha(event.textAlpha);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewTextAlignmentEvent(PagerViewEventBus.TextAlignmentEvent event) {
        mPagerView.setTextAlignment(event.textAlignment);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewTextOffsetEvent(PagerViewEventBus.TextOffsetEvent event) {
        mPagerView.resetTextLocation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewTextureEvent(PagerViewEventBus.TextureEvent event) {
        String textureUrl = event.textureUrl;
        if (Scheme.ofUri(event.textureUrl) == Scheme.DRAWABLE) {
            Glide.with(this)
                    .load(Integer.parseInt(Scheme.DRAWABLE.crop(textureUrl)))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {

                        @Override
                        public void onResourceReady(Bitmap resource,
                                GlideAnimation<? super Bitmap> glideAnimation) {
                            mPagerView.setTextureBitmap(resource);
                        }
                    });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPagerViewBackgroundColorEvent(PagerViewEventBus.BackgroundColorEvent event) {
        mPagerView.setPagerBackgroundColor(event.backgroundColor);
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
                mPanelsRelativeLayout.setVisibility(View.INVISIBLE);
                mPanelContainerFrameLayout.setVisibility(View.VISIBLE);
                if (mTextPanelFragment == null) {
                    mTextPanelFragment = new TextPanelFragment();
                }
                showPanel(mTextPanelFragment);
                break;
            case R.id.pager_aty_ib_bg_color_panel:
                mPanelsRelativeLayout.setVisibility(View.INVISIBLE);
                mPanelContainerFrameLayout.setVisibility(View.VISIBLE);
                if (mBgColorAndTexturePanelFragment == null) {
                    mBgColorAndTexturePanelFragment = new BgColorAndTexturePanelFragment();
                }
                showPanel(mBgColorAndTexturePanelFragment);
                break;
            case R.id.pager_aty_ib_bg_photo_panel:

                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnBackToPanelsEvent(BackToPanelsEvent event) {
        backToPanels();
    }

    private void backToPanels() {
        mPanelContainerFrameLayout.setVisibility(View.GONE);
        mPanelsRelativeLayout.setVisibility(View.VISIBLE);
        mInConcretePanel = false;
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

        mInConcretePanel = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInConcretePanel) {
            backToPanels();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
