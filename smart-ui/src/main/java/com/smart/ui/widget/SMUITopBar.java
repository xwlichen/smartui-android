package com.smart.ui.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.smart.ui.R;
import com.smart.ui.utils.SMUIDisplayHelper;
import com.smart.ui.utils.SMUIDrawableHelper;
import com.smart.ui.utils.SMUILangHelper;
import com.smart.ui.utils.SMUIResHelper;
import com.smart.ui.utils.SMUIViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @date : 2019-07-30 11:12
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class SMUITopBar extends RelativeLayout {

    private static final int DEFAULT_VIEW_ID = -1;
    private int leftLastViewId; // 左侧最右 view 的 id
    private int rightLastViewId; // 右侧最左 view 的 id

    private View centerView; // 中间的 View
    private LinearLayout titleContainerView; // 包裹 title 和 subTitle 的容器
    private TextView titleView; // 显示 title 文字的 TextView
    private TextView subTitleView; // 显示 subTitle 文字的 TextView

    private List<View> leftViewList;
    private List<View> rightViewList;

    private int topBarSeparatorColor;
    private int topBarBgColor;
    private int topBarSeparatorHeight;

    private Drawable topBarBgWithSeparatorDrawableCache;

    private int titleGravity;
    private int leftBackDrawableRes;
    private int titleTextSize;
    private int titleTextSizeWithSubTitle;
    private int subTitleTextSize;
    private int titleTextColor;
    private int subTitleTextColor;
    private int titleMarginHorWhenNoBtnAside;
    private int titleContainerPaddingHor;
    private int topBarImageBtnWidth;
    private int topBarImageBtnHeight;
    private int topBarTextBtnPaddingHor;
    private ColorStateList topBarTextBtnTextColor;
    private int topBarTextBtnTextSize;
    private int topbarHeight = -1;
    private Rect titleContainerRect;

    public SMUITopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVar();
        init(context, attrs, defStyleAttr);
    }

    public SMUITopBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SMUITopBarStyle);
    }


    // ========================= centerView 相关的方法

    public SMUITopBar(Context context) {
        this(context, null);
    }

    // ========================= title 相关的方法

    // 这个构造器只用于SMUI内部，不开放给外面用，目前用于SMUITopBarLayout
    public SMUITopBar(Context context, boolean inTopBarLayout) {
        super(context);
        initVar();
        if (inTopBarLayout) {
            int transparentColor = ContextCompat.getColor(context, R.color.smui_config_color_transparent);
            topBarSeparatorColor = transparentColor;
            topBarSeparatorHeight = 0;
            topBarBgColor = transparentColor;
        } else {
            init(context, null, R.attr.SMUITopBarStyle);
        }
    }

    private void initVar() {
        leftLastViewId = DEFAULT_VIEW_ID;
        rightLastViewId = DEFAULT_VIEW_ID;
        leftViewList = new ArrayList<>();
        rightViewList = new ArrayList<>();
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SMUITopBar, defStyleAttr, 0);
        topBarSeparatorColor = array.getColor(R.styleable.SMUITopBar_smui_topbar_separator_color,
                ContextCompat.getColor(context, R.color.smui_config_color_separator));
        topBarSeparatorHeight = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_separator_height, 1);
        topBarBgColor = array.getColor(R.styleable.SMUITopBar_smui_topbar_bg_color, Color.WHITE);
        getCommonFieldFormTypedArray(context, array);
        boolean hasSeparator = array.getBoolean(R.styleable.SMUITopBar_smui_topbar_need_separator, true);
        array.recycle();

        setBackgroundDividerEnabled(hasSeparator);
    }


    void getCommonFieldFormTypedArray(Context context, TypedArray array) {
        leftBackDrawableRes = array.getResourceId(R.styleable.SMUITopBar_smui_topbar_left_back_drawable_id, R.id.smui_topbar_item_left_back);
        titleGravity = array.getInt(R.styleable.SMUITopBar_smui_topbar_title_gravity, Gravity.CENTER);
        titleTextSize = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_title_text_size, SMUIDisplayHelper.sp2px(context, 17));
        titleTextSizeWithSubTitle = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_title_text_size, SMUIDisplayHelper.sp2px(context, 16));
        subTitleTextSize = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_subtitle_text_size, SMUIDisplayHelper.sp2px(context, 11));
        titleTextColor = array.getColor(R.styleable.SMUITopBar_smui_topbar_title_color, SMUIResHelper.getAttrColor(context, R.attr.smui_config_color_gray_1));
        subTitleTextColor = array.getColor(R.styleable.SMUITopBar_smui_topbar_subtitle_color, SMUIResHelper.getAttrColor(context, R.attr.smui_config_color_gray_4));
        titleMarginHorWhenNoBtnAside = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_title_margin_horizontal_when_no_btn_aside, 0);
        titleContainerPaddingHor = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_title_container_padding_horizontal, 0);
        topBarImageBtnWidth = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_image_btn_width, SMUIDisplayHelper.dp2px(context, 48));
        topBarImageBtnHeight = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_image_btn_height, SMUIDisplayHelper.dp2px(context, 48));
        topBarTextBtnPaddingHor = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_text_btn_padding_horizontal, SMUIDisplayHelper.dp2px(context, 12));
        topBarTextBtnTextColor = array.getColorStateList(R.styleable.SMUITopBar_smui_topbar_text_btn_color_state_list);
        topBarTextBtnTextSize = array.getDimensionPixelSize(R.styleable.SMUITopBar_smui_topbar_text_btn_text_size, SMUIDisplayHelper.sp2px(context, 16));

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        while (parent != null && (parent instanceof View)) {
//            if (parent instanceof SMUICollapsingTopBarLayout) {
//                makeSureTitleContainerView();
//                return;
//            }
            parent = parent.getParent();
        }
    }

    /**
     * 在 TopBar 的中间添加 View，如果此前已经有 View 通过该方法添加到 TopBar，则旧的View会被 remove
     *
     * @param view 要添加到TopBar中间的View
     */
    public void setCenterView(View view) {
        if (centerView == view) {
            return;
        }
        if (centerView != null) {
            removeView(centerView);
        }
        centerView = view;
        RelativeLayout.LayoutParams params = (LayoutParams) centerView.getLayoutParams();
        if (params == null) {
            params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(view, params);
    }

    /**
     * 添加 TopBar 的标题
     *
     * @param resId TopBar 的标题 resId
     */
    public TextView setTitle(int resId) {
        return setTitle(getContext().getString(resId));
    }

    /**
     * 添加 TopBar 的标题
     *
     * @param title TopBar 的标题
     */
    public TextView setTitle(String title) {
        TextView titleView = getTitleView(false);
        titleView.setText(title);
        if (SMUILangHelper.isNullOrEmpty(title)) {
            titleView.setVisibility(GONE);
        } else {
            titleView.setVisibility(VISIBLE);
        }
        return titleView;
    }

    public CharSequence getTitle() {
        if (titleView == null) {
            return null;
        }
        return titleView.getText();
    }

    public TextView setEmojiTitle(String title) {
        TextView titleView = getTitleView(true);
        titleView.setText(title);
        if (SMUILangHelper.isNullOrEmpty(title)) {
            titleView.setVisibility(GONE);
        } else {
            titleView.setVisibility(VISIBLE);
        }
        return titleView;
    }

    public void showTitleView(boolean toShow) {
        if (titleView != null) {
            titleView.setVisibility(toShow ? VISIBLE : GONE);
        }
    }


    protected TextView getTitleView() {
        if (titleView == null) {
//            titleView = isEmoji ? new EmojiconTextView(getContext()) : new TextView(getContext());
            titleView = new TextView(getContext());
            titleView.setGravity(Gravity.CENTER);
            titleView.setSingleLine(true);
            titleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            titleView.setTextColor(titleTextColor);
            updateTitleViewStyle();
            LinearLayout.LayoutParams titleLp = generateTitleViewAndSubTitleViewLp();
            makeSureTitleContainerView().addView(titleView, titleLp);
        }

        return titleView;
    }

    /**
     * 更新 titleView 的样式（因为有没有 subTitle 会影响 titleView 的样式）
     */
    private void updateTitleViewStyle() {
        if (titleView != null) {
            if (subTitleView == null || SMUILangHelper.isNullOrEmpty(subTitleView.getText())) {
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
            } else {
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSizeWithSubTitle);
            }
        }
    }

    /**
     * 添加 TopBar 的副标题
     *
     * @param subTitle TopBar 的副标题
     */
    public void setSubTitle(String subTitle) {
        TextView titleView = getSubTitleView();
        titleView.setText(subTitle);
        if (SMUILangHelper.isNullOrEmpty(subTitle)) {
            titleView.setVisibility(GONE);
        } else {
            titleView.setVisibility(VISIBLE);
        }
        // 更新 titleView 的样式（因为有没有 subTitle 会影响 titleView 的样式）
        updateTitleViewStyle();
    }

    /**
     * 添加 TopBar 的副标题
     *
     * @param resId TopBar 的副标题 resId
     */
    public void setSubTitle(int resId) {
        setSubTitle(getResources().getString(resId));
    }

    public TextView getSubTitleView() {
        if (subTitleView == null) {
            subTitleView = new TextView(getContext());
            subTitleView.setGravity(Gravity.CENTER);
            subTitleView.setSingleLine(true);
            subTitleView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            subTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, subTitleTextSize);
            subTitleView.setTextColor(subTitleTextColor);
            LinearLayout.LayoutParams titleLp = generateTitleViewAndSubTitleViewLp();
            titleLp.topMargin = SMUIDisplayHelper.dp2px(getContext(), 1);
            makeSureTitleContainerView().addView(subTitleView, titleLp);
        }

        return subTitleView;
    }

    /**
     * 设置 TopBar 的 gravity，用于控制 title 和 subtitle 的对齐方式
     *
     * @param gravity 参考 {@link android.view.Gravity}
     */
    public void setTitleGravity(int gravity) {
        titleGravity = gravity;
        if (titleView != null) {
            ((LinearLayout.LayoutParams) titleView.getLayoutParams()).gravity = gravity;
            if (gravity == Gravity.CENTER || gravity == Gravity.CENTER_HORIZONTAL) {
                titleView.setPadding(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), getPaddingBottom());
            }
        }
        if (subTitleView != null) {
            ((LinearLayout.LayoutParams) subTitleView.getLayoutParams()).gravity = gravity;
        }
        requestLayout();
    }

    public Rect getTitleContainerRect() {
        if (titleContainerRect == null) {
            titleContainerRect = new Rect();
        }
        if (titleContainerView == null) {
            titleContainerRect.set(0, 0, 0, 0);
        } else {
            SMUIViewHelper.getDescendantRect(this, titleContainerView, titleContainerRect);
        }
        return titleContainerRect;
    }


    // ========================= leftView、rightView 相关的方法

    private LinearLayout makeSureTitleContainerView() {
        if (titleContainerView == null) {
            titleContainerView = new LinearLayout(getContext());
            // 垂直，后面要支持水平的话可以加个接口来设置
            titleContainerView.setOrientation(LinearLayout.VERTICAL);
            titleContainerView.setGravity(Gravity.CENTER);
            titleContainerView.setPadding(titleContainerPaddingHor, 0, titleContainerPaddingHor, 0);
            addView(titleContainerView, generateTitleContainerViewLp());
        }
        return titleContainerView;
    }

    /**
     * 生成 TitleContainerView 的 LayoutParams。
     * 左右有按钮时，该 View 在左右按钮之间；
     * 没有左右按钮时，该 View 距离 TopBar 左右边缘有固定的距离
     */
    private LayoutParams generateTitleContainerViewLp() {
        return new LayoutParams(LayoutParams.MATCH_PARENT,
                SMUIResHelper.getAttrDimen(getContext(), R.attr.smui_topbar_height));
    }

    /**
     * 生成 titleView 或 subTitleView 的 LayoutParams
     */
    private LinearLayout.LayoutParams generateTitleViewAndSubTitleViewLp() {
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 垂直居中
        titleLp.gravity = titleGravity;
        return titleLp;
    }

    /**
     * 在TopBar的左侧添加View，如果此前已经有View通过该方法添加到TopBar，则新添加进去的View会出现在已有View的右侧
     *
     * @param view   要添加到 TopBar 左边的 View
     * @param viewId 该按钮的id，可在ids.xml中找到合适的或新增。手工指定viewId是为了适应自动化测试。
     */
    public void addLeftView(View view, int viewId) {
        ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
        LayoutParams layoutParams;
        if (viewLayoutParams != null && viewLayoutParams instanceof LayoutParams) {
            layoutParams = (LayoutParams) viewLayoutParams;
        } else {
            layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        this.addLeftView(view, viewId, layoutParams);
    }

    /**
     * 在TopBar的左侧添加View，如果此前已经有View通过该方法添加到TopBar，则新添加进去的View会出现在已有View的右侧。
     *
     * @param view         要添加到 TopBar 左边的 View。
     * @param viewId       该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @param layoutParams 传入一个 LayoutParams，当把 Button addView 到 TopBar 时，使用这个 LayouyParams。
     */
    public void addLeftView(View view, int viewId, LayoutParams layoutParams) {
        if (leftLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else {
            layoutParams.addRule(RelativeLayout.RIGHT_OF, leftLastViewId);
        }
        layoutParams.alignWithParent = true; // alignParentIfMissing
        leftLastViewId = viewId;
        view.setId(viewId);
        leftViewList.add(view);
        addView(view, layoutParams);
    }

    /**
     * 在 TopBar 的右侧添加 View，如果此前已经有 iew 通过该方法添加到 TopBar，则新添加进去的View会出现在已有View的左侧
     *
     * @param view   要添加到 TopBar 右边的View
     * @param viewId 该按钮的id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     */
    public void addRightView(View view, int viewId) {
        ViewGroup.LayoutParams viewLayoutParams = view.getLayoutParams();
        LayoutParams layoutParams;
        if (viewLayoutParams != null && viewLayoutParams instanceof LayoutParams) {
            layoutParams = (LayoutParams) viewLayoutParams;
        } else {
            layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        this.addRightView(view, viewId, layoutParams);
    }

    /**
     * 在 TopBar 的右侧添加 View，如果此前已经有 View 通过该方法添加到 TopBar，则新添加进去的 View 会出现在已有View的左侧。
     *
     * @param view         要添加到 TopBar 右边的 View。
     * @param viewId       该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @param layoutParams 生成一个 LayoutParams，当把 Button addView 到 TopBar 时，使用这个 LayouyParams。
     */
    public void addRightView(View view, int viewId, LayoutParams layoutParams) {
        if (rightLastViewId == DEFAULT_VIEW_ID) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        } else {
            layoutParams.addRule(RelativeLayout.LEFT_OF, rightLastViewId);
        }
        layoutParams.alignWithParent = true; // alignParentIfMissing
        rightLastViewId = viewId;
        view.setId(viewId);
        rightViewList.add(view);
        addView(view, layoutParams);
    }

    /**
     * 生成一个 LayoutParams，当把 Button addView 到 TopBar 时，使用这个 LayouyParams
     */
    public LayoutParams generateTopBarImageButtonLayoutParams() {
        LayoutParams lp = new LayoutParams(topBarImageBtnWidth, topBarImageBtnHeight);
        lp.topMargin = Math.max(0, (getTopBarHeight() - topBarImageBtnHeight) / 2);
        return lp;
    }

    /**
     * 根据 resourceId 生成一个 TopBar 的按钮，并 add 到 TopBar 的右侧
     *
     * @param drawableResId 按钮图片的 resourceId
     * @param viewId        该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return 返回生成的按钮
     */
    public ImageView addRightImageButton(int drawableResId, int viewId) {
        ImageView rightButton = generateTopBarImageButton(drawableResId);
        this.addRightView(rightButton, viewId, generateTopBarImageButtonLayoutParams());
        return rightButton;
    }

    /**
     * 根据 resourceId 生成一个 TopBar 的按钮，并 add 到 TopBar 的左边
     *
     * @param drawableResId 按钮图片的 resourceId
     * @param viewId        该按钮的 id，可在ids.xml中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return 返回生成的按钮
     */
    public ImageView addLeftImageButton(int drawableResId, int viewId) {
        ImageView leftButton = generateTopBarImageButton(drawableResId);
//        leftButton.setBackgroundColor(R.color.smui_config_color_transparent_20);
//        LayoutParams lp = new LayoutParams(topBarImageBtnHeight, topBarImageBtnHeight);
//        lp.topMargin = Math.max(0, (getTopBarHeight() - topBarImageBtnHeight) / 2);
        leftButton.setScaleType(ImageView.ScaleType.FIT_XY);
        this.addLeftView(leftButton, viewId, generateTopBarImageButtonLayoutParams());
//        this.addLeftView(leftButton, viewId, lp);

        return leftButton;
    }


    /**
     * 生成一个LayoutParams，当把 Button addView 到 TopBar 时，使用这个 LayouyParams
     */
    public LayoutParams generateTopBarTextButtonLayoutParams() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, topBarImageBtnHeight);
        lp.topMargin = Math.max(0, (getTopBarHeight() - topBarImageBtnHeight) / 2);
        return lp;
    }

    /**
     * 在 TopBar 左边添加一个 Button，并设置文字
     *
     * @param stringResId 按钮的文字的 resourceId
     * @param viewId      该按钮的id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return 返回生成的按钮
     */
    public Button addLeftTextButton(int stringResId, int viewId) {
        return addLeftTextButton(getResources().getString(stringResId), viewId);
    }

    /**
     * 在 TopBar 左边添加一个 Button，并设置文字
     *
     * @param buttonText 按钮的文字
     * @param viewId     该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return 返回生成的按钮
     */
    public Button addLeftTextButton(String buttonText, int viewId) {
        Button button = generateTopBarTextButton(buttonText);
        this.addLeftView(button, viewId, generateTopBarTextButtonLayoutParams());
        return button;
    }

    /**
     * 在 TopBar 右边添加一个 Button，并设置文字
     *
     * @param stringResId 按钮的文字的 resourceId
     * @param viewId      该按钮的id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return 返回生成的按钮
     */
    public Button addRightTextButton(int stringResId, int viewId) {
        return addRightTextButton(getResources().getString(stringResId), viewId);
    }

    /**
     * 在 TopBar 右边添加一个 Button，并设置文字
     *
     * @param buttonText 按钮的文字
     * @param viewId     该按钮的 id，可在 ids.xml 中找到合适的或新增。手工指定 viewId 是为了适应自动化测试。
     * @return 返回生成的按钮
     */
    public Button addRightTextButton(String buttonText, int viewId) {
        Button button = generateTopBarTextButton(buttonText);
        this.addRightView(button, viewId, generateTopBarTextButtonLayoutParams());
        return button;
    }

    /**
     * 生成一个文本按钮，并设置文字
     *
     * @param text 按钮的文字
     * @return 返回生成的按钮
     */
    private Button generateTopBarTextButton(String text) {
        Button button = new Button(getContext());
        button.setBackgroundResource(0);
        button.setMinWidth(0);
        button.setMinHeight(0);
        button.setMinimumWidth(0);
        button.setMinimumHeight(0);
        button.setPadding(topBarTextBtnPaddingHor, 0, topBarTextBtnPaddingHor, 0);
        button.setTextColor(topBarTextBtnTextColor);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, topBarTextBtnTextSize);
        button.setGravity(Gravity.CENTER);
        button.setText(text);
        return button;
    }

    /**
     * 生成一个图片按钮，配合 {{@link #generateTopBarImageButtonLayoutParams()} 使用
     *
     * @param imageResourceId 图片的 resId
     */
    private ImageView generateTopBarImageButton(int imageResourceId) {
        ImageView backButton = new ImageView(getContext());
//        backButton.setBackgroundColor(Color.TRANSPARENT);
        backButton.setBackgroundResource(R.drawable.smui_btn_circle_pressed);
        backButton.setImageResource(imageResourceId);
        return backButton;
    }

    /**
     * 便捷方法，在 TopBar 左边添加一个返回图标按钮
     *
     * @return 返回按钮
     */
    public ImageView addLeftBackImageButton() {
        return addLeftImageButton(leftBackDrawableRes, R.id.smui_topbar_item_left_back);
    }

    /**
     * 移除 TopBar 左边所有的 View
     */
    public void removeAllLeftViews() {
        for (View leftView : leftViewList) {
            removeView(leftView);
        }
        leftLastViewId = DEFAULT_VIEW_ID;
        leftViewList.clear();
    }

    /**
     * 移除 TopBar 右边所有的 View
     */
    public void removeAllRightViews() {
        for (View rightView : rightViewList) {
            removeView(rightView);
        }
        rightLastViewId = DEFAULT_VIEW_ID;
        rightViewList.clear();
    }

    /**
     * 移除 TopBar 的 centerView 和 titleView
     */
    public void removeCenterViewAndTitleView() {
        if (centerView != null) {
            if (centerView.getParent() == this) {
                removeView(centerView);
            }
            centerView = null;
        }

        if (titleView != null) {
            if (titleView.getParent() == this) {
                removeView(titleView);
            }
            titleView = null;
        }
    }

    protected int getTopBarHeight() {
        if (topbarHeight == -1) {
            topbarHeight = SMUIResHelper.getAttrDimen(getContext(), R.attr.smui_topbar_height);
        }
        return topbarHeight;
    }

    // ======================== TopBar自身相关的方法

    /**
     * 设置 TopBar 背景的透明度
     *
     * @param alpha 取值范围：[0, 255]，255表示不透明
     */
    public void setBackgroundAlpha(int alpha) {
        this.getBackground().setAlpha(alpha);
    }

    /**
     * 根据当前 offset、透明度变化的初始 offset 和目标 offset，计算并设置 Topbar 的透明度
     *
     * @param currentOffset     当前 offset
     * @param alphaBeginOffset  透明度开始变化的offset，即当 currentOffset == alphaBeginOffset 时，透明度为0
     * @param alphaTargetOffset 透明度变化的目标offset，即当 currentOffset == alphaTargetOffset 时，透明度为1
     */
    public int computeAndSetBackgroundAlpha(int currentOffset, int alphaBeginOffset, int alphaTargetOffset) {
        double alpha = (float) (currentOffset - alphaBeginOffset) / (alphaTargetOffset - alphaBeginOffset);
        alpha = Math.max(0, Math.min(alpha, 1)); // from 0 to 1
        int alphaInt = (int) (alpha * 255);
        this.setBackgroundAlpha(alphaInt);
        return alphaInt;
    }

    /**
     * 设置是否要 Topbar 底部的分割线
     */
    public void setBackgroundDividerEnabled(boolean enabled) {
        if (enabled) {
            if (topBarBgWithSeparatorDrawableCache == null) {
                topBarBgWithSeparatorDrawableCache = SMUIDrawableHelper.
                        createItemSeparatorBg(topBarSeparatorColor, topBarBgColor, topBarSeparatorHeight, false);
            }
            SMUIViewHelper.setBackgroundKeepingPadding(this, topBarBgWithSeparatorDrawableCache);
        } else {
            SMUIViewHelper.setBackgroundColorKeepPadding(this, topBarBgColor);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (titleContainerView != null) {
            // 计算左侧 View 的总宽度
            int leftViewWidth = 0;
            for (int leftViewIndex = 0; leftViewIndex < leftViewList.size(); leftViewIndex++) {
                View view = leftViewList.get(leftViewIndex);
                if (view.getVisibility() != GONE) {
                    leftViewWidth += view.getMeasuredWidth();
                }
            }
            // 计算右侧 View 的总宽度
            int rightViewWidth = 0;
            for (int rightViewIndex = 0; rightViewIndex < rightViewList.size(); rightViewIndex++) {
                View view = rightViewList.get(rightViewIndex);
                if (view.getVisibility() != GONE) {
                    rightViewWidth += view.getMeasuredWidth();
                }
            }
            // 计算 titleContainer 的最大宽度
            int titleContainerWidth;
            if ((titleGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                if (leftViewWidth == 0 && rightViewWidth == 0) {
                    // 左右没有按钮时，title 距离 TopBar 左右边缘的距离
                    leftViewWidth += titleMarginHorWhenNoBtnAside;
                    rightViewWidth += titleMarginHorWhenNoBtnAside;
                }


                // 标题水平居中，左右两侧的占位要保持一致
                titleContainerWidth = MeasureSpec.getSize(widthMeasureSpec) - Math.max(leftViewWidth, rightViewWidth) * 2 - getPaddingLeft() - getPaddingRight();
            } else {
                // 标题非水平居中，左右没有按钮时，间距分别计算
                if (leftViewWidth == 0) {
                    leftViewWidth += titleMarginHorWhenNoBtnAside;
                }
                if (rightViewWidth == 0) {
                    rightViewWidth += titleMarginHorWhenNoBtnAside;
                }

                // 标题非水平居中，左右两侧的占位按实际计算即可
                titleContainerWidth = MeasureSpec.getSize(widthMeasureSpec) - leftViewWidth - rightViewWidth - getPaddingLeft() - getPaddingRight();
            }
            int titleContainerWidthMeasureSpec = MeasureSpec.makeMeasureSpec(titleContainerWidth, MeasureSpec.EXACTLY);
            titleContainerView.measure(titleContainerWidthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (titleContainerView != null) {
            int titleContainerViewWidth = titleContainerView.getMeasuredWidth();
            int titleContainerViewHeight = titleContainerView.getMeasuredHeight();
            int titleContainerViewTop = (b - t - titleContainerView.getMeasuredHeight()) / 2;
            int titleContainerViewLeft = getPaddingLeft();
            if ((titleGravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.CENTER_HORIZONTAL) {
                // 标题水平居中
                titleContainerViewLeft = (r - l - titleContainerView.getMeasuredWidth()) / 2;
            } else {
                // 标题非水平居中
                // 计算左侧 View 的总宽度
                for (int leftViewIndex = 0; leftViewIndex < leftViewList.size(); leftViewIndex++) {
                    View view = leftViewList.get(leftViewIndex);
                    if (view.getVisibility() != GONE) {
                        titleContainerViewLeft += view.getMeasuredWidth();
                    }
                }

                if (leftViewList.isEmpty()) {
                    //左侧没有按钮，标题离左侧间距
                    titleContainerViewLeft += SMUIResHelper.getAttrDimen(getContext(),
                            R.attr.smui_topbar_title_margin_horizontal_when_no_btn_aside);
                }
            }
            titleContainerView.layout(titleContainerViewLeft, titleContainerViewTop, titleContainerViewLeft + titleContainerViewWidth, titleContainerViewTop + titleContainerViewHeight);
        }
    }

}
