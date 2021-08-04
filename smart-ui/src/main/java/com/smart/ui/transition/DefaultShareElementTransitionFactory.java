package com.smart.ui.transition;

import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.smart.ui.transition.effect.ChangeOnlineImageTransition;
import com.smart.ui.transition.effect.ChangeTextTransition;

import java.util.List;

/**
 * @date : 2019-08-19 14:46
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DefaultShareElementTransitionFactory implements IShareElementTransitionFactory {

    protected boolean useDefaultImageTransform = false;

    @Override
    public Transition buildShareElementEnterTransition(List<View> shareViewList) {
        return buildShareElementsTransition(shareViewList);
    }

    @Override
    public Transition buildShareElementExitTransition(List<View> shareViewList) {
        return buildShareElementsTransition(shareViewList);
    }

    protected TransitionSet buildShareElementsTransition(List<View> shareViewList) {
        TransitionSet transitionSet = new TransitionSet();
        if (shareViewList == null || shareViewList.size() == 0) {
            return transitionSet;
        }
        transitionSet.addTransition(new ChangeClipBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTextTransition());
        if (useDefaultImageTransform) {
            transitionSet.addTransition(new ChangeImageTransform());
        } else {
            transitionSet.addTransition(new ChangeOnlineImageTransition());
        }
        return transitionSet;
    }

    @Override
    public Transition buildEnterTransition() {
        return new Fade();
    }

    @Override
    public Transition buildExitTransition() {
        return new Fade();
    }
}
