package com.smart.ui.widget.bottomnav.lottie;

import androidx.annotation.ColorInt;

public class NavItem {

    public enum Source {
        /**
         * asset 资源文件
         */
        Assets,
        /**
         * raw 资源文件
         */
        Raw
    }

    private String navTitle;
    @ColorInt
    private int navTextSelectedColor;
    @ColorInt
    private int navTextUnselectedColor;

    private String selectedLottieName;
    private int unSelectedIcon;

    private Source lottieSource;
    private float lottieProgress;

    private boolean autoPlay;
    private boolean loop;

    private Object tag;

    NavItem() {
    }

    public NavItem(String navTitle,
                   int navTextSelectedColor,
                   int navTextUnselectedColor,
                   String selectedLottieName,
                   int unSelectedIcon,
                   Source lottieSource,
                   float lottieProgress,
                   boolean autoPlay,
                   boolean loop,
                   Object tag) {
        this.navTitle = navTitle;
        this.navTextSelectedColor = navTextSelectedColor;
        this.navTextUnselectedColor = navTextUnselectedColor;
        this.selectedLottieName = selectedLottieName;
        this.unSelectedIcon = unSelectedIcon;
        this.lottieSource = lottieSource;
        this.lottieProgress = lottieProgress;
        this.autoPlay = autoPlay;
        this.loop = loop;
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }

    public String getNavTitle() {
        return navTitle;
    }

    public void setNavTitle(String navTitle) {
        this.navTitle = navTitle;
    }

    public int getNavTextSelectedColor() {
        return navTextSelectedColor;
    }

    public void setNavTextSelectedColor(int navTextSelectedColor) {
        this.navTextSelectedColor = navTextSelectedColor;
    }

    public int getNavTextUnselectedColor() {
        return navTextUnselectedColor;
    }

    public void setNavTextUnselectedColor(int navTextUnselectedColor) {
        this.navTextUnselectedColor = navTextUnselectedColor;
    }

    public String getSelectedLottieName() {
        return selectedLottieName;
    }

    public void setSelectedLottieName(String selectedLottieName) {
        this.selectedLottieName = selectedLottieName;
    }

    public int getUnSelectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    public Source getLottieSource() {
        return lottieSource;
    }

    public void setLottieSource(Source lottieSource) {
        this.lottieSource = lottieSource;
    }

    public float getLottieProgress() {
        return lottieProgress;
    }

    public void setLottieProgress(float lottieProgress) {
        this.lottieProgress = lottieProgress;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
