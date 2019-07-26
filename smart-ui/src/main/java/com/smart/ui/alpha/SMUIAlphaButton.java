package com.smart.ui.alpha;

import android.content.Context;
import android.util.AttributeSet;

import com.smart.ui.utils.SMUIAlphaViewHelper;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @date : 2019-07-15 18:03
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUIAlphaButton extends AppCompatButton {

    private SMUIAlphaViewHelper alphaViewHelper;

    public SMUIAlphaButton(Context context) {
        super(context);
    }

    public SMUIAlphaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SMUIAlphaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private SMUIAlphaViewHelper getAlphaViewHelper() {
        if (alphaViewHelper == null) {
            alphaViewHelper = new SMUIAlphaViewHelper(this);
        }
        return alphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        getAlphaViewHelper().onEnabledChanged(this, enabled);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        getAlphaViewHelper().setChangeAlphaWhenPress(changeAlphaWhenPress);
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        getAlphaViewHelper().setChangeAlphaWhenDisable(changeAlphaWhenDisable);
    }
}
