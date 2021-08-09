package com.renj.guide.highlight.callback;

import android.graphics.RectF;

import com.renj.guide.highlight.HighLightMarginInfo;
import com.renj.guide.highlight.RHighLightPageParams;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-11-21   17:48
 * <p>
 * 描述：确认高亮控件装饰布局与高亮控件的相对位置
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnHLDecorPositionCallback {
    /**
     * 高亮View的装饰布局与高亮View的相对位置，<br/>
     * 设置时只需要设置 {@link HighLightMarginInfo#leftMargin}和{@link HighLightMarginInfo#topMargin}
     * [或者{@link HighLightMarginInfo#rightMargin}和{@link HighLightMarginInfo#bottomMargin}]就可以了
     *
     * @param rightMargin  高亮控件右边距
     * @param bottomMargin 高亮控件下边距
     * @param rectF        高亮控件在{@link RHighLightPageParams#anchor} 中的 {@link RectF} 值
     * @param marginInfo   {@link HighLightMarginInfo} 对象信息
     */
    void decorPositionInfo(float rightMargin, float bottomMargin, RectF rectF, HighLightMarginInfo marginInfo);
}
