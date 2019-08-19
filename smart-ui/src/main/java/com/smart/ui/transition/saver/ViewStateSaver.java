package com.smart.ui.transition.saver;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * @date : 2019-08-19 14:39
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ViewStateSaver implements Parcelable {

    public ViewStateSaver() {
    }


    public void captureViewInfo(View view, Bundle bundle) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected ViewStateSaver(Parcel in) {
    }

    public static final Creator<ViewStateSaver> CREATOR = new Creator<ViewStateSaver>() {
        @Override
        public ViewStateSaver createFromParcel(Parcel source) {
            return new ViewStateSaver(source);
        }

        @Override
        public ViewStateSaver[] newArray(int size) {
            return new ViewStateSaver[size];
        }
    };
}
