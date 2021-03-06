package com.markchan.carrier.presenter.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.markchan.carrier.R;
import com.markchan.carrier.presenter.core.PagerView.TextAlignment;
import com.markchan.carrier.presenter.view.adapter.TextPanelPagerAdapter;
import com.markchan.carrier.presenter.event.BackToPanelsEvent;
import com.markchan.carrier.presenter.view.panel.TextAlignmentPanel;
import com.markchan.carrier.presenter.view.panel.TextColorAndAlphaPanel;
import com.markchan.carrier.presenter.view.panel.TextSizePanel;
import com.markchan.carrier.presenter.view.panel.TypefacePanel;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/11
 */
public class TextPanelFragment extends Fragment {

    @BindView(R.id.text_panel_fragment_vp_tab)
    SmartTabLayout mViewPagerTab;
    @BindView(R.id.text_panel_fragment_vp)
    ViewPager mViewPager;

    private Unbinder mUnbinder;

    private TextPanelPagerAdapter mTextPanelPagerAdapter;
    private List<View> mViews;

    private TextAlignmentPanel mTextAlignmentPanel;

    public void showTextAlignmentPanel(@TextAlignment int textAlignment) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(2, false);
            mTextAlignmentPanel.setCurrentTextAlignment(textAlignment);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_panel, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        mViews = new ArrayList<>();
        View typefacePanelView = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_vp_typeface, container, false);
        TypefacePanel typefacePanel = new TypefacePanel(getActivity(), typefacePanelView);
        mViews.add(typefacePanelView);

        View textSizePanelView = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_vp_text_size, container, false);
        TextSizePanel textSizePanel = new TextSizePanel(getActivity(), textSizePanelView);
        mViews.add(textSizePanelView);

        View textAlignmentPanelView = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_vp_text_alignment, container, false);
        mTextAlignmentPanel = new TextAlignmentPanel(getActivity(),
                textAlignmentPanelView);
        mViews.add(textAlignmentPanelView);

        View textColorPanel = LayoutInflater.from(getActivity())
                .inflate(R.layout.item_vp_text_color_and_alpha, container, false);
        TextColorAndAlphaPanel textColorAndAlphaPanel = new TextColorAndAlphaPanel(getActivity(),
                textColorPanel);
        mViews.add(textColorPanel);

        mTextPanelPagerAdapter = new TextPanelPagerAdapter(getActivity(), mViews);

        mViewPager.setAdapter(mTextPanelPagerAdapter);

        mViewPagerTab.setViewPager(mViewPager);

        return view;
    }

    @OnClick({R.id.text_panel_fragment_acbtn_complete})
    public void complete() {
        EventBus.getDefault().post(new BackToPanelsEvent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
    }
}
