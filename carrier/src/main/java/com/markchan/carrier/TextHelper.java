package com.markchan.carrier;

import android.graphics.Paint;
import android.text.TextPaint;

import java.util.Vector;

/**
 * Created by Mark on 17/1/19.
 */
public class TextHelper {

    /**
     * 将文字拆分成每一行放到Vector里
     */
    public static Vector<String> getTextLinesVector(TextPaint textPaint, String text,
            float maxWidth, float maxHeight) {
        Vector<String> vector = new Vector<>();
        int line = 0;
        char ch;
        int w = 0;
        int newLineStartPos = 0;
        float fontHeight = getFontHeight(textPaint);
        int maxLine = (int) (maxHeight / fontHeight);
        int count = text.length();
        for (int i = 0; i < count; i++) {
            ch = text.charAt(i);
            float[] widths = new float[1];
            String str = String.valueOf(ch);
            textPaint.getTextWidths(str, widths);
            if (ch == '\n') {
                line++;
                vector.addElement(text.substring(newLineStartPos, i));
                newLineStartPos = i + 1;
                w = 0;
            } else {
                w += (int) Math.ceil(widths[0]);
                if (w > maxWidth) {
                    line++;
                    vector.addElement(text.substring(newLineStartPos, i));
                    newLineStartPos = i;
                    i--;
                    w = 0;
                } else {
                    if (i == count - 1) {
                        line++;
                        vector.addElement(text.substring(newLineStartPos, count));
                    }
                }
            }
            if (line == maxLine) {
                break;
            }
        }
        return vector;
    }

    public static String vectorToString(Vector<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }

    public static float getFontHeight(TextPaint textPaint) {
        Paint.FontMetrics fm = textPaint.getFontMetrics();
        return fm.bottom - fm.top;
    }

    public static float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.bottom - fm.top;
    }
}
