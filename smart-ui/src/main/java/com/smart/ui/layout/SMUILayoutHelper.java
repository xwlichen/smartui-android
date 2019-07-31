package com.smart.ui.layout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import com.smart.ui.R;

import java.util.ArrayList;

/**
 * @date : 2019-07-15 16:34
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUILayoutHelper {

    /**
     * top-left, top-right, bottom-right, bottom-left
     */
    private float[] radiusArray = new float[8];

    /**
     * 剪裁区域路径
     */
    private Path clipPath;
    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 圆形
     */
    private boolean isCircle = false;
    /**
     * 蒙版颜色
     */
    private ColorStateList maskColorStateList;
    private int maskColor;
    /**
     * 默认边界颜色
     */
    private int defaultBorderColor;
    /**
     * 边界颜色
     */
    private int borderColor;
    /**
     * 边界颜色的状态
     */
    private ColorStateList borderColorStateList;
    /**
     * 边界的宽度
     */
    private int borderWidth;
    /**
     * 是否剪裁背景
     */
    private boolean isClipBg;
    /**
     * 内容区域
     */
    private Region areaRegion;
    /**
     * 画布图层大小
     */
    private RectF canvasRectF;


    private int radius;
    private int radiusTopLeft;
    private int radiusTopRight;
    private int radiusBottomRight;
    private int radiusBottomLeft;


    public void initAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SMUILayout);
            int count = ta.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int index = ta.getIndex(i);
                if (index == R.styleable.SMUILayout_smui_isCircle) {
                    isCircle = ta.getBoolean(index, false);
                } else if (index == R.styleable.SMUILayout_smui_maskColor) {
                    maskColorStateList = ta.getColorStateList(index);
                } else if (index == R.styleable.SMUILayout_smui_borderColor) {
                    borderColorStateList = ta.getColorStateList(index);
                } else if (index == R.styleable.SMUILayout_smui_borderWidth) {
                    borderWidth = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SMUILayout_smui_isClipBg) {
                    isClipBg = ta.getBoolean(index, false);
                } else if (index == R.styleable.SMUILayout_smui_radius) {
                    radius = ta.getDimensionPixelSize(index, 0);
                } else if (index == R.styleable.SMUILayout_smui_radiusTopLeft) {
                    radiusTopLeft = ta.getDimensionPixelSize(index, radius);
                } else if (index == R.styleable.SMUILayout_smui_radiusTopRight) {
                    radiusTopRight = ta.getDimensionPixelSize(index, radius);
                } else if (index == R.styleable.SMUILayout_smui_radiusBottomRight) {
                    radiusBottomRight = ta.getDimensionPixelSize(index, radius);
                } else if (index == R.styleable.SMUILayout_smui_radiusBottomLeft) {
                    radiusBottomLeft = ta.getDimensionPixelSize(index, radius);
                }
            }

            ta.recycle();

        }

        if (null != borderColorStateList) {
            borderColor = borderColorStateList.getDefaultColor();
            defaultBorderColor = borderColorStateList.getDefaultColor();
        } else {
            borderColor = Color.WHITE;
            defaultBorderColor = Color.WHITE;
        }

        if (null != maskColorStateList) {
            maskColor = maskColorStateList.getDefaultColor();
        }

        radiusArray[0] = radiusTopLeft;
        radiusArray[1] = radiusTopLeft;

        radiusArray[2] = radiusTopRight;
        radiusArray[3] = radiusTopRight;

        radiusArray[4] = radiusBottomRight;
        radiusArray[5] = radiusBottomRight;

        radiusArray[6] = radiusBottomLeft;
        radiusArray[7] = radiusBottomLeft;

        canvasRectF = new RectF();
        clipPath = new Path();
        areaRegion = new Region();
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
    }

    public void onSizeChanged(View view, int w, int h) {
        canvasRectF.set(0, 0, w, h);
        refreshRegion(view);
    }

    public void refreshRegion(View view) {
        int w = (int) canvasRectF.width();
        int h = (int) canvasRectF.height();
        RectF areas = new RectF();
        areas.left = view.getPaddingLeft();
        areas.top = view.getPaddingTop();
        areas.right = w - view.getPaddingRight();
        areas.bottom = h - view.getPaddingBottom();
        clipPath.reset();
        if (isCircle) {
            float d = areas.width() >= areas.height() ? areas.height() : areas.width();
            float r = d / 2;
            PointF center = new PointF(w / 2, h / 2);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
                clipPath.addCircle(center.x, center.y, r, Path.Direction.CW);

                clipPath.moveTo(0, 0);  // 通过空操作让Path区域占满画布
                clipPath.moveTo(w, h);
            } else {
                float y = h / 2 - r;
                clipPath.moveTo(areas.left, y);
                clipPath.addCircle(center.x, y + r, r, Path.Direction.CW);
            }
        } else {
            clipPath.addRoundRect(areas, radiusArray, Path.Direction.CW);
        }
        Region clip = new Region((int) areas.left, (int) areas.top,
                (int) areas.right, (int) areas.bottom);
        areaRegion.setPath(clipPath, clip);
    }

    public void onClipDraw(Canvas canvas) {
        onClipDraw(canvas, false);
    }


    public void onClipDraw(Canvas canvas, boolean isPressed) {
        if (borderWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(borderWidth * 2);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(clipPath, paint);
            // 绘制描边
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(clipPath, paint);
        }
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawPath(clipPath, paint);
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

            final Path path = new Path();
            path.addRect(0, 0, (int) canvasRectF.width(), (int) canvasRectF.height(), Path.Direction.CW);
            path.op(clipPath, Path.Op.DIFFERENCE);
            canvas.drawPath(path, paint);
        }

        if (maskColor != 0) {
            paint.setXfermode(null);
            paint.setStyle(Paint.Style.FILL);
            if (isPressed) {
                paint.setColor(maskColor);

            } else {
                paint.setColor(Color.TRANSPARENT);

            }

            canvas.drawPath(clipPath, paint);
        }

    }


    //--- Selector 支持 ----------------------------------------------------------------------------

    public boolean isChecked;              // 是否是 check 状态
    public OnCheckedChangeListener nnCheckedChangeListener;

    public void drawableStateChanged(View view) {
        if (view instanceof ISMUILayout) {
            ArrayList<Integer> stateListArray = new ArrayList<>();
            if (view instanceof Checkable) {
                stateListArray.add(android.R.attr.state_checkable);
                if (((Checkable) view).isChecked()) {
                    stateListArray.add(android.R.attr.state_checked);
                }
            }
            if (view.isEnabled()) {
                stateListArray.add(android.R.attr.state_enabled);
            }
            if (view.isFocused()) {
                stateListArray.add(android.R.attr.state_focused);
            }
            if (view.isPressed()) {
                stateListArray.add(android.R.attr.state_pressed);
            }
            if (view.isHovered()) {
                stateListArray.add(android.R.attr.state_hovered);
            }
            if (view.isSelected()) {
                stateListArray.add(android.R.attr.state_selected);
            }
            if (view.isActivated()) {
                stateListArray.add(android.R.attr.state_activated);
            }
            if (view.hasWindowFocus()) {
                stateListArray.add(android.R.attr.state_window_focused);
            }

            if (borderColorStateList != null && borderColorStateList.isStateful()) {
                int[] stateList = new int[stateListArray.size()];
                for (int i = 0; i < stateListArray.size(); i++) {
                    stateList[i] = stateListArray.get(i);
                }
                int stateColor = borderColorStateList.getColorForState(stateList, defaultBorderColor);
                ((ISMUILayout) view).setBorderColor(stateColor);
            }
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(View view, boolean isChecked);
    }


    public boolean isClipBg() {
        return isClipBg;
    }

    public void setClipBg(boolean clipBg) {
        isClipBg = clipBg;
    }


    public Path getClipPath() {
        return clipPath;
    }

    public void setClipPath(Path clipPath) {
        this.clipPath = clipPath;
    }

    public RectF getCanvasRectF() {
        return canvasRectF;
    }

    public void setCanvasRectF(RectF canvasRectF) {
        this.canvasRectF = canvasRectF;
    }
}