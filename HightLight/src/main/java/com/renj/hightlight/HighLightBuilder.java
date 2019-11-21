package com.renj.hightlight;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.hightlight.callback.OnClickCallback;
import com.renj.hightlight.type.BorderLineType;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-11-21   17:34
 * <p>
 * 描述：高亮View属性设置
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class HighLightBuilder {
    /**
     * 默认模糊边界的大小
     */
    private static final int DEFAULT_WIDTH_BLUR = 15;
    /**
     * 默认圆角度数
     */
    private static final int DEFAULT_RADIUS = 6;

    Activity activity;
    /**
     * 需要增加高亮区域的根布局
     */
    View anchor;
    /**
     * 是否拦截点击事件
     */
    boolean intercept = true;
    /**
     * 背景颜色
     */
    int maskColor = 0x99000000;
    /**
     * 是否需要模糊边界，默认不需要
     */
    boolean isBlur = false;
    /**
     * 是否需要边框，默认需要
     */
    boolean isNeedBorder = true;
    /**
     * 模糊边界大小，默认15
     */
    int blurSize = DEFAULT_WIDTH_BLUR;
    /**
     * 圆角大小，默认6
     */
    int radius = DEFAULT_RADIUS;
    /**
     * 边框颜色，默认和背景颜色一样
     */
    int borderColor = maskColor;
    /**
     * 边框类型,默认虚线 {@link BorderLineType#DASH_LINE}
     */
    BorderLineType borderLineType = BorderLineType.DASH_LINE;
    /**
     * 边框宽度，单位：dp，默认3dp
     */
    float borderWidth = 3;
    /**
     * 虚线的排列方式，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true} 并且
     * 边框类型为 {@link BorderLineType#DASH_LINE}，该样式才能生效
     */
    float[] intervals = new float[]{4, 4};

    /**
     * 点击事件的回调，要想点击有效果，必须设置intercept为TRUE
     */
    OnClickCallback clickCallback;

    private HighLightBuilder(@NonNull Activity activity) {
        this.activity = activity;
        anchor = activity.findViewById(android.R.id.content);
    }

    /**
     * @param activity
     * @return
     */
    public static HighLightBuilder newInstance(@NonNull Activity activity) {
        if (activity == null)
            throw new IllegalArgumentException("HighLightBuilder#Builder(@NonNull Activity activity) params activity is null!");
        return new HighLightBuilder(activity);
    }

    /**
     * 绑定根布局，需要高亮显示部分区域时需要第一个调用的方法(如果是在Activity中调用，可以不调用)
     *
     * @param anchor 根布局
     */
    @SuppressWarnings("unused")
    public HighLightBuilder anchor(@NonNull View anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * 设置是否需要拦截点击事件
     *
     * @param intercept 是否需要拦截点击事件 true：拦截 false：不拦截
     */
    @SuppressWarnings("unused")
    public HighLightBuilder setIntercept(boolean intercept) {
        int length = intervals.length;
        if ((length >= 2) && (length % 2 == 0)) {
            this.intercept = intercept;
        } else {
            throw new IllegalArgumentException("元素的个数必须大于2并且是偶数");
        }
        return this;
    }

    /**
     * 设置是否需要模糊化边框，默认不需要
     *
     * @param isBlur 是否需要模糊边框 true：需要 false：不需要
     * @return {@link HighLightManager} 类对象
     */
    @SuppressWarnings("unused")
    public HighLightBuilder isBlur(boolean isBlur) {
        this.isBlur = isBlur;
        return this;
    }

    /**
     * 设置是否需要边框
     *
     * @param isNeedBorder 是否需要边框 true:需要 false:不需要
     */
    public HighLightBuilder setIsNeedBorder(boolean isNeedBorder) {
        this.isNeedBorder = isNeedBorder;
        return this;
    }

    /**
     * 设置是否需要模糊边界
     *
     * @param isBlur 是否需要模糊边界 true:需要 false:不需要
     */
    public HighLightBuilder setIsBlur(boolean isBlur) {
        this.isBlur = isBlur;
        return this;
    }

    /**
     * 设置模糊边界的宽度，需要调用 {@link #setIsBlur(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param blurSize 模糊边界的宽度
     */
    public HighLightBuilder setBlurWidth(int blurSize) {
        this.blurSize = blurSize;
        return this;
    }

    /**
     * 设置边框颜色，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param borderColor 边框颜色
     */
    public HighLightBuilder setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * 设置边框类型，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param borderLineType 边框类型 {@link BorderLineType}
     */
    public HighLightBuilder setBorderLineType(BorderLineType borderLineType) {
        this.borderLineType = borderLineType;
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param maskColor 背景颜色
     */
    public HighLightBuilder setMaskColor(int maskColor) {
        this.maskColor = maskColor;
        return this;
    }

    /**
     * 设置边框宽度，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，
     * 该方法才能生效；不需要转换单位，默认dp
     *
     * @param borderWidth 边框宽度
     */
    public HighLightBuilder setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    /**
     * 设置虚线边框的样式，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}并且
     * 边框类型为 {@link BorderLineType#DASH_LINE}，该方法才能生效；不需要转换单位，默认dp
     * <p/>
     * 必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
     * 如在 new float[] { 1, 2, 4, 8}中,表示先绘制长度1的实线,再绘制长度2的空白,再绘制长度4的实线,
     * 再绘制长度8的空白,依次重复
     *
     * @param intervals 虚线边框的样式
     */
    public HighLightBuilder setIntervals(@NonNull float[] intervals) {
        int length = intervals.length;
        if ((length >= 2) && (length % 2 == 0)) {
            this.intervals = intervals;
        } else {
            throw new IllegalArgumentException("元素的个数必须大于2并且是偶数");
        }
        return this;
    }

    /**
     * 设置圆角度数
     *
     * @param radius 圆角度数
     */
    public HighLightBuilder setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * 一个场景可能有多个步骤的高亮。一个步骤完成之后再进行下一个步骤的高亮,
     * 添加点击事件，将每次点击传给应用逻辑
     *
     * @param clickCallback 设置整个引导的点击事件
     * @return {@link HighLightManager} 类对象
     */
    public HighLightBuilder setOnClickCallback(@NonNull OnClickCallback clickCallback) {
        this.clickCallback = clickCallback;
        return this;
    }

    public HighLightManager build() {
        return new HighLightManager(this);
    }
}
