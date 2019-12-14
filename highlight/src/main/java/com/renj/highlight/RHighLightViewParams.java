package com.renj.highlight;

import android.graphics.RectF;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.highlight.callback.OnPosCallback;
import com.renj.highlight.type.BorderLineType;
import com.renj.highlight.type.HighLightShape;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-11-21   17:34
 * <p>
 * 描述：高亮View相关参数设置，高亮View、边界效果、高亮形状等
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RHighLightViewParams {
    /**
     * 高亮View
     */
    View highView;
    /**
     * 高亮ViewId
     */
    int highViewId;
    /**
     * 高亮背景装饰布局
     */
    int decorLayoutId = -1;
    /**
     * 高亮形状，默认矩形
     */
    HighLightShape highLightShape = HighLightShape.RECTANGULAR;
    /**
     * 圆角大小，默认6，只有当形状为 {@link HighLightShape#RECTANGULAR} 时生效
     */
    int radius = 6;
    /**
     * 是否需要边框，默认显示
     */
    boolean borderShow = true;
    /**
     * 边框颜色，默认和背景颜色一样
     */
    int borderColor = 0x99000000;
    /**
     * 边框类型,默认实线 {@link BorderLineType#FULL_LINE}
     */
    BorderLineType borderLineType = BorderLineType.FULL_LINE;
    /**
     * 边框宽度，单位：dp，默认1dp
     */
    float borderWidth = 1;
    /**
     * 虚线的排列方式，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true} 并且
     * 边框类型为 {@link BorderLineType#DASH_LINE}，该样式才能生效
     */
    float[] intervals = new float[]{4, 4};
    /**
     * 是否需要模糊边界，默认不需要
     */
    boolean blurShow = false;
    /**
     * 模糊边界大小，默认6dp
     */
    int blurSize = 6;
    /**
     * 高亮位置参数
     */
    RectF rectF;
    /**
     * 高亮区域和高亮说明背景左上右下的边距信息对象
     */
    HighLightMarginInfo marginInfo;

    /**
     * 是否拦截点击事件
     */
    //boolean intercept = true;

    /**
     * 高亮边距信息设置接口
     */
    OnPosCallback onPosCallback;

    /* ------------------ 设置属性方法 ----------------------*/

    private RHighLightViewParams() {
    }

    RHighLightViewParams setRectF(RectF rectF) {
        this.rectF = rectF;
        return this;
    }

    RHighLightViewParams setMarginInfo(HighLightMarginInfo marginInfo) {
        this.marginInfo = marginInfo;
        return this;
    }

    public static RHighLightViewParams create() {
        return new RHighLightViewParams();
    }

    /**
     * 需要高亮的View。<b>和方法 {@link #setHighView(int)} 二选一即可，若两个都设置了，该方法优先级更高</b>
     *
     * @param highView
     * @return
     * @see #setHighView(int)
     */
    public RHighLightViewParams setHighView(View highView) {
        this.highView = highView;
        return this;
    }

    /**
     * 需要高亮的ViewId。<b>和方法 {@link #setHighView(View)} 二选一即可，两个都设置了，该方法优先级更低</b>
     *
     * @param highViewId
     * @return
     * @see #setHighView(View)
     */
    public RHighLightViewParams setHighView(@IdRes int highViewId) {
        this.highViewId = highViewId;
        return this;
    }

    /**
     * 设置高亮背景装饰布局
     *
     * @param decorLayoutId
     * @return
     */
    public RHighLightViewParams setDecorLayoutId(@LayoutRes int decorLayoutId) {
        if (decorLayoutId == -1) {
            throw new IllegalArgumentException("Params decorLayoutId Exception !");
        }
        this.decorLayoutId = decorLayoutId;
        return this;
    }

    /**
     * 设置高亮形状，默认 矩形
     *
     * @param highLightShape {@link HighLightShape}
     * @return
     */
    public RHighLightViewParams setHighLightShape(HighLightShape highLightShape) {
        this.highLightShape = highLightShape;
        return this;
    }

    /**
     * 设置圆角度数。只有当形状为 {@link HighLightShape#RECTANGULAR} 时生效
     *
     * @param radius 圆角度数
     */
    public RHighLightViewParams setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * 设置是否需要边框
     *
     * @param borderShow 是否显示边框 true:显示 false:不显示
     */
    public RHighLightViewParams setBorderShow(boolean borderShow) {
        this.borderShow = borderShow;
        return this;
    }

    /**
     * 设置边框宽度，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，
     * 该方法才能生效；不需要转换单位，默认dp
     *
     * @param borderWidth 边框宽度
     */
    public RHighLightViewParams setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    /**
     * 设置边框颜色，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param borderColor 边框颜色
     */
    public RHighLightViewParams setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * 设置边框类型，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param borderLineType 边框类型 {@link BorderLineType}
     */
    public RHighLightViewParams setBorderLineType(BorderLineType borderLineType) {
        this.borderLineType = borderLineType;
        return this;
    }

    /**
     * 设置虚线边框的样式，需要调用 {@link #setBorderShow(boolean)} 方法设置为 {@code true}并且
     * 边框类型为 {@link BorderLineType#DASH_LINE}，该方法才能生效；不需要转换单位，默认dp
     * <p/>
     * 必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
     * 如在 new float[] { 1, 2, 4, 8}中,表示先绘制长度1的实线,再绘制长度2的空白,再绘制长度4的实线,
     * 再绘制长度8的空白,依次重复
     *
     * @param intervals 虚线边框的样式
     */
    public RHighLightViewParams setIntervals(@NonNull float[] intervals) {
        if (intervals == null)
            throw new IllegalArgumentException("Params intervals is null!");

        int length = intervals.length;
        if ((length >= 2) && (length % 2 == 0)) {
            this.intervals = intervals;
        } else {
            throw new IllegalArgumentException("元素的个数必须大于2并且是偶数");
        }
        return this;
    }


    /**
     * 设置是否需要模糊化边框，默认不显示
     *
     * @param blurShow 是否显示模糊边框 true：显示 false：不显示
     * @return {@link RHighLightManager} 类对象
     */
    public RHighLightViewParams setBlurShow(boolean blurShow) {
        this.blurShow = blurShow;
        return this;
    }

    /**
     * 设置模糊边界的宽度，需要调用 {@link #setBlurShow(boolean)} 方法设置为 {@code true}，该方法才能生效，单位dp
     *
     * @param blurSize 模糊边界的宽度 默认6dp
     */
    public RHighLightViewParams setBlurWidth(int blurSize) {
        this.blurSize = blurSize;
        return this;
    }


    /**
     * 设置是否需要拦截点击事件，默认拦截
     *
     * @param intercept 是否需要拦截点击事件 true：拦截 false：不拦截
     */
//    public HighLightViewParams setIntercept(boolean intercept) {
//        this.intercept = intercept;
//        return this;
//    }

    /**
     * 设置高亮控件和它的装饰控件相对位置
     *
     * @param onPosCallback
     * @return
     */
    public RHighLightViewParams setOnPosCallback(OnPosCallback onPosCallback) {
        if (onPosCallback == null) {
            throw new IllegalArgumentException("Params onPosCallback is null!");
        }
        this.onPosCallback = onPosCallback;
        return this;
    }

    /* ------------------ 深度克隆方法 ----------------------*/

    /**
     * 深度克隆出一个新的 {@link RHighLightViewParams} 对象，可以在继承老的参数之后进行部分修改
     *
     * @return
     */
    public RHighLightViewParams cloneParams() {
        RHighLightViewParams cloneRHighLightViewParams = RHighLightViewParams.create();
        cloneRHighLightViewParams.highView = this.highView;
        cloneRHighLightViewParams.highViewId = this.highViewId;
        cloneRHighLightViewParams.decorLayoutId = this.decorLayoutId;
        cloneRHighLightViewParams.highLightShape = this.highLightShape;
        cloneRHighLightViewParams.radius = this.radius;
        cloneRHighLightViewParams.borderShow = this.borderShow;
        cloneRHighLightViewParams.borderWidth = this.borderWidth;
        cloneRHighLightViewParams.borderColor = this.borderColor;
        cloneRHighLightViewParams.borderLineType = this.borderLineType;
        cloneRHighLightViewParams.intervals = this.intervals;
        cloneRHighLightViewParams.blurShow = this.blurShow;
        cloneRHighLightViewParams.blurSize = this.blurSize;
        cloneRHighLightViewParams.onPosCallback = this.onPosCallback;
        //cloneHighLightViewParams.intercept = this.intercept;
        return cloneRHighLightViewParams;
    }
}
