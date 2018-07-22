package com.renj.hightlight;

import android.support.annotation.NonNull;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-07-22   17:52
 * <p>
 * 描述：高亮工具类参数配置类
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ParamsConfig {
    // 背景颜色
    private int maskColor;
    // 是否需要模糊边界，默认不需要
    private boolean isBlur;
    // 是否需要边框，默认需要
    private boolean isNeedBorder;
    // 模糊边界大小，默认15
    private int blurSize;
    // 圆角大小，默认6
    private int radius;
    // 边框颜色，默认和背景颜色一样
    private int borderColor;
    // 边框类型,默认虚线 {@link HighLight.BorderLineType#DASH_LINE}
    private HighLight.BorderLineType borderLineType;
    // 边框宽度，单位：dp，默认3dp
    private float borderWidth;
    // 虚线的排列方式
    private float[] intervals;

    private ParamsConfig(Builder builder) {
        this.maskColor = builder.maskColor;
        this.isBlur = builder.isBlur;
        this.isNeedBorder = builder.isNeedBorder;
        this.blurSize = builder.blurSize;
        this.radius = builder.radius;
        this.borderColor = builder.borderColor;
        this.borderLineType = builder.borderLineType;
        this.borderWidth = builder.borderWidth;
        this.intervals = builder.intervals;
    }

    public int getMaskColor() {
        return maskColor;
    }

    public boolean isBlur() {
        return isBlur;
    }

    public boolean isNeedBorder() {
        return isNeedBorder;
    }

    public int getBlurSize() {
        return blurSize;
    }

    public int getRadius() {
        return radius;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public HighLight.BorderLineType getBorderLineType() {
        return borderLineType;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public float[] getIntervals() {
        return intervals;
    }

    public static class Builder {
        /**
         * 默认模糊边界的大小
         */
        private static final int DEFAULT_WIDTH_BLUR = 15;
        /**
         * 默认圆角度数
         */
        private static final int DEFAULT_RADIUS = 6;
        /**
         * 背景颜色
         */
        private int maskColor = 0x99000000;

        /**
         * 是否需要模糊边界，默认不需要
         */
        private boolean isBlur = false;
        /**
         * 是否需要边框，默认需要
         */
        private boolean isNeedBorder = true;
        /**
         * 模糊边界大小，默认15
         */
        private int blurSize = DEFAULT_WIDTH_BLUR;
        /**
         * 圆角大小，默认6
         */
        private int radius = DEFAULT_RADIUS;
        /**
         * 边框颜色，默认和背景颜色一样
         */
        private int borderColor = maskColor;
        /**
         * 边框类型,默认虚线 {@link HighLight.BorderLineType#DASH_LINE}
         */
        private HighLight.BorderLineType borderLineType = HighLight.BorderLineType.DASH_LINE;
        /**
         * 边框宽度，单位：dp，默认3dp
         */
        private float borderWidth = 3;
        /**
         * 虚线的排列方式，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true} 并且
         * 边框类型为 {@link HighLight.BorderLineType#DASH_LINE}，该样式才能生效
         */
        private float[] intervals;

        /**
         * 设置是否需要边框
         *
         * @param isNeedBorder 是否需要边框 true:需要 false:不需要
         */
        public Builder setIsNeedBorder(boolean isNeedBorder) {
            this.isNeedBorder = isNeedBorder;
            return this;
        }

        /**
         * 设置是否需要模糊边界
         *
         * @param isBlur 是否需要模糊边界 true:需要 false:不需要
         */
        public Builder setIsBlur(boolean isBlur) {
            this.isBlur = isBlur;
            return this;
        }

        /**
         * 设置模糊边界的宽度，需要调用 {@link #setIsBlur(boolean)} 方法设置为 {@code true}，该方法才能生效
         *
         * @param blurSize 模糊边界的宽度
         */
        public Builder setBlurWidth(int blurSize) {
            this.blurSize = blurSize;
            return this;
        }

        /**
         * 设置边框颜色，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，该方法才能生效
         *
         * @param borderColor 边框颜色
         */
        public Builder setBorderColor(int borderColor) {
            this.borderColor = borderColor;
            return this;
        }

        /**
         * 设置边框类型，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，该方法才能生效
         *
         * @param borderLineType 边框类型 {@link HighLight.BorderLineType}
         */
        public Builder setBorderLineType(HighLight.BorderLineType borderLineType) {
            this.borderLineType = borderLineType;
            return this;
        }

        /**
         * 设置背景颜色
         *
         * @param maskColor 背景颜色
         */
        public Builder setMaskColor(int maskColor) {
            this.maskColor = maskColor;
            return this;
        }

        /**
         * 设置边框宽度，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，
         * 该方法才能生效；不需要转换单位，默认dp
         *
         * @param borderWidth 边框宽度
         */
        public Builder setBorderWidth(float borderWidth) {
            this.borderWidth = borderWidth;
            return this;
        }

        /**
         * 设置虚线边框的样式，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}并且
         * 边框类型为 {@link HighLight.BorderLineType#DASH_LINE}，该方法才能生效；不需要转换单位，默认dp
         * <p/>
         * 必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
         * 如在 new float[] { 1, 2, 4, 8}中,表示先绘制长度1的实线,再绘制长度2的空白,再绘制长度4的实线,
         * 再绘制长度8的空白,依次重复
         *
         * @param intervals 虚线边框的样式
         */
        public Builder setIntervals(@NonNull float[] intervals) {
            int length = intervals.length;
            return this;
        }

        /**
         * 设置圆角度数
         *
         * @param radius 圆角度数
         */
        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public ParamsConfig build() {
            return new ParamsConfig(this);
        }
    }
}
