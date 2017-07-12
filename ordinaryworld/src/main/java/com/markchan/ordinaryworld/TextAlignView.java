package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Mark on 17/1/19.
 */

public class TextAlignView extends View {

    public TextAlignView(Context context) {
        this(context, null);
    }

    public TextAlignView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextAlignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public TextAlignView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        float centerX = getWidth() / 2;
        float y = 200;

        // horizontal center line
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mHelperPaint);

        // left(default)
        canvas.drawLine(centerX, y, centerX + getWidth(), y, mHelperPaint);
        mPaint.setTextAlign(Align.LEFT);
        canvas.drawText(text, centerX, y, mPaint);

        canvas.save();
        canvas.translate(0, 200);

        // center
        canvas.drawLine(centerX, y, centerX + getWidth(), y, mHelperPaint);
        mPaint.setTextAlign(Align.CENTER);
        canvas.drawText(text, centerX, y, mPaint);

        canvas.restore();

        canvas.save();
        canvas.translate(0, 400);

        // right
        canvas.drawLine(centerX, y, centerX + getWidth(), y, mHelperPaint);
        mPaint.setTextAlign(Align.RIGHT);
        canvas.drawText(text, centerX, y, mPaint);

        canvas.restore();
    }
}
