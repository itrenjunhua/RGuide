package com.renj.highlight;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.highlight.callback.OnClickCallback;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-12-13   14:13
 * <p>
 * 描述：高亮页面参数
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RHighLightBgParams {
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
     * 拦截事件时点击回调
     */
    OnClickCallback onClickCallback;

    public RHighLightBgParams(Activity activity) {
        this.activity = activity;
        anchor = activity.findViewById(android.R.id.content);
    }


    /**
     * 闯进啊 {@link RHighLightBgParams}
     */
    public static RHighLightBgParams create(@NonNull Activity activity) {
        if (activity == null)
            throw new IllegalArgumentException("Params activity is null!");
        return new RHighLightBgParams(activity);
    }

    /**
     * 设置背景颜色
     *
     * @param maskColor 背景颜色
     */
    public RHighLightBgParams setMaskColor(int maskColor) {
        this.maskColor = maskColor;
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param anchor 根布局，Activity中可以不设置
     */
    public RHighLightBgParams setAnchor(View anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * 设置点击回调
     *
     * @param onClickCallback
     * @return
     */
    public RHighLightBgParams setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
        return this;
    }
}
