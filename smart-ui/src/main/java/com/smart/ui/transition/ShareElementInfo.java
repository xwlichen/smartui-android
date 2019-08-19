package com.smart.ui.transition;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.smart.ui.R;
import com.smart.ui.transition.saver.ViewStateSaver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @date : 2019-08-19 14:38
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ShareElementInfo
        <T extends Parcelable> implements Parcelable {

    private transient View view;
    /**
     * 用于存放{@link android.app.SharedElementCallback#onCreateSnapshotView}里的snapshot
     */
    private Parcelable snapshot;
    /**
     * 存放View相关的数据。用于定位切换页面后新的ShareElement
     */
    protected T data;
    /**
     * 用于Transition判断当前是进入还是退出
     */
    private boolean isEnter;

    private Bundle fromViewBundle = new Bundle();

    private Bundle toViewBundle = new Bundle();

    private ViewStateSaver viewStateSaver;

    public ShareElementInfo(@NonNull View view) {
        this(view, null, null);
    }

    public ShareElementInfo(@NonNull View view, @Nullable T data) {
        this(view, data, null);
    }

    public ShareElementInfo(@NonNull View view, ViewStateSaver viewStateSaver) {
        this(view, null, viewStateSaver);
    }

    public ShareElementInfo(@NonNull View view, @Nullable T data, ViewStateSaver viewStateSaver) {
        this.view = view;
        this.data = data;
        view.setTag(R.id.smui_share_element_info, this);
        this.viewStateSaver = viewStateSaver;
    }

    public Bundle getFromViewBundle() {
        return fromViewBundle;
    }

    public void setFromViewBundle(Bundle fromViewBundle) {
        this.fromViewBundle = fromViewBundle;
    }

    public Bundle getToViewBundle() {
        return toViewBundle;
    }

    public void setToViewBundle(Bundle toViewBundle) {
        this.toViewBundle = toViewBundle;
    }

    public View getView() {
        return view;
    }

    public Parcelable getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Parcelable snapshot) {
        this.snapshot = snapshot;
    }

    public T getData() {
        return data;
    }

    public boolean isEnter() {
        return isEnter;
    }

    public void setEnter(boolean enter) {
        isEnter = enter;
    }

    public ViewStateSaver getViewStateSaver() {
        return viewStateSaver;
    }

    public static ShareElementInfo getFromView(View view) {
        if (view == null) {
            return null;
        }
        Object tag = view.getTag(R.id.smui_share_element_info);
        return tag instanceof ShareElementInfo ? (ShareElementInfo) tag : null;
    }

    public static void saveToView(View view, ShareElementInfo info) {
        if (view == null) {
            return;
        }
        view.setTag(R.id.smui_share_element_info, info);
    }


    public void captureFromViewInfo(View view) {
        if (viewStateSaver != null) {
            viewStateSaver.captureViewInfo(view, fromViewBundle);
        }
    }

    public void captureToViewInfo(View view) {
        if (viewStateSaver != null) {
            viewStateSaver.captureViewInfo(view, toViewBundle);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.snapshot, flags);
        dest.writeParcelable(this.data, flags);
        dest.writeByte(this.isEnter ? (byte) 1 : (byte) 0);
        dest.writeBundle(this.fromViewBundle);
        dest.writeBundle(this.toViewBundle);
        dest.writeParcelable(this.viewStateSaver, flags);
    }

    protected ShareElementInfo(Parcel in) {
        this.snapshot = in.readParcelable(Parcelable.class.getClassLoader());
        this.data = in.readParcelable(getClass().getClassLoader());
        this.isEnter = in.readByte() != 0;
        this.fromViewBundle = in.readBundle();
        this.toViewBundle = in.readBundle();
        this.viewStateSaver = in.readParcelable(ViewStateSaver.class.getClassLoader());
    }

    public static final Creator<ShareElementInfo> CREATOR = new Creator<ShareElementInfo>() {
        @Override
        public ShareElementInfo createFromParcel(Parcel source) {
            return new ShareElementInfo(source);
        }

        @Override
        public ShareElementInfo[] newArray(int size) {
            return new ShareElementInfo[size];
        }
    };
}
