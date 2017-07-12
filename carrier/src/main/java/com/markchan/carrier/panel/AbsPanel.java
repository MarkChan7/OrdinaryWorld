package com.markchan.carrier.panel;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public abstract class AbsPanel implements OnClickListener {

    protected Context mContext;
    protected View mView;

    public AbsPanel(Context context, View view) {
        mContext = context;
        mView = view;
        initView(view);
    }

    protected abstract void initView(View view);
}
