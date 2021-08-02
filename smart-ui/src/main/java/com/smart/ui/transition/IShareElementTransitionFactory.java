package com.smart.ui.transition;

import android.transition.Transition;
import android.view.View;

import java.util.List;

/**
 * @date : 2019-08-19 14:44
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface IShareElementTransitionFactory {

    Transition buildShareElementEnterTransition(List<View> shareViewList);

    Transition buildShareElementExitTransition(List<View> shareViewList);

    Transition buildEnterTransition();

    Transition buildExitTransition();
}
