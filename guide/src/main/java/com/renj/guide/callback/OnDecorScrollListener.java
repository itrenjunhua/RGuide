package com.renj.guide.callback;

import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-09   17:24
 * <p>
 * 描述：整个装饰背景/遮罩层滑动回调接口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnDecorScrollListener {
    /**
     * 垂直方向滑动
     */
    int SCROLL_VERTICAL = 0;
    /**
     * 水平方向滑动
     */
    int SCROLL_HORIZONTAL = 1;

    /**
     * 往正方向滑动，正方向定义与手机坐标轴定义相同
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果是垂直方向滑动，正方向为：手指从上往下滑动
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果是水平方向滑动，正方向为：手指从左往右滑动
     */
    int AXIS_POSITIVE = 0;
    /**
     * 往负方向滑动，正方向定义与手机坐标轴定义相同
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果是垂直方向滑动，负方向为：手指从下往上滑动
     * <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果是水平方向滑动，负方向为：手指从右往左滑动
     */
    int AXIS_NEGATIVE = 1;

    /**
     * 在装饰背景上滑动
     *
     * @param decorView   装饰或遮罩布局
     * @param orientation 滑动的方向 水平/垂直 方向 {@link #SCROLL_VERTICAL} 或 {@link #SCROLL_HORIZONTAL}
     * @param axis        往正方向滑动还是往负方向滑动 {@link #AXIS_POSITIVE} 或 {@link #AXIS_NEGATIVE}
     */
    void onScroll(View decorView, int orientation, int axis);
}
