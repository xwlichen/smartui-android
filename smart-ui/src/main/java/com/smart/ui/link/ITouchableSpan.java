package com.smart.ui.link;

import android.view.View;

/**
 * @date : 2019-07-04 18:01
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface ITouchableSpan {
    void setPressed(boolean pressed);
    void onClick(View widget);
}
