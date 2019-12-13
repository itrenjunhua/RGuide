package com.renj.highlight.callback;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-11-21   17:35
 * <p>
 * 描述：点击回调接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnClickCallback {
    /**
     * 点击回调方法，要想点击有效果，必须设置intercept为TRUE
     */
    void onClick();
}