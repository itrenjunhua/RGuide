package com.renj.hightlight.callback;

import android.graphics.RectF;

import com.renj.hightlight.HighLightManager;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-11-21   17:48
 * <p>
 * 描述：增加高亮View的回调
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnPosCallback {
    /**
     * 增加高亮View的回调方法，封装了高亮View的位置信息
     *
     * @param rightMargin  高亮位置右边距
     * @param bottomMargin 高亮位置下边距
     * @param rectF        高亮位置的 {@link RectF} 值
     * @param marginInfo   {@link HighLightManager.MarginInfo} 对象信息
     */
    void getPos(float rightMargin, float bottomMargin, RectF rectF, HighLightManager.MarginInfo marginInfo);
}
