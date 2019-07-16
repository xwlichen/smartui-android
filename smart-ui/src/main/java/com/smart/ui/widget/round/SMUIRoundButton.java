package com.smart.ui.widget.round;

import android.content.Context;
import android.util.AttributeSet;

import com.smart.ui.R;
import com.smart.ui.alpha.SMUIAlphaButton;
import com.smart.ui.utils.SMUIViewHelper;

/**
 * @date : 2019-07-15 18:11
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUIRoundButton extends SMUIAlphaButton {

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
        SMUIRoundButtonDrawable bg = SMUIRoundButtonDrawable.fromAttributeSet(context, attrs, defStyleAttr);
        SMUIViewHelper.setBackgroundKeepingPadding(this, bg);
        setChangeAlphaWhenDisable(false);
        setChangeAlphaWhenPress(false);
    }
}
