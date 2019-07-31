package com.smart.ui.layout;

/**
 * @author lichen
 * @date ：2019-07-31 21:34
 * @email : 196003945@qq.com
 * @description :
 */
public interface INotchInsetConsumer {
    /**
     * @return if true stop dispatch to child view
     */
    boolean notifyInsetMaybeChanged();
}