package com.smart.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * @date : 2019-07-04 17:27
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUIWrapContentScrollView extends ScrollView {
    private int maxHeight = Integer.MAX_VALUE >> 2;

    public SMUIWrapContentScrollView(Context context) {
        super(context);
    }

    public SMUIWrapContentScrollView(Context context, int maxHeight) {
        super(context);
        this.maxHeight = maxHeight;
    }

    public SMUIWrapContentScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SMUIWrapContentScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMaxHeight(int maxHeight) {
        if (this.maxHeight != maxHeight) {
            this.maxHeight = maxHeight;
            requestLayout();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        int expandSpec;
        if (lp.height > 0 && lp.height <= maxHeight) {
            expandSpec = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
        } else {
            expandSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
