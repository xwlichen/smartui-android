package com.smart.ui.widget.bottomnav.lottie;

import android.content.Context;
import android.graphics.Typeface;

import com.smart.ui.utils.SMUIDisplayHelper;

import androidx.annotation.NonNull;

/**
 * @date : 2019-08-13 13:44
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class NavConfig {

    private int selectedNavWidth;
    private int unselectedNavWidth;

    private int selectedNavHeight;
    private int unselectedNavHeight;

    /**
     * 单位 px
     */
    private int navTextSize;

    private boolean showTextOnUnselected;

    private Typeface typeface = Typeface.DEFAULT;


    public NavConfig(@NonNull Context context) {

        selectedNavWidth = SMUIDisplayHelper.dp2px(context, 30);
        selectedNavHeight = SMUIDisplayHelper.dp2px(context, 30);

        unselectedNavWidth = selectedNavWidth;
        unselectedNavHeight = selectedNavHeight;

        navTextSize = SMUIDisplayHelper.sp2px(context, 12);
        showTextOnUnselected = true;
    }

    public int getSelectedNavWidth() {
        return selectedNavWidth;
    }

    public void setSelectedNavWidth(int selectedNavWidth) {

        if (selectedNavWidth == -1) {
            return;
        }
        this.selectedNavWidth = selectedNavWidth;
    }

    public int getUnselectedNavWidth() {
        return unselectedNavWidth;
    }

    public void setUnselectedNavWidth(int unselectedNavWidth) {

        if (unselectedNavWidth == -1) {
            return;
        }
        this.unselectedNavWidth = unselectedNavWidth;
    }

    public int getSelectedNavHeight() {
        return selectedNavHeight;
    }

    public void setSelectedNavHeight(int selectedNavHeight) {

        if (selectedNavHeight == -1) {
            return;
        }
        this.selectedNavHeight = selectedNavHeight;
    }

    public int getUnselectedNavHeight() {
        return unselectedNavHeight;
    }

    public void setUnselectedNavHeight(int unselectedNavHeight) {

        if (unselectedNavHeight == -1) {
            return;
        }
        this.unselectedNavHeight = unselectedNavHeight;
    }

    public boolean isShowTextOnUnselected() {
        return showTextOnUnselected;
    }

    public void setShowTextOnUnselected(boolean showTextOnUnselected) {
        this.showTextOnUnselected = showTextOnUnselected;
    }

    public int getNavTextSize() {
        return navTextSize;
    }

    public void setNavTextSize(int navTextSize) {
        this.navTextSize = navTextSize;
    }


    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }
}
