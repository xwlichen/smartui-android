package com.smart.ui.widget.textview;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;

import com.smart.ui.link.SMUILinkTouchDecorHelper;
import com.smart.ui.link.SMUILinkTouchMovementMethod;

/**
 * @date : 2019-07-04 17:57
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUISpanTouchFixTextView extends AppCompatTextView implements ISpanTouchFix {
    /**
     * 记录当前 Touch 事件对应的点是不是点在了 span 上面
     */
    private boolean touchSpanHit;

    /**
     * 记录每次真正传入的press，每次更改mTouchSpanHint，需要再调用一次setPressed，确保press状态正确
     */
    private boolean isPressedRecord = false;
    /**
     * TextView是否应该消耗事件
     */
    private boolean needForceEventToParent = false;

    public SMUISpanTouchFixTextView(Context context) {
        this(context, null);
    }

    public SMUISpanTouchFixTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMUISpanTouchFixTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置高亮的文字区域为透明，比如span设置链接的时候
        setHighlightColor(Color.TRANSPARENT);
    }


    /**
     * 在需要父布局的时候，取消相关焦点
     * @param needForceEventToParent
     * 当只需要父布局消费点击时间时候，设置如下属性，在{@link SMUILinkTouchDecorHelper}，getPressSpan 都会返回null
     */
    public void setNeedForceEventToParent(boolean needForceEventToParent) {
        this.needForceEventToParent = needForceEventToParent;
        //
        setFocusable(!needForceEventToParent);
        setClickable(!needForceEventToParent);
        setLongClickable(!needForceEventToParent);
    }

    /**
     * 使用者主动调用
     */
    public void setMovementMethodDefault() {
        setMovementMethodCompat(SMUILinkTouchMovementMethod.getInstance());
    }

    public void setMovementMethodCompat(MovementMethod movement) {
        setMovementMethod(movement);
        if (needForceEventToParent) {
            setNeedForceEventToParent(true);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!(getText() instanceof Spannable)) {
            return super.onTouchEvent(event);
        }
        touchSpanHit = true;
        // 调用super.onTouchEvent,会走到SMUILinkTouchMovementMethod
        // 会走到SMUILinkTouchMovementMethod#onTouchEvent会修改touchSpanHint
        boolean ret = super.onTouchEvent(event);
        if (needForceEventToParent) {
            return touchSpanHit;
        }
        return ret;
    }

    @Override
    public void setTouchSpanHit(boolean hit) {
        if (touchSpanHit != hit) {
            touchSpanHit = hit;
            setPressed(isPressedRecord);
        }
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean performClick() {
        if (!touchSpanHit && !needForceEventToParent) {
            return super.performClick();
        }
        return false;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean performLongClick() {
        if (!touchSpanHit && !needForceEventToParent) {
            return super.performLongClick();
        }
        return false;
    }

    @Override
    public final void setPressed(boolean pressed) {
        isPressedRecord = pressed;
        if (!touchSpanHit) {
            onSetPressed(pressed);
        }
    }


    //调用系统的按压效果
    protected void onSetPressed(boolean pressed) {
        super.setPressed(pressed);
    }
}
