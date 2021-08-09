package com.renj.guide.highlight.callback;

import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-09   23:17
 * <p>
 * 描述：装饰布局初始化完成回调
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnHLDecorInflateListener {
    /**
     * 装饰布局初始化完成回调
     *
     * @param decorLayoutView 装饰布局根控件
     */
    void onInflateFinish(View decorLayoutView);
}
