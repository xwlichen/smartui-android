package com.smart.ui.widget.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.smart.ui.R;

/**
 * @date : 2019-08-19 16:57
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ShadowBgView extends ViewGroup {


    int backgroudColor = Color.BLACK;
    float backgroudRadius;
    int shadowColor;
    float shadowRadius;
    float shadowSize;
    float shadowPadding;

    float leftTopRaduis;
    float leftBottomRaduis;
    float rightTopRaduis;
    float rightBottomRaduis;


    Paint shadowPaint;
    Path shadowPath;

    Paint bgPaint;
    Path bgPath;


    Paint paintClear;

    RectF mRectF;

    RectF bgRectF;


    Context context;

    int width;
    int height;

    int widthMespec;
    int heightMespec;
    int boxWidth;
    int boxHeight;


    public ShadowBgView(Context context) {
        super(context);
        this.context = context;
        init();

    }

    public ShadowBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getAttrs(attrs);
        init();

    }

    public ShadowBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getAttrs(attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    public void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShadowBgView);
        backgroudColor = typedArray.getColor(R.styleable.ShadowBgView_smui_backgroundColor, Color.WHITE);
        backgroudRadius = typedArray.getDimension(R.styleable.ShadowBgView_smui_radius, 0f);
        shadowColor = typedArray.getColor(R.styleable.ShadowBgView_smui_shadowColor, Color.parseColor("#e0e0e0"));
        shadowRadius = typedArray.getDimension(R.styleable.ShadowBgView_smui_shadowRadius, 10f);
        shadowPadding = typedArray.getDimension(R.styleable.ShadowBgView_smui_shadowPadding, 10f);

        shadowSize = typedArray.getDimension(R.styleable.ShadowBgView_smui_shadowSize, 2f);

        leftTopRaduis = typedArray.getDimension(R.styleable.ShadowBgView_smui_radiusTopLeft, 0f);
        leftBottomRaduis = typedArray.getDimension(R.styleable.ShadowBgView_smui_radiusBottomLeft, 0f);
        rightTopRaduis = typedArray.getDimension(R.styleable.ShadowBgView_smui_radiusTopRight, 0f);
        rightBottomRaduis = typedArray.getDimension(R.styleable.ShadowBgView_smui_radiusBottomRight, 0f);

        if (leftTopRaduis != 0 || leftBottomRaduis != 0 || rightTopRaduis != 0 || rightBottomRaduis != 0) {
            setRadius(leftTopRaduis, rightTopRaduis, rightBottomRaduis, leftBottomRaduis);
        } else {
            setRadius(shadowRadius, shadowRadius, shadowRadius, shadowRadius);
        }

        typedArray.recycle();


    }

    public void init() {
        setWillNotDraw(false);

        shadowPath = new Path();
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(backgroudColor);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);


        bgPath = new Path();
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(Color.WHITE);
//        bgPaint.setColor(Color.BLUE);

        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);


        paintClear = new Paint();
        paintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));


        this.setLayerType(View.LAYER_TYPE_SOFTWARE, shadowPaint);
//        shadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

    }


    /**
     * 根据Model返回值
     *
     * @param value
     * @return
     */
    private int measure(int value) {
        int result = 0;
        int specMode = MeasureSpec.getMode(value);
        int specSize = MeasureSpec.getSize(value);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                result = specSize;
                break;
        }

        return result;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        width = measure(widthMeasureSpec);
        height = measure(heightMeasureSpec);
//        boxWidth = (int) (width - shadowPadding);
//        boxHeight = (int) (height - shadowPadding);
        boxWidth = (int) (width);
        boxHeight = (int) (height);


        widthMespec = widthMeasureSpec;
        heightMespec = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawShadow(canvas);
        drawBackgroud(canvas);


    }


    float[] radiusArray = new float[8];

    public void setRadius(float leftTop, float rightTop, float rightBottom, float leftBottom) {

        radiusArray[0] = leftTop;
        radiusArray[1] = leftTop;
        radiusArray[2] = rightTop;
        radiusArray[3] = rightTop;

        radiusArray[4] = rightBottom;
        radiusArray[5] = rightBottom;
        radiusArray[6] = leftBottom;
        radiusArray[7] = leftBottom;


    }

    float shaowTopPadding;
    float shaowLeftPadding;
    float shaowRightPadding;
    float shaowBottomPadding;

    public void drawShadow(Canvas canvas) {
//
//        if (getLayerType() != LAYER_TYPE_SOFTWARE) {
//            setLayerType(LAYER_TYPE_SOFTWARE, null);
//        }
        shadowPaint.setShadowLayer(shadowRadius, 0, 0, shadowColor);
//        shadowPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));

//        canvas.drawPaint(paintClear);
        initRectF();


        shadowPath = null;
        shadowPath = new Path();
        shadowPath.addRoundRect(mRectF, radiusArray, Path.Direction.CW);

        canvas.drawPath(shadowPath, shadowPaint);


    }


    public void drawBackgroud(Canvas canvas) {
//        shadowPaint.setShadowLayer(shadowRadius, 0, 0, shadowColor);
//        bgPaint.setMaskFilter(new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL));

//        canvas.drawPaint(paintClear);
        initBgRectF();

        bgPath = null;
        bgPath = new Path();
        bgPath.addRoundRect(bgRectF, radiusArray, Path.Direction.CW);
        canvas.drawPath(bgPath, bgPaint);


    }


    public void initRectF() {
        if (leftTopRaduis != 0) {
            shaowTopPadding = shadowPadding;
            shaowLeftPadding = shadowPadding;
        }
        if (leftBottomRaduis != 0) {
            shaowBottomPadding = shadowPadding;
            shaowLeftPadding = shadowPadding;

        }
        if (rightTopRaduis != 0) {
            shaowTopPadding = shadowPadding;
            shaowRightPadding = shadowPadding;

        }
        if (rightBottomRaduis != 0) {
            shaowBottomPadding = shadowPadding;
            shaowRightPadding = shadowPadding;

        }

        if (mRectF == null) {
            mRectF = new RectF(shaowLeftPadding, shaowTopPadding, boxWidth - shaowRightPadding - 2, boxHeight - shaowBottomPadding);
        }
    }


    int tempSize = 3;

    public void initBgRectF() {
        if (leftTopRaduis != 0) {
            shaowTopPadding = shadowPadding;
            shaowLeftPadding = shadowPadding;
        }
        if (leftBottomRaduis != 0) {
            shaowBottomPadding = shadowPadding;
            shaowLeftPadding = shadowPadding;

        }
        if (rightTopRaduis != 0) {
            shaowTopPadding = shadowPadding;
            shaowRightPadding = shadowPadding;

        }
        if (rightBottomRaduis != 0) {
            shaowBottomPadding = shadowPadding;
            shaowRightPadding = shadowPadding;

        }

        if (bgRectF == null) {
            bgRectF = new RectF(shaowLeftPadding - tempSize, shaowTopPadding - tempSize, boxWidth - shaowRightPadding + tempSize, boxHeight - shaowBottomPadding + tempSize);
        }
    }

}
