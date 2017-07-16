package com.markchan.carrier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.markchan.carrier.R;

public class SettingsActivity extends AppCompatActivity {

//    @BindView(R.id.settings_aty_acib_back)
//    AppCompatImageButton mBackImageBtn;
//    @BindView(R.id.settings_aty_ll_font_manager)
//    RelativeLayout mFontManagerRelativeLayout;
//    @BindView(R.id.settings_aty_ll_watermark)
//    LinearLayout mWatermarkLinearLayout;
//    @BindView(R.id.settings_aty_ll_advice_new_function)
//    LinearLayout mAdviceNewFunctionLinearLayout;
//    @BindView(R.id.settings_aty_ll_share_to_friends)
//    LinearLayout mShareToFriendsLinearLayout;
//    @BindView(R.id.settings_aty_ll_contact_us)
//    LinearLayout mContactUsLinearLayout;
//    @BindView(R.id.settings_aty_ll_open_url)
//    LinearLayout mOpenUrlLinearLayout;
//    @BindView(R.id.settings_aty_ll_follow_us)
//    LinearLayout mFollowUsLinearLayout;
//    @BindView(R.id.settings_aty_tv_copyright)
//    AppCompatTextView mCopyrightTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.settings_aty_acib_back, R.id.settings_aty_ll_font_manager,
            R.id.settings_aty_ll_watermark, R.id.settings_aty_ll_advice_new_function,
            R.id.settings_aty_ll_share_to_friends, R.id.settings_aty_ll_contact_us,
            R.id.settings_aty_ll_open_url, R.id.settings_aty_ll_follow_us,
            R.id.settings_aty_tv_copyright})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.settings_aty_acib_back:
                finish();
                break;
            case R.id.settings_aty_ll_font_manager:
                startActivity(new Intent(this, FontManagerActivity.class));
                break;
            case R.id.settings_aty_ll_watermark:
                break;
            case R.id.settings_aty_ll_advice_new_function:
                break;
            case R.id.settings_aty_ll_share_to_friends:
                break;
            case R.id.settings_aty_ll_contact_us:
                break;
            case R.id.settings_aty_ll_open_url:
                break;
            case R.id.settings_aty_ll_follow_us:
                break;
            case R.id.settings_aty_tv_copyright:
                break;
            default:
                break;
        }
    }
}
