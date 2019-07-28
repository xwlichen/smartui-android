package com.smart.ui.utils;

import android.graphics.Color;

import androidx.annotation.ColorInt;

/**
 * @author lichen
 * @date ：2019-07-17 22:24
 * @email : 196003945@qq.com
 * @description :
 */
public class SMUIColorHelper {

    public static float COLOR_FRANCTION = 0.1f;

    public static void setColorFranction(float colorFranction) {
        COLOR_FRANCTION = colorFranction;
    }


    /**
     * 设置颜色的alpha值
     *
     * @param color    需要被设置的颜色值
     * @param alpha    取值为[0,1]，0表示全透明，1表示不透明
     * @param override 覆盖原本的 alpha
     * @return 返回改变了 alpha 值的颜色值
     */
    public static int getColorAlpha(@ColorInt int color, float alpha, boolean override) {
        int origin = override ? 0xff : (color >> 24) & 0xff;
        return color & 0x00ffffff | (int) (alpha * origin) << 24;
    }

    /**
     * 根据比例，在两个color值之间计算出一个color值
     * <b>注意该方法是ARGB通道分开计算比例的</b>
     *
     * @param fromColor 开始的color值
     * @param toColor   最终的color值
     * @param fraction  比例，取值为[0,1]，为0时返回 fromColor， 为1时返回 toColor
     * @return 计算出的color值
     */
    public static int computeColor(@ColorInt int fromColor, @ColorInt int toColor, float fraction) {
        fraction = Math.max(Math.min(fraction, 1), 0);

        int minColorA = Color.alpha(fromColor);
        int maxColorA = Color.alpha(toColor);
        int resultA = (int) ((maxColorA - minColorA) * fraction) + minColorA;

        int minColorR = Color.red(fromColor);
        int maxColorR = Color.red(toColor);
        int resultR = (int) ((maxColorR - minColorR) * fraction) + minColorR;

        int minColorG = Color.green(fromColor);
        int maxColorG = Color.green(toColor);
        int resultG = (int) ((maxColorG - minColorG) * fraction) + minColorG;

        int minColorB = Color.blue(fromColor);
        int maxColorB = Color.blue(toColor);
        int resultB = (int) ((maxColorB - minColorB) * fraction) + minColorB;

        return Color.argb(resultA, resultR, resultG, resultB);
    }

    /**
     * 将 color 颜色值转换为十六进制字符串
     *
     * @param color 颜色值
     * @return 转换后的字符串
     */
    public static String colorToString(@ColorInt int color) {
        return String.format("#%08X", color);
    }


    public static int getDefaultColorDeep(int srcColor) {
        return getColorDeep(srcColor, COLOR_FRANCTION);
    }


    /**
     * 获取相对当前颜色更暗更深的颜色
     *
     * @param srcColor 初始颜色
     * @param fraction 比例[0f,1f] 越大越暗
     * @return 返回变暗的颜色
     */
    public static int getColorDeep(int srcColor, float fraction) {
        return computeColor(srcColor, Color.BLACK, fraction);

    }


    public static int getDefaultColorShallow(int srcColor) {
        return getColorShallow(srcColor, COLOR_FRANCTION);
    }


    /**
     * 获取相对当前颜色更亮更浅的颜色
     *
     * @param srcColor 初始颜色
     * @param fraction 比例[0f,1f] 越大越亮
     * @return 返回变亮的颜色
     */
    public static int getColorShallow(int srcColor, float fraction) {
        return computeColor(srcColor, Color.WHITE, fraction);

    }
}
