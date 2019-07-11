package com.smart.ui.link;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.smart.ui.BuildConfig;
import com.smart.ui.widget.textview.ISpanTouchFix;

/**
 * @date : 2019-07-04 18:00
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUILinkTouchDecorHelper {
    private ITouchableSpan pressedSpan;

    public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            pressedSpan = getPressedSpan(textView, spannable, event);
            if (pressedSpan != null) {
                pressedSpan.setPressed(true);
                Selection.setSelection(spannable, spannable.getSpanStart(pressedSpan),
                        spannable.getSpanEnd(pressedSpan));
            }
            if (textView instanceof ISpanTouchFix) {
                ISpanTouchFix tv = (ISpanTouchFix) textView;
                tv.setTouchSpanHit(pressedSpan != null);
            }
            return pressedSpan != null;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            ITouchableSpan touchedSpan = getPressedSpan(textView, spannable, event);
            if (pressedSpan != null && touchedSpan != pressedSpan) {
                pressedSpan.setPressed(false);
                pressedSpan = null;
                Selection.removeSelection(spannable);
            }
            if (textView instanceof ISpanTouchFix) {
                ISpanTouchFix tv = (ISpanTouchFix) textView;
                tv.setTouchSpanHit(pressedSpan != null);
            }
            return pressedSpan != null;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean touchSpanHint = false;
            if (pressedSpan != null) {
                touchSpanHint = true;
                pressedSpan.setPressed(false);
                pressedSpan.onClick(textView);
            }

            pressedSpan = null;
            Selection.removeSelection(spannable);
            if (textView instanceof ISpanTouchFix) {
                ISpanTouchFix tv = (ISpanTouchFix) textView;
                tv.setTouchSpanHit(touchSpanHint);
            }
            return touchSpanHint;
        } else {
            if (pressedSpan != null) {
                pressedSpan.setPressed(false);
            }
            if (textView instanceof ISpanTouchFix) {
                ISpanTouchFix tv = (ISpanTouchFix) textView;
                tv.setTouchSpanHit(false);
            }
            Selection.removeSelection(spannable);
            return false;
        }

    }

    public ITouchableSpan getPressedSpan(TextView textView, Spannable spannable, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        x -= textView.getTotalPaddingLeft();
        y -= textView.getTotalPaddingTop();

        x += textView.getScrollX();
        y += textView.getScrollY();

        Layout layout = textView.getLayout();
        //某点在垂直方向上的行数值
        int line = layout.getLineForVertical(y);

        /*
         * BugFix: https://issuetracker.google.com/issues/113348914
         */
        try {
            //某点
            int off = layout.getOffsetForHorizontal(line, x);
            if (x < layout.getLineLeft(line) || x > layout.getLineRight(line)) {
                // 实际上没点到任何内容
                off = -1;
            }
            ITouchableSpan[] link = spannable.getSpans(off, off, ITouchableSpan.class);
            ITouchableSpan touchedSpan = null;
            if (link.length > 0) {
                touchedSpan = link[0];
            }
            return touchedSpan;
        } catch (IndexOutOfBoundsException e) {
            if (BuildConfig.DEBUG) {
                Log.d(this.toString(), "getPressedSpan", e);
            }
        }
        return null;
    }
}
