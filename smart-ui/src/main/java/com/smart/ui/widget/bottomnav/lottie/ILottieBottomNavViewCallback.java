package com.smart.ui.widget.bottomnav.lottie;

/**
 * @date : 2019-08-13 13:46
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface ILottieBottomNavViewCallback {

    void onMenuSelected(int oldIndex, int newIndex, NavItem menuItem);

    void onAnimationStart(int index, NavItem menuItem);

    void onAnimationEnd(int index, NavItem menuItem);

    void onAnimationCancel(int index, NavItem menuItem);
}
