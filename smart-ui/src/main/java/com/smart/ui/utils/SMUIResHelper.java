package com.smart.ui.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.smart.ui.R;

/**
 * @date : 2019-07-15 16:39
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUIResHelper {
    public static float getAttrFloatValue(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.getFloat();
    }

    public static int getAttrColor(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return typedValue.data;
    }

    public static ColorStateList getAttrColorStateList(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return ContextCompat.getColorStateList(context, typedValue.resourceId);
    }

    public static Drawable getAttrDrawable(Context context, int attrRes) {
        int[] attrs = new int[]{attrRes};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        Drawable drawable = getAttrDrawable(context, ta, 0);
        ta.recycle();
        return drawable;
    }

    public static Drawable getAttrDrawable(Context context, TypedArray typedArray, int index) {
        TypedValue value = typedArray.peekValue(index);
        if (value != null) {
            if (value.type != TypedValue.TYPE_ATTRIBUTE && value.resourceId != 0) {
                return SMUIDrawableHelper.getVectorDrawable(context, value.resourceId);
            }
        }
        return null;
    }

    public static int getAttrDimen(Context context, int attrRes) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(attrRes, typedValue, true);
        return TypedValue.complexToDimensionPixelSize(typedValue.data, SMUIDisplayHelper.getDisplayMetrics(context));
    }

    public static void assignTextViewWithAttr(TextView textView, int attrRes) {
        TypedArray a = textView.getContext().obtainStyledAttributes(null, R.styleable.SMUITextCommonStyleDef, attrRes, 0);
        int count = a.getIndexCount();
        int paddingLeft = textView.getPaddingLeft(), paddingRight = textView.getPaddingRight(),
                paddingTop = textView.getPaddingTop(), paddingBottom = textView.getPaddingBottom();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.SMUITextCommonStyleDef_android_gravity) {
                textView.setGravity(a.getInt(attr, -1));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_textColor) {
                textView.setTextColor(a.getColorStateList(attr));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_textSize) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_paddingLeft) {
                paddingLeft = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_paddingRight) {
                paddingRight = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_paddingTop) {
                paddingTop = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_paddingBottom) {
                paddingBottom = a.getDimensionPixelSize(attr, 0);
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_singleLine) {
                textView.setSingleLine(a.getBoolean(attr, false));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_ellipsize) {
                int ellipsize = a.getInt(attr, 3);
                switch (ellipsize) {
                    case 1:
                        textView.setEllipsize(TextUtils.TruncateAt.START);
                        break;
                    case 2:
                        textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                        break;
                    case 3:
                        textView.setEllipsize(TextUtils.TruncateAt.END);
                        break;
                    case 4:
                        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        break;
                    default:
                        break;
                }
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_maxLines) {
                textView.setMaxLines(a.getInt(attr, -1));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_background) {
                SMUIViewHelper.setBackgroundKeepingPadding(textView, a.getDrawable(attr));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_lineSpacingExtra) {
                textView.setLineSpacing(a.getDimensionPixelSize(attr, 0), 1f);
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_drawablePadding) {
                textView.setCompoundDrawablePadding(a.getDimensionPixelSize(attr, 0));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_textColorHint) {
                textView.setHintTextColor(a.getColor(attr, 0));
            } else if (attr == R.styleable.SMUITextCommonStyleDef_android_textStyle) {
                int styleIndex = a.getInt(attr, -1);
                textView.setTypeface(null, styleIndex);
            }
        }
        textView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        a.recycle();
    }
}
