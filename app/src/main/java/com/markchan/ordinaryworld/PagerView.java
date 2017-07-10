package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;
import timber.log.Timber;

/**
 * Created by Mark on 2017/7/8.
 */
public class PagerView extends View {

    public static final boolean DEBUG = true;

    public PagerView(Context context) {
        this(context, null);
    }

    public PagerView(Context context,
            @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    public PagerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public static final int TEXT_ALIGNMENT_LEFT = -1;
    public static final int TEXT_ALIGNMENT_CENTER = 0;
    public static final int TEXT_ALIGNMENT_RIGHT = 1;

    @IntDef(value = {TEXT_ALIGNMENT_LEFT, TEXT_ALIGNMENT_CENTER, TEXT_ALIGNMENT_RIGHT})
    public @interface TextAlignment {
        // no-op by default
    }

    private static final String DEFAULT_TEXT = "Like Sunday Like Raining";
    private static final float DEFAULT_TEXT_SIZE_SP = 22.0F;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_ALPHA = 255;
    private static final int DEFAULT_TEXT_ALIGNMENT = TEXT_ALIGNMENT_CENTER;

    private Rect mTextBorder;

    private Paint mTextBorderPaint;

    private Rect mTextRect;

    private Paint mTextRectPaint;

    /** 文字 */
    private String mText;

    /** 字体路径 */
    private String mTypefaceUri;

    /** 字体大小 */
    private float mTextSize;

    /** 字体颜色 */
    @ColorInt
    private int mTextColor;

    /** 字体透明度 */
    private int mTextAlpha;

    /** 对齐方式 */
    @TextAlignment
    private int mTextAlignment;

    /** 文字相对顶部的偏移量 */
    private float mTextOffset;

    /** 纹理颜色 */
    @ColorInt
    private int mVeinColor;

    /** 纹理 */
    private Bitmap mVeinBitmap;

    /** 背景 */
    private Bitmap mBackgroundBitmap;

    /** 背景高斯模糊半径 */
    private float mBackgroundBlurRadius;

    /** 背景亮度 */
    private float mBackgroundLight;

    private TextPaint mTextPaint;

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        mText = DEFAULT_TEXT;
        mTextSize = SizeUtils.sp2px(DEFAULT_TEXT_SIZE_SP);
        mTextColor = DEFAULT_TEXT_COLOR;
        mTextAlpha = DEFAULT_TEXT_ALPHA;
        mTextAlignment = DEFAULT_TEXT_ALIGNMENT;

        mTextOffset = 0.0F;

        mTextBorder = new Rect();
        mTextBorderPaint = new Paint();
        mTextBorderPaint.setColor(Color.GREEN);

        mTextRect = new Rect();
        mTextRectPaint = new Paint();
        mTextRectPaint.setColor(Color.YELLOW);

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAlpha(mTextAlpha);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int area = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        setMeasuredDimension(area, area);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (DEBUG) {
            canvas.drawARGB(64, 255, 0, 0);
        }

        if (!TextUtils.isEmpty(mTypefaceUri)) {
            Scheme scheme = Scheme.ofUri(mTypefaceUri);
            if (scheme == Scheme.ASSETS || scheme == Scheme.FILE) {
                try {
                    String path = scheme.crop(mTypefaceUri);
                    Typeface typeface;
                    if (scheme == Scheme.ASSETS) {
                        typeface = Typeface.createFromAsset(getContext().getAssets(), path);
                    } else {
                        typeface = Typeface.createFromFile(path);
                    }
                    mTextPaint.setTypeface(typeface);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAlpha(mTextAlpha);

        FontMetricsInt fm = mTextPaint.getFontMetricsInt();
        int textHeight = fm.bottom - fm.top;

        int textBorderPadding = SizeUtils.dp2px(8);
        int textRectPaddingTopAndBottom = SizeUtils.dp2px(8);
        int textRectPaddingLeftAndRight = SizeUtils.dp2px(12);

        mTextBorder.left = textBorderPadding;
        mTextBorder.right = ScreenUtils.getScreenWidth() - textBorderPadding;
        mTextBorder.top = ScreenUtils.getScreenWidth() / 2 - textHeight / 2 - textBorderPadding;
        mTextBorder.bottom = mTextRect.top + textHeight + textBorderPadding;

        canvas.drawRect(mTextBorder, mTextBorderPaint);

        mTextRect.left = mTextBorder.left + textRectPaddingLeftAndRight;
        mTextRect.right = mTextBorder.right - textRectPaddingLeftAndRight;
        mTextRect.top = mTextBorder.top + textRectPaddingTopAndBottom;
        mTextRect.bottom = mTextBorder.bottom - textRectPaddingTopAndBottom;

        canvas.drawRect(mTextRect, mTextRectPaint);

        float baseLineY = mTextRect.bottom - fm.bottom;
        float baseLineX;
        switch (mTextAlignment) {
            case TEXT_ALIGNMENT_LEFT:
                mTextPaint.setTextAlign(Paint.Align.LEFT);
                baseLineX = mTextRect.left;
                break;
            case TEXT_ALIGNMENT_RIGHT:
                mTextPaint.setTextAlign(Paint.Align.RIGHT);
                baseLineX = mTextRect.right;
                break;
            case TEXT_ALIGNMENT_CENTER:
            default:
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                baseLineX = mTextRect.width() / 2;
                break;
        }

        Timber.d("BaseLineX = %f, BaseLineY = %f", baseLineX, baseLineY);

        canvas.drawText(mText, baseLineX, baseLineY, mTextPaint);
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            text = DEFAULT_TEXT;
        }
        mText = text;
    }

    @Override
    @TextAlignment
    public int getTextAlignment() {
        return mTextAlignment;
    }

    @Override
    public void setTextAlignment(@TextAlignment int textAlignment) {
        mTextAlignment = textAlignment;
        invalidate();
    }
}
