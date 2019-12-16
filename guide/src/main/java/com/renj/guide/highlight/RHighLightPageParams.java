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
     * 设置背景颜色
     *
     * @param anchor 根布局，Activity中可以不设置
     */
    public RHighLightPageParams setAnchor(View anchor) {
        this.anchor = anchor;
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
        cloneRHighLightPageParams.onDecorClickListener = this.onDecorClickListener;
        return cloneRHighLightPageParams;
    }
}
