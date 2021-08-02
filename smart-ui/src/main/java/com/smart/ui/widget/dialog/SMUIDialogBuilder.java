package com.smart.ui.widget.dialog;

import android.content.Context;

import com.smart.ui.R;
import com.smart.ui.utils.SMUIDisplayHelper;

/**
 * @date : 2019-07-04 16:49
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : 不同类型Dialog的基类构造器
 */
public abstract class SMUIDialogBuilder<T extends SMUIDialogBuilder> {

    private Context context;
    private int contentAreaMaxHeight = -1;
    private String title;

    public SMUIDialogBuilder(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    protected int getContentAreaMaxHeight() {
        if (contentAreaMaxHeight == -1) {
            // 屏幕高度的0.85 - 预估的 title 和 action 高度
            return (int) (SMUIDisplayHelper.getScreenHeight(context) * 0.85) - SMUIDisplayHelper.dp2px(context, 100);
        }
        return contentAreaMaxHeight;
    }


    /**
     * 设置内容区域最高的宽度
     *
     * @param contentAreaMaxHeight
     * @return
     */
    public T setContentAreaMaxHeight(int contentAreaMaxHeight) {
        this.contentAreaMaxHeight = contentAreaMaxHeight;
        return (T) this;
    }


    public T setTitle(String title) {
        if (title != null && title.length() > 0) {
            this.title = title + context.getString(R.string.qmui_tool_fixellipsize);
        }
        return (T) this;
    }

    /**
     * 设置对话框顶部的标题文字
     */
    public T setTitle(int resId) {
        return setTitle(context.getResources().getString(resId));
    }
}
