package com.smart.ui.widget.bottomnav.lottie;

import android.content.Context;

import com.smart.ui.utils.SMUIDisplayHelper;

import androidx.annotation.NonNull;

/**
 * @date : 2019-08-13 13:44
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class NavConfig {

    private int selectedMenuWidth;
    private int unselectedMenuWidth;

    private int selectedMenuHeight;
    private int unselectedMenuHeight;

    private boolean showTextOnUnselected;

    public NavConfig(@NonNull Context context) {

        selectedMenuWidth = SMUIDisplayHelper.dp2px(context, 48);
        selectedMenuHeight = SMUIDisplayHelper.dp2px(context, 48);

        unselectedMenuWidth = selectedMenuWidth;
        unselectedMenuHeight = selectedMenuHeight;

        showTextOnUnselected = true;
    }

    public int getSelectedMenuWidth() {
        return selectedMenuWidth;
    }

    public void setSelectedMenuWidth(int selectedMenuWidth) {

        if (selectedMenuWidth == -1) {
            return;
        }
        this.selectedMenuWidth = selectedMenuWidth;
    }

    public int getUnselectedMenuWidth() {
        return unselectedMenuWidth;
    }

    public void setUnselectedMenuWidth(int unselectedMenuWidth) {

        if (unselectedMenuWidth == -1) {
            return;
        }
        this.unselectedMenuWidth = unselectedMenuWidth;
    }

    public int getSelectedMenuHeight() {
        return selectedMenuHeight;
    }

    public void setSelectedMenuHeight(int selectedMenuHeight) {

        if (selectedMenuHeight == -1) {
            return;
        }
        this.selectedMenuHeight = selectedMenuHeight;
    }

    public int getUnselectedMenuHeight() {
        return unselectedMenuHeight;
    }

    public void setUnselectedMenuHeight(int unselectedMenuHeight) {

        if (unselectedMenuHeight == -1) {
            return;
        }
        this.unselectedMenuHeight = unselectedMenuHeight;
    }

    public boolean isShowTextOnUnselected() {
        return showTextOnUnselected;
    }

    public void setShowTextOnUnselected(boolean showTextOnUnselected) {
        this.showTextOnUnselected = showTextOnUnselected;
    }
}
