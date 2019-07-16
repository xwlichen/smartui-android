package com.smart.ui.utils;

import android.support.annotation.NonNull;
import android.view.View;

import com.smart.ui.R;

import java.lang.ref.WeakReference;

/**
 * @date : 2019-07-15 18:04
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUIAlphaViewHelper {

    private WeakReference<View> target;

    /**
     * 设置是否要在 press 时改变透明度
     */
    private boolean changeAlphaWhenPress = true;

    /**
     * 设置是否要在 disabled 时改变透明度
     */
    private boolean changeAlphaWhenDisable = true;

    private float normalAlpha = 1f;
    private float pressedAlpha = .5f;
    private float disabledAlpha = .5f;

    public SMUIAlphaViewHelper(@NonNull View target) {
        this.target = new WeakReference<>(target);
        this.pressedAlpha = SMUIResHelper.getAttrFloatValue(target.getContext(), R.attr.smui_alpha_pressed);
        disabledAlpha = SMUIResHelper.getAttrFloatValue(target.getContext(), R.attr.smui_alpha_disabled);
    }

    public SMUIAlphaViewHelper(@NonNull View target, float pressedAlpha, float disabledAlpha) {
        this.target = new WeakReference<>(target);
        this.pressedAlpha = pressedAlpha;
        this.disabledAlpha = disabledAlpha;
    }

    /**
     * @param current the view to be handled, maybe not equal to target view
     * @param pressed
     */
    public void onPressedChanged(View current, boolean pressed) {
        View target = this.target.get();
        if (target == null) {
            return;
        }
        if (current.isEnabled()) {
            target.setAlpha(changeAlphaWhenPress && pressed && current.isClickable() ? this.pressedAlpha : normalAlpha);
        } else {
            if (this.changeAlphaWhenDisable) {
                target.setAlpha(disabledAlpha);
            }
        }
    }

    /**
     * @param current the view to be handled, maybe not  equal to target view
     * @param enabled
     */
    public void onEnabledChanged(View current, boolean enabled) {
        View target = this.target.get();
        if (target == null) {
            return;
        }
        float alphaForIsEnable;
        if (this.changeAlphaWhenDisable) {
            alphaForIsEnable = enabled ? normalAlpha : disabledAlpha;
        } else {
            alphaForIsEnable = normalAlpha;
        }
        if (current != target && target.isEnabled() != enabled) {
            target.setEnabled(enabled);
        }
        target.setAlpha(alphaForIsEnable);
    }

    /**
     * 设置是否要在 press 时改变透明度
     *
     * @param changeAlphaWhenPress 是否要在 press 时改变透明度
     */
    public void setChangeAlphaWhenPress(boolean changeAlphaWhenPress) {
        this.changeAlphaWhenPress = changeAlphaWhenPress;
    }

    /**
     * 设置是否要在 disabled 时改变透明度
     *
     * @param changeAlphaWhenDisable 是否要在 disabled 时改变透明度
     */
    public void setChangeAlphaWhenDisable(boolean changeAlphaWhenDisable) {
        this.changeAlphaWhenDisable = changeAlphaWhenDisable;
        View target = this.target.get();
        if (target != null) {
            onEnabledChanged(target, target.isEnabled());
        }

    }
}
