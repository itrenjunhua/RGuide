package com.renj.guide.cover.callback;

import android.view.View;

import com.renj.guide.cover.RCoverViewParams;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-09   23:23
 * <p>
 * 描述：遮罩层布局初始化完成回调
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnCViewInflateListener {
    /**
     * 遮罩层布局初始化完成回调
     *
     * @param rCoverViewParams 遮罩View信息
     * @param coverView        遮罩View布局
     */
    void onInflateFinish(RCoverViewParams rCoverViewParams, View coverView);
}
