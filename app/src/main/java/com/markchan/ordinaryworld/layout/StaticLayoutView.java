package com.markchan.ordinaryworld.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mark on 17/1/19.
 */

public class StaticLayoutView extends View {

    public StaticLayoutView(Context context) {
        this(context, null);
    }

    public StaticLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StaticLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public StaticLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private TextPaint mPaint;
    private Paint mBoundsPaint;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mBoundsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBoundsPaint.setColor(Color.CYAN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        // --------------------------------------------------
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(30);

        Rect rect = new Rect(200, 200, 400, 400);
        canvas.drawRect(rect, mBoundsPaint);

        String text = "这是一串需要进行换行显示的文字。这是一串需要进行换行显示的文字。这是一串需要进行换行显示的文字。";

        // 文字自动换行
        StaticLayout layout = new StaticLayout(text, mPaint, rect.width(), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        canvas.save();
        mPaint.setTextAlign(Paint.Align.LEFT);
        // 文字的位置
        canvas.translate(rect.left, rect.top);
        layout.draw(canvas);
        canvas.restore();
    }
}
