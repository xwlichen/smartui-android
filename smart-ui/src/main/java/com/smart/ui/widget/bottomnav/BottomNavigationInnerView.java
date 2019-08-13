package com.smart.ui.widget.bottomnav;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.internal.ThemeEnforcement;
import com.smart.ui.R;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import androidx.appcompat.widget.TintTypedArray;
import androidx.viewpager.widget.ViewPager;

/**
 * @author lichen
 * @date ：2019-08-11 14:41
 * @email : 196003945@qq.com
 * @description : copy from https://github.com/ittianyu/BottomNavigationViewEx
 */
@SuppressLint("RestrictedApi")
public class BottomNavigationInnerView extends BottomNavigationView {

    private float shiftAmount;
    private float scaleUpFactor;
    private float scaleDownFactor;
    private boolean animationRecord;
    private float largeLabelSize;
    private float smallLabelSize;
    private boolean visibilityTextSizeRecord;
    private boolean visibilityHeightRecord;
    private int itemHeight;
    private boolean textVisibility = true;

    private SmartOnNavItemSelectedListener smartOnNavItemSelectedListener;
    private BottomNavigationViewExOnPageChangeListener pageChangeListener;
    private BottomNavigationMenuView menuView;
    private ViewPager viewPager;
    private BottomNavigationItemView[] buttons;

    private static boolean isNavigationItemClicking = false;

    public BottomNavigationInnerView(Context context) {
        this(context, null);
    }

    public BottomNavigationInnerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("PrivateResource")
    public BottomNavigationInnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TintTypedArray a = ThemeEnforcement.obtainTintedStyledAttributes(context, attrs,
                R.styleable.BottomNavigationView,
                defStyleAttr, R.style.Widget_Design_BottomNavigationView,
                R.styleable.BottomNavigationView_itemTextAppearanceInactive,
                R.styleable.BottomNavigationView_itemTextAppearanceActive);
        if (!a.hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
            clearIconTintColor();
        }
        a.recycle();
    }


    public BottomNavigationInnerView setIconVisibility(boolean visibility) {

        final BottomNavigationMenuView menuView = getBottomNavigationMenuView();
        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
        for (BottomNavigationItemView button : mButtons) {
            ImageView mIcon = getField(button.getClass(), button, "icon");
            mIcon.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        }

        if (!visibility) {
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true;
                itemHeight = getItemHeight();
            }

            BottomNavigationItemView button = mButtons[0];
            if (null != button) {
                final ImageView mIcon = getField(button.getClass(), button, "icon");
                if (null != mIcon) {
                    mIcon.post(new Runnable() {
                        @Override
                        public void run() {
                            setItemHeight(itemHeight - mIcon.getMeasuredHeight());
                        }
                    });
                }
            }
        } else {
            if (!visibilityHeightRecord) {
                return this;
            }

            setItemHeight(itemHeight);
        }

        menuView.updateMenuView();
        return this;
    }

    public BottomNavigationInnerView setTextVisibility(boolean visibility) {

        BottomNavigationMenuView menuView = getBottomNavigationMenuView();
        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();

        for (BottomNavigationItemView button : mButtons) {
            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");

            if (!visibility) {
                if (!visibilityTextSizeRecord && !animationRecord) {
                    visibilityTextSizeRecord = true;
                    largeLabelSize = mLargeLabel.getTextSize();
                    smallLabelSize = mSmallLabel.getTextSize();
                }

                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);
                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0);

            } else {
                if (!visibilityTextSizeRecord) {
                    break;
                }

                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, largeLabelSize);
                mSmallLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallLabelSize);
            }
        }

        if (!visibility) {
            if (!visibilityHeightRecord) {
                visibilityHeightRecord = true;
                itemHeight = getItemHeight();
            }


            setItemHeight(itemHeight - getFontHeight(smallLabelSize));

        } else {
            if (!visibilityHeightRecord) {
                return this;
            }
            setItemHeight(itemHeight);
        }

        menuView.updateMenuView();
        return this;
    }


    private static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }


    public BottomNavigationInnerView enableAnimation(boolean enable) {


        BottomNavigationMenuView menuView = getBottomNavigationMenuView();
        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
        for (BottomNavigationItemView button : mButtons) {
            TextView mLargeLabel = getField(button.getClass(), button, "largeLabel");
            TextView mSmallLabel = getField(button.getClass(), button, "smallLabel");

            if (!enable) {
                if (!animationRecord) {
                    animationRecord = true;
                    shiftAmount = getField(button.getClass(), button, "shiftAmount");
                    scaleUpFactor = getField(button.getClass(), button, "scaleUpFactor");
                    scaleDownFactor = getField(button.getClass(), button, "scaleDownFactor");

                    largeLabelSize = mLargeLabel.getTextSize();
                    smallLabelSize = mSmallLabel.getTextSize();
                }
                setField(button.getClass(), button, "shiftAmount", 0);
                setField(button.getClass(), button, "scaleUpFactor", 1);
                setField(button.getClass(), button, "scaleDownFactor", 1);

                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallLabelSize);

            } else {
                if (!animationRecord) {
                    return this;
                }
                setField(button.getClass(), button, "shiftAmount", shiftAmount);
                setField(button.getClass(), button, "scaleUpFactor", scaleUpFactor);
                setField(button.getClass(), button, "scaleDownFactor", scaleDownFactor);
                mLargeLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, largeLabelSize);
            }
        }
        menuView.updateMenuView();
        return this;
    }


    @Deprecated
    public BottomNavigationInnerView enableShiftingMode(boolean enable) {

        setLabelVisibilityMode(enable ? LabelVisibilityMode.LABEL_VISIBILITY_SELECTED : LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        return this;
    }


    @Deprecated
    public BottomNavigationInnerView enableItemShiftingMode(boolean enable) {


        setItemHorizontalTranslationEnabled(enable);

        return this;
    }


    public int getCurrentItem() {
        BottomNavigationItemView[] mButtons = getBottomNavigationItemViews();
        Menu menu = getMenu();
        for (int i = 0; i < mButtons.length; i++) {
            if (menu.getItem(i).isChecked()) {
                return i;
            }
        }
        return 0;
    }


    public int getMenuItemPosition(MenuItem item) {
        int itemId = item.getItemId();
        Menu menu = getMenu();
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            if (menu.getItem(i).getItemId() == itemId) {
                return i;
            }
        }
        return -1;
    }


    public BottomNavigationInnerView setCurrentItem(int index) {
        setSelectedItemId(getMenu().getItem(index).getItemId());
        return this;
    }


    public OnNavigationItemSelectedListener getOnNavigationItemSelectedListener() {
        OnNavigationItemSelectedListener mListener = getField(BottomNavigationView.class, this, "selectedListener");
        return mListener;
    }

    @Override
    public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
        if (null == smartOnNavItemSelectedListener) {
            super.setOnNavigationItemSelectedListener(listener);
            return;
        }

        smartOnNavItemSelectedListener.setOnNavigationItemSelectedListener(listener);
    }


    public BottomNavigationMenuView getBottomNavigationMenuView() {
        if (null == menuView) {
            menuView = getField(BottomNavigationView.class, this, "menuView");
        }
        return menuView;
    }


    public BottomNavigationInnerView clearIconTintColor() {
        getBottomNavigationMenuView().setIconTintList(null);
        return this;
    }


    public BottomNavigationItemView[] getBottomNavigationItemViews() {
        if (null != buttons) {
            return buttons;
        }

        BottomNavigationMenuView menuView = getBottomNavigationMenuView();
        buttons = getField(menuView.getClass(), menuView, "buttons");
        return buttons;
    }


    public BottomNavigationItemView getBottomNavigationItemView(int position) {
        return getBottomNavigationItemViews()[position];
    }


    public ImageView getIconAt(int position) {

        BottomNavigationItemView mButtons = getBottomNavigationItemView(position);
        ImageView mIcon = getField(BottomNavigationItemView.class, mButtons, "icon");
        return mIcon;
    }


    public TextView getSmallLabelAt(int position) {

        BottomNavigationItemView mButtons = getBottomNavigationItemView(position);
        TextView mSmallLabel = getField(BottomNavigationItemView.class, mButtons, "smallLabel");
        return mSmallLabel;
    }


    public TextView getLargeLabelAt(int position) {

        BottomNavigationItemView mButtons = getBottomNavigationItemView(position);
        TextView mLargeLabel = getField(BottomNavigationItemView.class, mButtons, "largeLabel");
        return mLargeLabel;
    }


    public int getItemCount() {
        BottomNavigationItemView[] bottomNavigationItemViews = getBottomNavigationItemViews();
        if (null == bottomNavigationItemViews) {
            return 0;
        }
        return bottomNavigationItemViews.length;
    }

    public BottomNavigationInnerView setSmallTextSize(float sp) {
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            getSmallLabelAt(i).setTextSize(sp);
        }
        menuView.updateMenuView();
        return this;
    }

    public BottomNavigationInnerView setLargeTextSize(float sp) {
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            TextView tvLarge = getLargeLabelAt(i);
            if (null != tvLarge) {
                tvLarge.setTextSize(sp);
            }
        }
        menuView.updateMenuView();
        return this;
    }

    public BottomNavigationInnerView setTextSize(float sp) {
        setLargeTextSize(sp);
        setSmallTextSize(sp);
        return this;
    }


    public BottomNavigationInnerView setIconSizeAt(int position, float width, float height) {
        ImageView icon = getIconAt(position);
        // update size
        ViewGroup.LayoutParams layoutParams = icon.getLayoutParams();
        layoutParams.width = dp2px(getContext(), width);
        layoutParams.height = dp2px(getContext(), height);
        icon.setLayoutParams(layoutParams);

        menuView.updateMenuView();
        return this;
    }


    public BottomNavigationInnerView setIconSize(float width, float height) {
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            setIconSizeAt(i, width, height);
        }
        return this;
    }


    public BottomNavigationInnerView setIconSize(float dpSize) {
        setItemIconSize(dp2px(getContext(), dpSize));
        return this;
    }


    public BottomNavigationInnerView setItemHeight(int height) {
        final BottomNavigationMenuView menuView = getBottomNavigationMenuView();
        setField(menuView.getClass(), menuView, "itemHeight", height);
        menuView.updateMenuView();
        return this;
    }


    public int getItemHeight() {
        final BottomNavigationMenuView menuView = getBottomNavigationMenuView();
        return getField(menuView.getClass(), menuView, "itemHeight");
    }


    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public BottomNavigationInnerView setTypeface(Typeface typeface, int style) {
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            getLargeLabelAt(i).setTypeface(typeface, style);
            getSmallLabelAt(i).setTypeface(typeface, style);
        }
        menuView.updateMenuView();
        return this;
    }

    public BottomNavigationInnerView setTypeface(Typeface typeface) {
        int count = getItemCount();
        for (int i = 0; i < count; i++) {
            getLargeLabelAt(i).setTypeface(typeface);
            getSmallLabelAt(i).setTypeface(typeface);
        }
        menuView.updateMenuView();
        return this;
    }


    private <T> T getField(Class targetClass, Object instance, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void setField(Class targetClass, Object instance, String fieldName, Object value) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public BottomNavigationInnerView setupWithViewPager(final ViewPager viewPager) {
        return setupWithViewPager(viewPager, false);
    }


    public BottomNavigationInnerView setupWithViewPager(final ViewPager viewPager, boolean smoothScroll) {
        if (viewPager != null) {
            if (pageChangeListener != null) {
                viewPager.removeOnPageChangeListener(pageChangeListener);
            }
        }

        if (null == viewPager) {
            this.viewPager = null;
            super.setOnNavigationItemSelectedListener(null);
            return this;
        }

        this.viewPager = viewPager;

        if (pageChangeListener == null) {
            pageChangeListener = new BottomNavigationViewExOnPageChangeListener(this);
        }
        viewPager.addOnPageChangeListener(pageChangeListener);

        OnNavigationItemSelectedListener listener = getOnNavigationItemSelectedListener();
        smartOnNavItemSelectedListener = new SmartOnNavItemSelectedListener(viewPager, this, smoothScroll, listener);
        super.setOnNavigationItemSelectedListener(smartOnNavItemSelectedListener);
        return this;
    }


    private static class BottomNavigationViewExOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<BottomNavigationInnerView> mBnveRef;

        public BottomNavigationViewExOnPageChangeListener(BottomNavigationInnerView bnve) {
            mBnveRef = new WeakReference<>(bnve);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset,
                                   final int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(final int position) {
            final BottomNavigationInnerView bnve = mBnveRef.get();
            if (null != bnve && !isNavigationItemClicking) {
                bnve.setCurrentItem(position);
            }
        }
    }


    private static class SmartOnNavItemSelectedListener implements OnNavigationItemSelectedListener {
        private OnNavigationItemSelectedListener listener;
        private final WeakReference<ViewPager> viewPagerRef;
        private boolean smoothScroll;
        private SparseIntArray items;
        private int previousPosition = -1;


        SmartOnNavItemSelectedListener(ViewPager viewPager, BottomNavigationInnerView bnve, boolean smoothScroll, OnNavigationItemSelectedListener listener) {
            this.viewPagerRef = new WeakReference<>(viewPager);
            this.listener = listener;
            this.smoothScroll = smoothScroll;

            Menu menu = bnve.getMenu();
            int size = menu.size();
            items = new SparseIntArray(size);
            for (int i = 0; i < size; i++) {
                int itemId = menu.getItem(i).getItemId();
                items.put(itemId, i);
            }
        }

        public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            int position = items.get(item.getItemId());
            if (previousPosition == position) {
                return true;
            }
//
            if (null != listener) {
                boolean bool = listener.onNavigationItemSelected(item);
                if (!bool) {
                    return false;
                }
            }

            ViewPager viewPager = viewPagerRef.get();
            if (null == viewPager) {
                return false;
            }

            isNavigationItemClicking = true;
            viewPager.setCurrentItem(items.get(item.getItemId()), smoothScroll);
            isNavigationItemClicking = false;

            previousPosition = position;

            return true;
        }

    }

    public BottomNavigationInnerView enableShiftingMode(int position, boolean enable) {
        getBottomNavigationItemView(position).setShifting(enable);
        return this;
    }

    public BottomNavigationInnerView setItemBackground(int position, int background) {
        getBottomNavigationItemView(position).setItemBackground(background);
        return this;
    }

    public BottomNavigationInnerView setIconTintList(int position, ColorStateList tint) {
        getBottomNavigationItemView(position).setIconTintList(tint);
        return this;
    }

    public BottomNavigationInnerView setTextTintList(int position, ColorStateList tint) {
        getBottomNavigationItemView(position).setTextColor(tint);
        return this;
    }


    public BottomNavigationInnerView setIconsMarginTop(int marginTop) {
        for (int i = 0; i < getItemCount(); i++) {
            setIconMarginTop(i, marginTop);
        }
        return this;
    }


    public BottomNavigationInnerView setIconMarginTop(int position, int marginTop) {

        BottomNavigationItemView itemView = getBottomNavigationItemView(position);
        setField(BottomNavigationItemView.class, itemView, "defaultMargin", marginTop);
        menuView.updateMenuView();
        return this;
    }

}

