package com.renj.guide.highlight;

import android.app.Activity;
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
    int maskColor = 0x99000000;
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
     * 设置点击任意位置是否自动移除高亮遮罩，默认true
     *
     * @param autoRemoveView true：移除 false：不移除
     */
    public RHighLightPageParams setAutoRemoveView(boolean autoRemoveView) {
        this.autoRemoveView = autoRemoveView;
        return this;
    }

    /**
     * 当移除(手动或自动)之后是否自动显示下一个高亮，如果有的话。默认true
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
     * @param onDecorClickListener
     * @return
     */
    public RHighLightPageParams setOnDecorClickListener(OnDecorClickListener onDecorClickListener) {
        this.onDecorClickListener = onDecorClickListener;
        return this;
    }

    /* ------------------ 获取属性方法 ----------------------*/

    /**
     * 获取Activity
     *
     * @return 方法 {@link #create(Activity)} 传递的值
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * 获取根布局
     *
     * @return 方法 {@link #setAnchor(View)} 传递的值
     */
    public View getAnchor() {
        return anchor;
    }

    /**
     * 获取背景颜色
     *
     * @return 方法 {@link #setMaskColor(int)} 传递的值
     */
    public int getMaskColor() {
        return maskColor;
    }

    /**
     * 是否自动移除，方法 {@link #setAutoRemoveView(boolean)} 传递的值
     *
     * @return true：自动移除 false：不自动移除
     */
    public boolean isAutoRemoveView() {
        return autoRemoveView;
    }

    /**
     * 是否自动显示下一个，方法 {@link #setAutoShowNext(boolean)} 传递的值
     *
     * @return true：自动显示下一个 false：不自动显示下一个
     */
    public boolean isAutoShowNext() {
        return autoShowNext;
    }
    /* ------------------ 深度克隆方法 ----------------------*/

    /**
     * 深度克隆出一个新的 {@link RHighLightPageParams} 对象，可以在继承老的参数之后进行部分修改
     *
     * @return
     */
    public RHighLightPageParams cloneParams() {
        RHighLightPageParams cloneRHighLightPageParams = RHighLightPageParams.create(this.activity);
        cloneRHighLightPageParams.anchor = this.anchor;
        cloneRHighLightPageParams.maskColor = this.maskColor;
        cloneRHighLightPageParams.autoRemoveView = this.autoRemoveView;
        cloneRHighLightPageParams.onDecorClickListener = this.onDecorClickListener;
        return cloneRHighLightPageParams;
    }
}
