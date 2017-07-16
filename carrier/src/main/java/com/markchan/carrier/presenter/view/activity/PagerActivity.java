package com.markchan.carrier.presenter.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.florent37.viewanimator.AnimationListener.Start;
import com.github.florent37.viewanimator.AnimationListener.Stop;
import com.github.florent37.viewanimator.ViewAnimator;
import com.markchan.carrier.R;
import com.markchan.carrier.presenter.config.ConfigManager;
import com.markchan.carrier.presenter.core.PagerView;
import com.markchan.carrier.presenter.core.PagerView.OnTextTapListener;
import com.markchan.carrier.presenter.event.BackToPanelsEvent;
import com.markchan.carrier.presenter.event.PagerViewEventBus;
import com.markchan.carrier.presenter.util.Scheme;
import com.markchan.carrier.presenter.util.keyboard.KeyboardHeightObserver;
import com.markchan.carrier.presenter.util.keyboard.KeyboardHeightProvider;
import com.markchan.carrier.presenter.view.fragment.BgColorAndTexturePanelFragment;
import com.markchan.carrier.presenter.view.fragment.TextPanelFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PagerActivity extends AppCompatActivity implements KeyboardHeightObserver {

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @BindView(R.id.pager_aty_rl_root)
    RelativeLayout mRootRelativeLayout;
    @BindView(R.id.pager_aty_ll_content_root)
    LinearLayout mContentRootRelativeLayout;
    @BindView(R.id.pager_aty_rl_title_bar)
    RelativeLayout mTitleBarRelativeLayout;
    @BindView(R.id.pager_aty_acib_discard)
    AppCompatImageButton mDiscardImageBtn;
    @BindView(R.id.pager_aty_actv_title)
    AppCompatTextView mTitleTextView;
    @BindView(R.id.pager_aty_acib_save)
    AppCompatImageButton mSaveImageBtn;
    @BindView(R.id.pager_aty_pager_view)
    PagerView mPagerView;
    @BindView(R.id.pager_aty_rl_panels)
    RelativeLayout mPanelsRelativeLayout;
    @BindView(R.id.pager_aty_acib_text_panel)
    AppCompatImageButton mTextPanelImageBtn;
    @BindView(R.id.pager_aty_acib_bg_color_panel)
    AppCompatImageButton mBackgroundColorPanelImageBtn;
    @BindView(R.id.pager_aty_acib_bg_photo_panel)
    AppCompatImageButton mBackgroundPhotoPanelImageBtn;
    @BindView(R.id.pager_aty_rl_settings_red_point)
    RelativeLayout mSettingsRedPointView;
    @BindView(R.id.pager_aty_fl_panel_container)
    FrameLayout mPanelContainerFrameLayout;
    @BindView(R.id.pager_aty_ll_input)
    LinearLayout mInputLinearLayout;
    @BindView(R.id.pager_aty_acet)
    AppCompatEditText mEditText;
    @BindView(R.id.pager_aty_acib_confirm)
    AppCompatImageButton mConfirmImageBtn;

    private LayoutInflater mInflater;

    private boolean mInConcretePanel;

    private FragmentManager mFragmentManager;

    private TextPanelFragment mTextPanelFragment;
    private BgColorAndTexturePanelFragment mBgColorAndTexturePanelFragment;

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    private KeyboardHeightProvider mKeyboardHeightProvider;

    private String mPagerText;

    private int mKeyBoardHeight;

    private boolean mCloseKbByConfirmFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);
        mInflater = getLayoutInflater();

        mKeyBoardHeight = ConfigManager.getDefault().getKeyboardHeight();
        mKeyboardHeightProvider = new KeyboardHeightProvider(this);
        mRootRelativeLayout.post(new Runnable() {

            public void run() {
                mKeyboardHeightProvider.start();
            }
        });

        initView();

        mFragmentManager = getSupportFragmentManager();
    }

    private void updateInputBottomMargin() {
        RelativeLayout.LayoutParams inputLayoutParams = (RelativeLayout.LayoutParams) mInputLinearLayout
                .getLayoutParams();
        inputLayoutParams.setMargins(0, 0, 0, mKeyBoardHeight);
        mInputLinearLayout.setLayoutParams(inputLayoutParams);
    }

    private void initView() {
        mPagerView.setOnTextTapListener(new OnTextTapListener() {

            @Override
            public void onTextTap(String text) {
                mPagerText = text;

                mInputLinearLayout.setVisibility(View.VISIBLE);
                ViewAnimator.animate(mInputLinearLayout)
                        .alpha(0.0F, 1.0F)
                        .duration(mKeyBoardHeight == 0 ? 0 : 200)
                        .onStop(new Stop() {

                            @Override
                            public void onStop() {
                                KeyboardUtils.showSoftInput(mEditText);
                            }
                        })
                        .thenAnimate(mContentRootRelativeLayout)
                        .startDelay(80)
                        .translationY(-getResources().getDimension(R.dimen.title_bar_height))
                        .duration(120)
                        .start();
            }
        });

        updateInputBottomMargin();

        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op by default
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // no-op by default
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = mEditText.getText().toString();
                if (!TextUtils.isEmpty(text) && !text.equals(mPagerView.getText())) {
                    mPagerView.setText(text);
                }
            }
        });
    }

    @OnClick({R.id.pager_aty_acib_discard, R.id.pager_aty_acib_save, R.id.pager_aty_acib_confirm,
            R.id.pager_aty_acib_text_panel, R.id.pager_aty_acib_bg_color_panel,
            R.id.pager_aty_acib_bg_photo_panel, R.id.pager_aty_acib_settings})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pager_aty_acib_discard:
                finish();
                break;
            case R.id.pager_aty_acib_save:
                ToastUtils.showShort("Wait for minute");
                break;
            case R.id.pager_aty_acib_text_panel:
                if (mTextPanelFragment == null) {
                    mTextPanelFragment = new TextPanelFragment();
                }
                showPanel(getText(R.string.pager_aty_panel_text).toString(), mTextPanelFragment);
                break;
            case R.id.pager_aty_acib_bg_color_panel:
                if (mBgColorAndTexturePanelFragment == null) {
                    mBgColorAndTexturePanelFragment = new BgColorAndTexturePanelFragment();
                }
                showPanel(getText(R.string.pager_aty_panel_bg_color).toString(),
                        mBgColorAndTexturePanelFragment);
                break;
            case R.id.pager_aty_acib_bg_photo_panel:
                mTitleTextView.setText(getText(R.string.pager_aty_panel_bg_photo));
                // TODO: 2017/7/15 Add a photo as background
                break;
            case R.id.pager_aty_acib_confirm:
                mCloseKbByConfirmFlag = true;
                ViewAnimator.animate(mInputLinearLayout)
                        .alpha(0.0F)
                        .duration(200)
                        .onStart(new Start() {

                            @Override
                            public void onStart() {
                                KeyboardUtils.hideSoftInput(PagerActivity.this);
                            }
                        })
                        .thenAnimate(mContentRootRelativeLayout)
                        .translationY(0)
                        .duration(200)
                        .onStop(new Stop() {

                            @Override
                            public void onStop() {
                                mCloseKbByConfirmFlag = false;
                            }
                        })
                        .start();
                break;
            case R.id.pager_aty_acib_settings:
                startActivity(new Intent(this, SettingsActivity.class));
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
        mTitleTextView.setText(getText(R.string.app_name));
        mDiscardImageBtn.setVisibility(View.VISIBLE);
        mSaveImageBtn.setVisibility(View.VISIBLE);
        mPanelContainerFrameLayout.setVisibility(View.GONE);
        mPanelsRelativeLayout.setVisibility(View.VISIBLE);
        mInConcretePanel = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInConcretePanel) {
            backToPanels();
        }
    }

    private void showPanel(String name, Fragment fragment) {
        mTitleTextView.setText(name);

        mDiscardImageBtn.setVisibility(View.INVISIBLE);
        mSaveImageBtn.setVisibility(View.INVISIBLE);

        mPanelsRelativeLayout.setVisibility(View.INVISIBLE);
        mPanelContainerFrameLayout.setVisibility(View.VISIBLE);

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
    public void onKeyboardHeightChanged(int height, int orientation) {
        if ((mKeyBoardHeight == 0 && height != 0)
                || (mKeyBoardHeight != 0 && height != 0 && mKeyBoardHeight != height)) {
            mKeyBoardHeight = height;
            updateInputBottomMargin();
            ConfigManager.getDefault().setKeyboardHeight(height);
        }

        if (height == 0) { // closed
            if (!mCloseKbByConfirmFlag) {
                ViewAnimator.animate(mContentRootRelativeLayout)
                        .translationY(0)
                        .duration(200)
                        .start();
            }
            if (mInputLinearLayout.getVisibility() != View.GONE) {
                mInputLinearLayout.setVisibility(View.GONE);
            }
        } else { // opened
            mEditText.setText(mPagerText);
            if (!TextUtils.isEmpty(mPagerText)) {
                mEditText.setSelection(mPagerText.length());
            }
        }
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mKeyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mKeyboardHeightProvider.setKeyboardHeightObserver(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mKeyboardHeightProvider.close();
    }
}
