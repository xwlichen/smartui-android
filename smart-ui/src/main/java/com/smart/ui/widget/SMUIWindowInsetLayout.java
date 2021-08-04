package com.smart.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.WindowInsetsCompat;

import com.smart.ui.utils.SMUIWindowInsetHelper;

/**
 * @author lichen
 * @date ：2019-07-31 21:24
 * @email : 196003945@qq.com
 * @description :
 */
public class SMUIWindowInsetLayout extends SMUIFrameLayout {

    public SMUIWindowInsetLayout(Context context) {
        this(context, null);
    }

    public SMUIWindowInsetLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMUIWindowInsetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        SMUIWindowInsetHelper.handleWindowInsets(child, WindowInsetsCompat.Type.statusBars() | WindowInsetsCompat.Type.displayCutout());
    }
}