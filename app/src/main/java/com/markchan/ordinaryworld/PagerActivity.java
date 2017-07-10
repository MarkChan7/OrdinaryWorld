package com.markchan.ordinaryworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.markchan.ordinaryworld.panel.TextAlignmentPanel;

public class PagerActivity extends AppCompatActivity {

    private PagerView mPagerView;
    private View mTextAlignmentPanelView;

    private TextAlignmentPanel mTextAlignmentPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        mPagerView = (PagerView) findViewById(R.id.pager_aty_pager_view);
        mTextAlignmentPanelView = findViewById(R.id.pager_aty_view_text_alignment_panel);
        mTextAlignmentPanel = new TextAlignmentPanel(mPagerView, mTextAlignmentPanelView);
    }
}
