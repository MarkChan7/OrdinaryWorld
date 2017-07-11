package com.markchan.ordinaryworld.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.markchan.ordinaryworld.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/11
 */
public class TextPanelPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> mViews;

    public TextPanelPagerAdapter(Context context, List<View> views) {
        mContext = context;
        mViews = views == null ? new ArrayList<View>() : views;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getStringArray(R.array.text_panel_title_array)[position];
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        container.addView(mViews.get(position));
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }
}
