package com.smart.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.smart.ui.R;
import com.smart.ui.widget.SMUIWrapContentScrollView;
import com.smart.ui.widget.textview.SMUISpanTouchFixTextView;

/**
 * @date : 2019-07-04 16:03
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : Dialog基类
 */
public class SMUIDialog extends Dialog {


    private Context context;
    private boolean cancelable;
    private boolean canceledOnTouchOutSideSet;


    public SMUIDialog(@NonNull Context context) {
        this(context, R.style.SMUI_Dialog);
    }

    public SMUIDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    private void init() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initDialog() {

    }


    public static class MsgDialogBuilder extends SMUIDialogBuilder<MsgDialogBuilder> {
        private CharSequence msg;
        private SMUIWrapContentScrollView scrollView;
        private SMUISpanTouchFixTextView textView;

        public MsgDialogBuilder(Context context) {
            super(context);
        }


    }
}
