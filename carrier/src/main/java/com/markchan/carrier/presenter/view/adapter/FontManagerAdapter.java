package com.markchan.carrier.presenter.view.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.markchan.carrier.R;
import com.markchan.carrier.Scheme;
import com.markchan.carrier.presenter.model.FontModel;
import com.markchan.carrier.widget.ProgressWheel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mark Chan <a href="markchan2gm@gmail.com">Contact me.</a>
 * @version 1.0
 * @since 17/7/12
 */
public class FontManagerAdapter extends BaseQuickAdapter<FontModel, BaseViewHolder> {

    private final RequestManager mGlide;

    private final List<FontModel> mDownloadingFontModels;
    private final List<FontModel> mDownloadedFontModels;
    private final Map<FontModel, Integer> mDownloadingFontProgressMap;

    public FontManagerAdapter(@Nullable List<FontModel> data, RequestManager glide) {
        super(R.layout.item_list_font, data);
        mGlide = glide;
        mDownloadingFontModels = new ArrayList<>();
        mDownloadedFontModels = new ArrayList<>();
        mDownloadingFontProgressMap = new HashMap<>();
    }

    @Override
    protected void convert(BaseViewHolder helper, FontModel item) {
        mGlide.load(item.getThumbUrl())
                .into((AppCompatImageView) helper.getView(R.id.font_list_item_aciv));

        helper.addOnClickListener(R.id.font_list_item_acib);

        if (Scheme.ofUri(item.getUri()) == Scheme.FILE) {
            helper.setVisible(R.id.font_list_item_pw, false);
            if (!mDownloadedFontModels.contains(item)) {
                helper.setImageResource(R.id.font_list_item_acib, R.drawable.ic_delete);
            } else {
                helper.setImageResource(R.id.font_list_item_acib,
                        R.drawable.ic_download_success_26dp);
            }
        } else if (mDownloadingFontModels.contains(item)) {
            helper.setImageResource(R.id.font_list_item_acib, R.drawable.ic_downloading_26dp);

            helper.setVisible(R.id.font_list_item_pw, true);
            ProgressWheel pb = helper.getView(R.id.font_list_item_pw);
            pb.setProgress((int) (mDownloadingFontProgressMap.get(item) * 3.6F));
        } else {
            helper.setImageResource(R.id.font_list_item_acib, R.drawable.ic_download_26dp);
            helper.setVisible(R.id.font_list_item_pw, false);
        }
    }

    public void setDownloadingFontModelProgress(FontModel fontModel, int progress) {
        mDownloadingFontProgressMap.put(fontModel, progress);
        notifyDataSetChanged();
    }

    public void addDownloadingFontModel(FontModel fontModel) {
        if (!mDownloadingFontModels.contains(fontModel)) {
            mDownloadingFontModels.add(fontModel);
            mDownloadingFontProgressMap.put(fontModel, 0);
            notifyDataSetChanged();
        }
    }

    public void removeDownloadingFontModel(FontModel fontModel,
                                           boolean isAddToDownloadedCollection) {
        if (mDownloadingFontModels.contains(fontModel)) {
            mDownloadingFontModels.remove(fontModel);
            mDownloadingFontProgressMap.remove(fontModel);
            if (isAddToDownloadedCollection && !mDownloadedFontModels.contains(fontModel)) {
                mDownloadedFontModels.add(fontModel);
            }
            notifyDataSetChanged();
        }
    }

    public boolean removeDownloadedFontModel(FontModel fontModel) {
        if (mDownloadedFontModels.contains(fontModel)) {
            mDownloadedFontModels.remove(fontModel);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }
}
