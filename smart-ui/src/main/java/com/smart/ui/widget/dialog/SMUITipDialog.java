package com.smart.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.ui.R;
import com.smart.ui.utils.SMUIDisplayHelper;
import com.smart.ui.widget.SMUILoadingView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @date : 2019-08-12 11:06
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUITipDialog extends Dialog {
    public SMUITipDialog(@NonNull Context context) {
        this(context, R.style.SMUI_TipDialog);
    }

    public SMUITipDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);
    }


    private void initDialogWidth() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams wmLp = window.getAttributes();
            wmLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(wmLp);
        }
    }

    /**
     * 生成默认的 {@link SMUITipDialog}
     * <p>
     * 提供了一个图标和一行文字的样式, 其中图标有几种类型可选。见 {@link IconType}
     * </p>
     *
     * @see CustomBuilder
     */
    public static class Builder {
        /**
         * 不显示任何icon
         */
        public static final int ICON_TYPE_NOTHING = 0;
        /**
         * 显示 Loading 图标
         */
        public static final int ICON_TYPE_LOADING = 1;
        /**
         * 显示成功图标
         */
        public static final int ICON_TYPE_SUCCESS = 2;
        /**
         * 显示失败图标
         */
        public static final int ICON_TYPE_FAIL = 3;
        /**
         * 显示信息图标
         */
        public static final int ICON_TYPE_INFO = 4;

        @IntDef({ICON_TYPE_NOTHING, ICON_TYPE_LOADING, ICON_TYPE_SUCCESS, ICON_TYPE_FAIL, ICON_TYPE_INFO})
        @Retention(RetentionPolicy.SOURCE)
        public @interface IconType {
        }

        private @IconType
        int currentIconType = ICON_TYPE_NOTHING;

        private Context context;

        private CharSequence mTipWord;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置 icon 显示的内容
         *
         * @see IconType
         */
        public Builder setIconType(@IconType int iconType) {
            currentIconType = iconType;
            return this;
        }

        /**
         * 设置显示的文案
         */
        public Builder setTipWord(CharSequence tipWord) {
            mTipWord = tipWord;
            return this;
        }

        public SMUITipDialog create() {
            return create(true);
        }

        /**
         * 创建 Dialog, 但没有弹出来, 如果要弹出来, 请调用返回值的 {@link Dialog#show()} 方法
         *
         * @param cancelable 按系统返回键是否可以取消
         * @return 创建的 Dialog
         */
        public SMUITipDialog create(boolean cancelable) {
            SMUITipDialog dialog = new SMUITipDialog(context);
            dialog.setCancelable(cancelable);
            dialog.setContentView(R.layout.smui_tip_dialog_layout);
            ViewGroup contentWrap = dialog.findViewById(R.id.contentWrap);

            if (currentIconType == ICON_TYPE_LOADING) {
                SMUILoadingView loadingView = new SMUILoadingView(context);
                loadingView.setColor(Color.WHITE);
                loadingView.setSize(SMUIDisplayHelper.dp2px(context, 32));
                LinearLayout.LayoutParams loadingViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                loadingView.setLayoutParams(loadingViewLP);
                contentWrap.addView(loadingView);

            } else if (currentIconType == ICON_TYPE_SUCCESS || currentIconType == ICON_TYPE_FAIL || currentIconType == ICON_TYPE_INFO) {
                ImageView imageView = new ImageView(context);
                LinearLayout.LayoutParams imageViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(imageViewLP);

                if (currentIconType == ICON_TYPE_SUCCESS) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.smui_ic_notify_done));
                } else if (currentIconType == ICON_TYPE_FAIL) {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.smui_ic_notify_error));
                } else {
                    imageView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.smui_ic_notify_info));
                }

                contentWrap.addView(imageView);

            }

            if (mTipWord != null && mTipWord.length() > 0) {
                TextView tipView = new TextView(context);
                LinearLayout.LayoutParams tipViewLP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                if (currentIconType != ICON_TYPE_NOTHING) {
                    tipViewLP.topMargin = SMUIDisplayHelper.dp2px(context, 12);
                }
                tipView.setLayoutParams(tipViewLP);

                tipView.setEllipsize(TextUtils.TruncateAt.END);
                tipView.setGravity(Gravity.CENTER);
                tipView.setMaxLines(2);
                tipView.setTextColor(ContextCompat.getColor(context, R.color.smui_config_color_white));
                tipView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tipView.setText(mTipWord);

                contentWrap.addView(tipView);
            }
            return dialog;
        }

    }

    /**
     * 传入自定义的布局并使用这个布局生成 TipDialog
     */
    public static class CustomBuilder {
        private Context context;
        private int contentLayoutId;

        public CustomBuilder(Context context) {
            this.context = context;
        }

        public CustomBuilder setContent(@LayoutRes int layoutId) {
            contentLayoutId = layoutId;
            return this;
        }

        /**
         * 创建 Dialog, 但没有弹出来, 如果要弹出来, 请调用返回值的 {@link Dialog#show()} 方法
         *
         * @return 创建的 Dialog
         */
        public SMUITipDialog create() {
            SMUITipDialog dialog = new SMUITipDialog(context);
            dialog.setContentView(R.layout.smui_tip_dialog_layout);
            ViewGroup contentWrap = (ViewGroup) dialog.findViewById(R.id.contentWrap);
            LayoutInflater.from(context).inflate(contentLayoutId, contentWrap, true);
            return dialog;
        }
    }


}
