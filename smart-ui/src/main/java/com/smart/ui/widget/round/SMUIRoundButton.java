package com.smart.ui.widget.round;

import android.content.Context;
import android.util.AttributeSet;

import com.smart.ui.R;
import com.smart.ui.utils.SMUIAlphaViewHelper;
import com.smart.ui.utils.SMUIViewHelper;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @date : 2019-07-15 18:11
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUIRoundButton extends AppCompatButton {

    private SMUIRoundButtonDrawable[] bgs;
    private SMUIAlphaViewHelper alphaViewHelper;
    private boolean enablePress = true;


    public SMUIRoundButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SMUIRoundButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.SMUIButtonStyle);
        init(context, attrs, R.attr.SMUIButtonStyle);
    }

    public SMUIRoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        bgs = SMUIRoundButtonDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        SMUIViewHelper.setBackgroundKeepingPadding(this, bgs[0]);
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(true);
    }


    private SMUIAlphaViewHelper getAlphaViewHelper() {
        if (alphaViewHelper == null) {
            alphaViewHelper = new SMUIAlphaViewHelper(this);
        }
        return alphaViewHelper;
    }

    @Override
    public void setPressed(boolean pressed) {
//        super.setPressed(pressed);
        changePressed(pressed);
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
        this.enablePress = changeAlphaWhenPress;
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


    /**
     * 按压下的视图操作
     *
     * @param pressed
     */
    protected void changePressed(boolean pressed) {
        if (enablePress) {
            if (bgs != null && bgs.length == 2) {
                if (pressed) {
                    SMUIViewHelper.setBackgroundKeepingPadding(this, bgs[1]);
                } else {
                    SMUIViewHelper.setBackgroundKeepingPadding(this, bgs[0]);
                }
            }
        }
    }


}
