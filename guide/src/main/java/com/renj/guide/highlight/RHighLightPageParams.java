package com.renj.guide.highlight;

import android.app.Activity;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.guide.callback.OnDecorClickListener;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-12-13   14:13
 * <p>
 * 描述：高亮页面参数，页面根布局、Activity、遮罩层颜色等
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RHighLightPageParams {
    Activity activity;
    /**
     * 需要增加高亮区域的根布局
     */
    View anchor;
    /**
     * 背景颜色
     */
    int maskColor = 0x66000000;
    /**
     * 背景是否需要模糊效果，默认不需要
     */
    boolean maskIsBlur = false;
    /**
     * 模糊半径大小，越大表示越模糊，取值[0,25]
     */
    int maskBlurRadius = 18;
    /**
     * 是否自动移除高亮遮罩，默认true
     */
    boolean autoRemoveView = true;
    /**
     * 当移除(手动或自动)之后是否自动显示下一个高亮，如果有的话。默认true
     */
    boolean autoShowNext = true;
    /**
     * 装饰布局点击回调
     */
    OnDecorClickListener onDecorClickListener;

    private RHighLightPageParams(Activity activity) {
        this.activity = activity;
        anchor = activity.findViewById(android.R.id.content);
    }

    /**
     * 创建 {@link RHighLightPageParams}
     */
    public static RHighLightPageParams create(@NonNull Activity activity) {
        if (activity == null)
            throw new IllegalArgumentException("Params activity is null!");
        return new RHighLightPageParams(activity);
    }

    /* ------------------ 设置属性方法 ----------------------*/

    /**
     * 设置背景颜色
     *
     * @param maskColor 背景颜色
     */
    public RHighLightPageParams setMaskColor(int maskColor) {
        this.maskColor = maskColor;
        return this;
    }

    /**
     * 设置根布局
     *
     * @param anchor 根布局，Activity中可以不设置
     */
    public RHighLightPageParams setAnchor(View anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * 设置背景是否需要模糊效果，效果为高斯模糊 {@link #setAnchor(View)} 方法设置的布局，默认大小为 18
     *
     * @param maskIsBlur true：需要 false：不需要
     * @see #setMaskBlur(boolean, int)
     */
    public RHighLightPageParams setMaskIsBlur(boolean maskIsBlur) {
        this.maskIsBlur = maskIsBlur;
        return this;
    }

    /**
     * 设置模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     *
     * @param maskBlurRadius 模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @see #setMaskBlur(boolean, int)
     */
    public RHighLightPageParams setMaskBlurRadius(@IntRange(from = 0, to = 25) int maskBlurRadius) {
        if (maskBlurRadius < 0) maskBlurRadius = 0;
        if (maskBlurRadius > 25) maskBlurRadius = 25;
        this.maskBlurRadius = maskBlurRadius;
        return this;
    }

    /**
     * 设置背景是否需要模糊效果，并且指定模糊大小。效果为高斯模糊 {@link #setAnchor(View)} 方法设置的布局，默认大小为 18
     *
     * @param maskIsBlur     true：需要 false：不需要
     * @param maskBlurRadius 模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @see #setMaskIsBlur(boolean)
     * @see #setMaskBlurRadius(int)
     */
    public RHighLightPageParams setMaskBlur(boolean maskIsBlur, @IntRange(from = 0, to = 25) int maskBlurRadius) {
        setMaskIsBlur(maskIsBlur);
        setMaskBlurRadius(maskBlurRadius);
        return this;
    }

    /**
     * 设置点击任意位置是否自动移除高亮遮罩并且自动显示下一个高亮，默认true。<br/>
     * <b>与 {@link #setAutoRemoveView(boolean)} 和 {@link #setAutoShowNext(boolean)} 方法互斥，后设置生效</b>
     *
     * @param autoRemoveAndShowNextView true：移除 false：不移除
     */
    public RHighLightPageParams setAutoRemoveAndShowNextView(boolean autoRemoveAndShowNextView) {
        this.setAutoRemoveView(autoRemoveAndShowNextView);
        this.setAutoShowNext(autoRemoveAndShowNextView);
        return this;
    }

    /**
     * 设置点击任意位置是否自动移除高亮遮罩，默认true<br/>
     * <b>与 {@link #setAutoRemoveAndShowNextView(boolean)}方法互斥，后设置生效</b>
     *
     * @param autoRemoveView true：移除 false：不移除
     */
    public RHighLightPageParams setAutoRemoveView(boolean autoRemoveView) {
        this.autoRemoveView = autoRemoveView;
        return this;
    }

    /**
     * 当移除(手动或自动)之后是否自动显示下一个高亮，如果有的话。默认true<br/>
     * <b>与 {@link #setAutoRemoveAndShowNextView(boolean)}方法互斥，后设置生效</b>
     *
     * @param autoShowNext true：自动显示 false：不自动显示
     */
    public RHighLightPageParams setAutoShowNext(boolean autoShowNext) {
        this.autoShowNext = autoShowNext;
        return this;
    }

    /**
     * 设置装饰布局点击回调
     *
     * @param onDecorClickListener {@link OnDecorClickListener}
     * @return 当前 {@link RHighLightPageParams} 对象，方便链式调用
     */
    public RHighLightPageParams setOnDecorClickListener(OnDecorClickListener onDecorClickListener) {
        this.onDecorClickListener = onDecorClickListener;
        return this;
    }

    /* ------------------ 获取属性方法 ----------------------*/

    /**
     * 获取Activity
     *
     * @return 方法 {@link #create(Activity)} 设置的值
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * 获取根布局
     *
     * @return 方法 {@link #setAnchor(View)} 设置的值
     */
    public View getAnchor() {
        return anchor;
    }

    /**
     * 获取背景颜色
     *
     * @return 方法 {@link #setMaskColor(int)} 设置的值
     */
    public int getMaskColor() {
        return maskColor;
    }

    /**
     * 获取背景是否需要模糊效果，默认不需要
     *
     * @return 方法 {@link #setMaskIsBlur(boolean)} 或 {@link #setMaskBlur(boolean, int)} 设置的值
     */
    public boolean isMaskIsBlur() {
        return maskIsBlur;
    }

    /**
     * 获取糊半径大小，越大表示越模糊，取值[0,25]
     *
     * @return 方法 {@link #setMaskBlurRadius(int)} 或 {@link #setMaskBlur(boolean, int)} 设置的值
     */
    public int getMaskBlurRadius() {
        return maskBlurRadius;
    }

    /**
     * 是否自动移除，方法 {@link #setAutoRemoveView(boolean)} 设置的值
     *
     * @return true：自动移除 false：不自动移除
     */
    public boolean isAutoRemoveView() {
        return autoRemoveView;
    }

    /**
     * 是否自动显示下一个，方法 {@link #setAutoShowNext(boolean)} 设置的值
     *
     * @return true：自动显示下一个 false：不自动显示下一个
     */
    public boolean isAutoShowNext() {
        return autoShowNext;
    }
}
