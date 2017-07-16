package com.markchan.carrier.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.markchan.carrier.R;
import com.markchan.carrier.adapter.BackgroundColorPagerAdapter;
import com.markchan.carrier.adapter.TexturePagerAdapter;
import com.markchan.carrier.model.BackgroundColorModel;
import com.markchan.carrier.model.TextureModel;
import com.markchan.carrier.event.BackToPanelsEvent;
import com.markchan.carrier.event.PagerViewEventBus;
import com.markchan.carrier.util.Scheme;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mark on 2017/7/12.
 */
public class BgColorAndTexturePanelFragment extends Fragment {

    @BindView(R.id.bg_color_frg_vp_bg_color)
    ViewPager mColorViewPager;
    @BindView(R.id.bg_color_frg_vp_texture)
    ViewPager mTextureViewPager;
    @BindView(R.id.bg_color_frg_btn_confirm)
    AppCompatButton mConfirmBtn;

    private Unbinder mUnbinder;

    private List<BackgroundColorModel> mBackgroundColorModels;

    private List<TextureModel> mTextureModels;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_bg_color_and_texture_panel, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        // ---------------------------------------------------------------------------------------------

        int padding = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(72)) / 2;

        mColorViewPager.setPadding(padding, 0, padding, 0);
        mTextureViewPager.setPadding(padding, 0, padding, 0);

        // ---------------------------------------------------------------------------------------------

        mBackgroundColorModels = new ArrayList<>();
        String[] colorNameArr = getResources()
                .getStringArray(R.array.background_color_panel_array_color_name);
        int[] colorArr = getResources().getIntArray(R.array.background_color_panel_array_color);
        for (int i = 0; i < colorNameArr.length; i++) {
            mBackgroundColorModels.add(new BackgroundColorModel(colorNameArr[i], colorArr[i]));
        }
        BackgroundColorPagerAdapter colorAdapter = new BackgroundColorPagerAdapter(getActivity(),
                mBackgroundColorModels);
        mColorViewPager.setAdapter(colorAdapter);
        mColorViewPager.setOffscreenPageLimit(colorNameArr.length);
        mColorViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                // no-op by default
            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(new PagerViewEventBus.BackgroundColorEvent(
                        mBackgroundColorModels.get(position).getColor()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // no-op by default
            }
        });

        mTextureModels = new ArrayList<>();
        String[] textureNameArr = getResources()
                .getStringArray(R.array.background_color_panel_array_texture_name);
        String[] textureDrawableNameArr = getResources()
                .getStringArray(R.array.background_color_panel_array_texture_drawable_name);
        for (int i = 0; i < textureNameArr.length; i++) {
            String textureName = textureNameArr[i];
            if (i == 0) {
                mTextureModels.add(TextureModel.createPureTexture(textureName));
            } else {
                int drawableResId = getResources()
                        .getIdentifier(textureDrawableNameArr[i - 1], "drawable",
                                AppUtils.getAppPackageName());
                mTextureModels.add(new TextureModel(textureName,
                        Scheme.DRAWABLE.wrap(drawableResId + "")));
            }
        }
        TexturePagerAdapter textureAdapter = new TexturePagerAdapter(getActivity(), mTextureModels);
        mTextureViewPager.setAdapter(textureAdapter);
        mTextureViewPager.setOffscreenPageLimit(textureNameArr.length);
        mTextureViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                // no-op by default
            }

            @Override
            public void onPageSelected(int position) {
                EventBus.getDefault().post(new PagerViewEventBus.TextureEvent(
                        mTextureModels.get(position).getUrl()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // no-op by default
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.bg_color_frg_btn_confirm)
    public void onViewClicked() {
        EventBus.getDefault().post(new BackToPanelsEvent());
    }
}
