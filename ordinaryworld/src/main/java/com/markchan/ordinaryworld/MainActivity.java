package com.markchan.ordinaryworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.markchan.ordinaryworld.apply.DrawAtSpecPosActivity;
import com.markchan.ordinaryworld.apply.DrawCenterAtRectActivity;
import com.markchan.ordinaryworld.apply.DrawCenterAtSpecPosActivity;
import com.markchan.ordinaryworld.layout.CenterStaticLayoutActivity;
import com.markchan.ordinaryworld.layout.StaticLayoutActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void basicDrawText(View v) {
        startActivity(new Intent(this, BasicDrawTextActivity.class));
    }

    public void textAlign(View v) {
        startActivity(new Intent(this, TextAlignActivity.class));
    }

    public void fontMetrics(View v) {
        startActivity(new Intent(this, FontMetricsActivity.class));
    }

    public void textBounds(View v) {
        startActivity(new Intent(this, TextBoundsActivity.class));
    }

    public void drawAtSpecPos(View v) {
        startActivity(new Intent(this, DrawAtSpecPosActivity.class));
    }

    public void drawCenterAtSpecPos(View v) {
        startActivity(new Intent(this, DrawCenterAtSpecPosActivity.class));
    }

    public void drawCenterAtRect(View v) {
        startActivity(new Intent(this, DrawCenterAtRectActivity.class));
    }

    public void staticLayout(View v) {
        startActivity(new Intent(this, StaticLayoutActivity.class));
    }

    public void centerStaticLayout(View v) {
        startActivity(new Intent(this, CenterStaticLayoutActivity.class));
    }
}
