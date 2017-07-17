package com.markchan.carrier.presenter.view.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.markchan.carrier.R;
import com.markchan.carrier.Scheme;
import com.markchan.carrier.presenter.model.FontModel;

import java.util.List;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class FontManagerAdapter extends BaseQuickAdapter<FontModel, BaseViewHolder> {

    private final RequestManager mGlide;

    public FontManagerAdapter(@Nullable List<FontModel> data, RequestManager glide) {
        super(R.layout.item_list_font, data);
        mGlide = glide;
    }

    @Override
    protected void convert(BaseViewHolder helper, FontModel item) {
        mGlide.load(item.getThumbUrl())
                .into((AppCompatImageView) helper.getView(R.id.font_list_item_aciv));
        if (Scheme.ofUri(item.getUri()) == Scheme.FILE) {
            helper.setImageResource(R.id.font_list_item_acib, R.drawable.ic_download_success_26dp);
        } else {
            helper.setImageResource(R.id.font_list_item_acib, R.drawable.ic_download_26dp);
        }
    }
}
