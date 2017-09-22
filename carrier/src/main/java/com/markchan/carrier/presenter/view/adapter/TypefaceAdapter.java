package com.markchan.carrier.presenter.view.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.markchan.carrier.R;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.presenter.util.TypefaceHelper;

import java.util.List;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/21
 */
public class TypefaceAdapter extends BaseQuickAdapter<FontModel, BaseViewHolder> {

    public TypefaceAdapter(@Nullable List<FontModel> data) {
        super(R.layout.item_list_typeface, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FontModel item) {
        if (helper.getItemViewType() == BaseQuickAdapter.FOOTER_VIEW) {
            helper.addOnClickListener(R.id.typeface_list_footer_item_actv);
        } else {
            AppCompatTextView textView = helper.getView(R.id.typeface_list_item_actv);
            textView.setTypeface(TypefaceHelper.createTypeface(mContext, item.getUri()));
            textView.setText(item.getDisplayName());
        }
    }
}
