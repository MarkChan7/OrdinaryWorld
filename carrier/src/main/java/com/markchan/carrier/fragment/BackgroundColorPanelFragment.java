package com.markchan.carrier.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.github.lzyzsd.randomcolor.RandomColor;
import com.markchan.carrier.R;
import com.markchan.carrier.adapter.BackgroundColorPagerAdapter;
import com.markchan.carrier.domain.BackgroundColor;
import com.markchan.carrier.event.PagerViewEventBus;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Mark on 2017/7/12.
 */
public class BackgroundColorPanelFragment extends Fragment {

    @BindView(R.id.bg_color_frg_vp_bg_color)
    ViewPager mColorViewPager;
    @BindView(R.id.bg_color_frg_vp_vein)
    ViewPager mVeinViewPager;
    @BindView(R.id.bg_color_frg_btn_confirm)
    AppCompatButton mConfirmBtn;

    private Unbinder mUnbinder;

    private List<BackgroundColor> mBackgroundColors;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background_color_panel, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mBackgroundColors = new ArrayList<>();
        String[] colorNameArr = getResources()
                .getStringArray(R.array.background_color_panel_array_color_name);
        RandomColor randomColor = new RandomColor();
        List<View> views = new ArrayList<>();
        for (String colorName : colorNameArr) {
            views.add(LayoutInflater.from(getActivity()).inflate(R.layout.item_vp_background_color, null));
            mBackgroundColors.add(new BackgroundColor(colorName, randomColor.randomColor()));
        }
        BackgroundColorPagerAdapter colorAdapter = new BackgroundColorPagerAdapter(getActivity(),
                views, mBackgroundColors);
        mColorViewPager.setAdapter(colorAdapter);

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
        EventBus.getDefault().post(new PagerViewEventBus.TextureEvent(""));
    }
}
