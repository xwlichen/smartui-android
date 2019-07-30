package com.smart.ui.layout;

/**
 * @date : 2019-07-15 16:33
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface ISMUILayout {

    void setClipBg(boolean clipBackground);

    void setIsCircle(boolean roundAsCircle);

    void setRadius(int radius);

    void setTopLeftRadius(int topLeftRadius);

    void setTopRightRadius(int topRightRadius);

    void setBottomLeftRadius(int bottomLeftRadius);

    void setBottomRightRadius(int bottomRightRadius);

    void setBorderWidth(int strokeWidth);

    void setBorderColor(int strokeColor);

    boolean isClipBg();

    boolean isCircle();

    float getTopLeftRadius();

    float getTopRightRadius();

    float getBottomLeftRadius();

    float getBottomRightRadius();

    int getBorderWidth();

    int getBorderColor();
}
