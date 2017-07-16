package com.markchan.carrier.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.markchan.carrier.R;
import com.markchan.carrier.domain.Font;
import java.util.List;

public class FontManagerActivity extends AppCompatActivity {

    @BindView(R.id.font_manager_aty_acib_back)
    AppCompatImageButton mBackImageBtn;
    @BindView(R.id.font_manager_aty_rv)
    RecyclerView mRecyclerView;

    private List<Font> mFonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_manager);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.font_manager_aty_acib_back)
    public void back() {
        finish();
    }
}
