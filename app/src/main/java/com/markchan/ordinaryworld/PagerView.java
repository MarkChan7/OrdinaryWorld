package com.markchan.ordinaryworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
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
import com.github.lzyzsd.randomcolor.RandomColor;

import java.util.Vector;

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
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public static final int TEXT_ALIGNMENT_LEFT = -1;
    public static final int TEXT_ALIGNMENT_CENTER = 0;
    public static final int TEXT_ALIGNMENT_RIGHT = 1;

    @IntDef(value = {TEXT_ALIGNMENT_LEFT, TEXT_ALIGNMENT_CENTER, TEXT_ALIGNMENT_RIGHT})
    public @interface TextAlignment {
        // no-op by default
    }

    private static final String DEFAULT_TEXT = "Like Sunday Like Raining\nToday is Sunnday";
    private static final float DEFAULT_TEXT_SIZE = SizeUtils.dp2px(22);
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_TEXT_ALPHA = 255;
    private static final int DEFAULT_TEXT_ALIGNMENT = TEXT_ALIGNMENT_CENTER;

    private static final int TEXT_BORDER_PADDING = SizeUtils.dp2px(8);
    private static final int TEXT_PADDING_LEFT_AND_RIGHT = SizeUtils.dp2px(12);
    private static final int TEXT_PADDING_TOP_AND_BOTTOM = SizeUtils.dp2px(8);

    private static final int TEXT_RECT_MAX_HEIGHT =
            ScreenUtils.getScreenWidth() - TEXT_BORDER_PADDING * 2
                    - TEXT_PADDING_TOP_AND_BOTTOM * 2;

    private static final int TEXT_RECT_MAX_WIDTH =
            ScreenUtils.getScreenWidth() - TEXT_BORDER_PADDING * 2
                    - TEXT_PADDING_LEFT_AND_RIGHT * 2;

    private Rect mTextBorder;

    private Rect mTextRect;

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
    private int mTextOffset;

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

    private Paint mTextBorderPaint;

    private Paint mHelperPaint;

    private boolean mShowDashRect = false;

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                      int defStyleRes) {
        mText = DEFAULT_TEXT;
        mTextSize = DEFAULT_TEXT_SIZE;
        mTextColor = DEFAULT_TEXT_COLOR;
        mTextAlpha = DEFAULT_TEXT_ALPHA;
        mTextAlignment = DEFAULT_TEXT_ALIGNMENT;

        mTextBorder = new Rect();
        mTextBorder.left = TEXT_BORDER_PADDING;
        mTextBorder.right = ScreenUtils.getScreenWidth() - TEXT_BORDER_PADDING;

        mTextRect = new Rect();
        mTextRect.left = mTextBorder.left + TEXT_PADDING_LEFT_AND_RIGHT;
        mTextRect.right = mTextBorder.right - TEXT_PADDING_LEFT_AND_RIGHT;

        mTextPaint = new TextPaint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setAlpha(mTextAlpha);

        mTextBorderPaint = new Paint();
        mTextBorderPaint.setStyle(Style.STROKE);
        mTextBorderPaint.setStrokeWidth(SizeUtils.dp2px(1.0F));
        float interval = SizeUtils.dp2px(4.0F);
        DashPathEffect dashPathEffect = new DashPathEffect(
                new float[]{interval, interval, interval, interval}, 0);
        mTextBorderPaint.setPathEffect(dashPathEffect);

        FontMetricsInt fm = mTextPaint.getFontMetricsInt();
        int textHeight = fm.bottom - fm.top;

        mTextOffset = ScreenUtils.getScreenWidth() / 2 - textHeight / 2;

        mHelperPaint = new Paint();
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
        canvas.setDrawFilter(
                new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));

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

//        mText = "A\nB\nC\nD\nE\nF\nG\na\nb\nc\nd\ne\nf\ng\nh\ni\nj\nk\nl\nm\nn\no\np\nq\nr\ns\nt\nu\nv\nw\nx\ny\nx";

        Vector<String> textLinesVector = TextHelper
                .getTextLinesVector(mTextPaint, mText, TEXT_RECT_MAX_WIDTH, TEXT_RECT_MAX_HEIGHT);

        int lines = textLinesVector.size();

        mTextOffset = ScreenUtils.getScreenWidth() / 2 - textHeight * lines / 2;

        mTextBorder.top = mTextOffset - TEXT_PADDING_TOP_AND_BOTTOM;
        mTextBorder.bottom = mTextOffset + textHeight * lines + TEXT_PADDING_TOP_AND_BOTTOM;

        if (DEBUG) {
            mHelperPaint.setColor(Color.GRAY);
            canvas.drawRect(mTextBorder, mHelperPaint);
        }

        if (mShowDashRect) { // draw dash rectangle
            Path textBorderPath = new Path();
            textBorderPath.moveTo(mTextBorder.left, mTextBorder.top);
            textBorderPath.lineTo(mTextBorder.right, mTextBorder.top);
            textBorderPath.lineTo(mTextBorder.right, mTextBorder.bottom);
            textBorderPath.lineTo(mTextBorder.left, mTextBorder.bottom);
            textBorderPath.close();
            canvas.drawPath(textBorderPath, mTextBorderPaint);
        }

        for (int i = 0; i < textLinesVector.size(); i++) {
            mTextRect.top = mTextOffset + (i * textHeight);
            mTextRect.bottom = mTextRect.top + textHeight;

            if (DEBUG) {
                mHelperPaint.setColor(new RandomColor().randomColor());
                canvas.drawRect(mTextRect, mHelperPaint);
            }

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
                    baseLineX = ScreenUtils.getScreenWidth() / 2;
                    break;
            }

            Timber.d("BaseLineX = %f, BaseLineY = %f", baseLineX, baseLineY);

            canvas.drawText(textLinesVector.elementAt(i), baseLineX, baseLineY, mTextPaint);
        }
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            text = DEFAULT_TEXT;
        }
        mText = text;
        invalidate();
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
