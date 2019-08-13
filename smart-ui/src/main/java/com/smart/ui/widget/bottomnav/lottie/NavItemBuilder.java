package com.smart.ui.widget.bottomnav.lottie;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class NavItemBuilder {

    private NavItem navItem;

    private NavItemBuilder(String navTitle,
                           String lottieName,
                           NavItem.Source lottieSource,
                           Object tag) {

        navItem = new NavItem();

        navItem.setNavTitle(navTitle);

        navItem.setNavTextSelectedColor(Color.BLACK);
        navItem.setNavTextUnselectedColor(Color.GRAY);

        navItem.setSelectedLottieName(lottieName);
        navItem.setUnselectedLottieName(lottieName);

        navItem.setLottieSource(lottieSource);

        navItem.setTag(tag);
    }

    public static NavItemBuilder create(@NonNull String navTitle,
                                        @NonNull String lottieName,
                                        @NonNull NavItem.Source lottieSource,
                                        @Nullable Object tag) throws IllegalArgumentException {

        if (TextUtils.isEmpty(navTitle)) {
            throw new IllegalArgumentException("Menu name cannot be empty.");
        } else if (TextUtils.isEmpty(lottieName)) {
            throw new IllegalArgumentException("Lottie file must be provided.");
        }

        return new NavItemBuilder(navTitle, lottieName, lottieSource, tag);
    }

    public static NavItemBuilder createFrom(@NonNull NavItem navItem) {

        NavItemBuilder builder = create(navItem.getNavTitle(),
                navItem.getSelectedLottieName(),
                navItem.getLottieSource(),
                null);

        builder.navItem.setSelectedLottieName(navItem.getSelectedLottieName());
        builder.navItem.setUnselectedLottieName(navItem.getUnselectedLottieName());

        builder.navItem.setNavTextSelectedColor(navItem.getNavTextSelectedColor());
        builder.navItem.setNavTextUnselectedColor(navItem.getNavTextUnselectedColor());

        builder.navItem.setLottieProgress(navItem.getLottieProgress());

        builder.navItem.setAutoPlay(navItem.isAutoPlay());
        builder.navItem.setLoop(navItem.isLoop());

        return builder;
    }

    public NavItemBuilder navTitle(@NonNull String navTitle) {
        this.navItem.setNavTitle(navTitle);
        return this;
    }

    public NavItemBuilder selectedTextColor(@ColorInt int color) {
        navItem.setNavTextSelectedColor(color);
        return this;
    }

    public NavItemBuilder unSelectedTextColor(@ColorInt int color) {
        navItem.setNavTextUnselectedColor(color);
        return this;
    }

    public NavItemBuilder selectedLottieName(String lottieName) {
        navItem.setSelectedLottieName(lottieName);
        return this;
    }

    public NavItemBuilder unSelectedLottieName(String lottieName) {
        navItem.setUnselectedLottieName(lottieName);
        return this;
    }

    public NavItemBuilder pausedProgress(float progress) {

        if (progress <= 0) {
            progress = 0;
        } else if (progress >= 100) {
            progress = 100;
        }

        navItem.setLottieProgress(progress);
        return this;
    }

    public NavItemBuilder autoPlay(boolean autoPlay) {
        navItem.setAutoPlay(autoPlay);
        return this;
    }

    public NavItemBuilder loop(boolean loop) {
        navItem.setLoop(loop);
        return this;
    }

    public NavItemBuilder tag(Object tag) {
        navItem.setTag(tag);
        return this;
    }

    public NavItem build() {
        return navItem;
    }
}
