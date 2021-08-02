package com.smart.ui.transition.saver;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * @date : 2019-08-19 14:48
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class TextViewStateSaver extends ViewStateSaver {

    public float getTextSize(Bundle bundle) {
        return bundle.getFloat("textSize");
    }

    public int getTextColor(Bundle bundle) {
        return bundle.getInt("textColor");
    }

    @Override
    public void captureViewInfo(View view, Bundle bundle) {
        super.captureViewInfo(view, bundle);
        if (view instanceof TextView) {
            bundle.putFloat("textSize", ((TextView) view).getTextSize());
            bundle.putInt("textColor", ((TextView) view).getCurrentTextColor());
        }
    }
}
