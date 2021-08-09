package com.renj.guide.highlight.callback;

import android.support.annotation.IdRes;
import android.view.View;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-09   16:46
 * <p>
 * 描述：高亮位置点击
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnHLViewClickListener {
    /**
     * 高亮位置点击
     *
     * @param highLightView 点击的高亮View
     * @param highViewId    点击的高亮View id（如果调用了 {@link com.renj.guide.highlight.RHighLightViewParams#setHighView(int)} 方法）
     */
    void onHighLightViewClick(View highLightView, @IdRes int highViewId);
}
