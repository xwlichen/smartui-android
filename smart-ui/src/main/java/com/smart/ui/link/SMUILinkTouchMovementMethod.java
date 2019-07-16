package com.smart.ui.link;

import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.method.Touch;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @date : 2019-07-04 17:59
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUILinkTouchMovementMethod extends LinkMovementMethod {

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        return helper.onTouchEvent(widget, buffer, event)
                || Touch.onTouchEvent(widget, buffer, event);
    }

    public static MovementMethod getInstance() {
        if (instance == null) {
            instance = new SMUILinkTouchMovementMethod();
        }

        return instance;
    }

    private static SMUILinkTouchMovementMethod instance;
    private static SMUILinkTouchDecorHelper helper = new SMUILinkTouchDecorHelper();

}
