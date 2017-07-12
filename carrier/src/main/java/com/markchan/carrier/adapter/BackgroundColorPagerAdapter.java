package com.markchan.carrier.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.markchan.carrier.R;
import com.markchan.carrier.domain.BackgroundColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class BackgroundColorPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<BackgroundColor> mData;
    private List<View> mViews;

    public BackgroundColorPagerAdapter(Context context, List<BackgroundColor> data) {
        mContext = context;
        mData = data == null ? new ArrayList<BackgroundColor>() : data;
        mViews = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_vp_background_color, null);

        TextView textView = (TextView) view.findViewById(R.id.bg_color_vp_item_tv);
        textView.setText(mData.get(position).getName());

        view.findViewById(R.id.bg_color_vp_item_rl_root)
                .setBackgroundColor(mData.get(position).getColor());

        mViews.add(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
