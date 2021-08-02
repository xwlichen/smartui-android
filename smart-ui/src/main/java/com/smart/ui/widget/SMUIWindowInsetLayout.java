package com.smart.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;

import androidx.core.view.ViewCompat;

import com.smart.ui.layout.IWindowInsetLayout;
import com.smart.ui.utils.SMUIWindowInsetHelper;

/**
 * @author lichen
 * @date ：2019-07-31 21:24
 * @email : 196003945@qq.com
 * @description :
 */
public class SMUIWindowInsetLayout extends SMUIFrameLayout implements IWindowInsetLayout {
    protected SMUIWindowInsetHelper smuiWindowInsetHelper;

    public SMUIWindowInsetLayout(Context context) {
        this(context, null);
    }

    public SMUIWindowInsetLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMUIWindowInsetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        smuiWindowInsetHelper = new SMUIWindowInsetHelper(this, this);
    }


    @SuppressWarnings("deprecation")
    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            return applySystemWindowInsets19(insets);
        }
        return super.fitSystemWindows(insets);
    }

    @Override
    public boolean applySystemWindowInsets19(Rect insets) {
        return smuiWindowInsetHelper.defaultApplySystemWindowInsets19(this, insets);
    }

    @Override
    public boolean applySystemWindowInsets21(Object insets) {
        return smuiWindowInsetHelper.defaultApplySystemWindowInsets21(this, insets);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewCompat.requestApplyInsets(this);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // xiaomi 8 not reapply insets default...
        ViewCompat.requestApplyInsets(this);
    }
}