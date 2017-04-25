package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mark on 17/1/19.
 */

public class BasicDrawTextView extends View {

    public BasicDrawTextView(Context context) {
        this(context, null);
    }

    public BasicDrawTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasicDrawTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public BasicDrawTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private Paint mPaint;

    private Paint mHelperPaint;

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelperPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHelperPaint.setColor(Color.RED);
        mHelperPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.CYAN);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(120);
        String text = "asdfghjkl";
        float baseLineX = 0;
        float baseLineY = 200;

        // basic line
        canvas.drawLine(baseLineX, baseLineY, baseLineX + getWidth(), baseLineY, mHelperPaint);

        canvas.drawText(text, baseLineX, baseLineY, mPaint);
    }
}
