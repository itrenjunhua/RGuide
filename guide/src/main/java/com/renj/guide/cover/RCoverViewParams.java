package com.renj.guide.cover;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

import com.renj.guide.callback.OnDecorClickListener;
import com.renj.guide.highlight.RHighLightPageParams;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-12-16   09:52
 * <p>
 * 描述：覆盖页面类型参数
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RCoverViewParams {
    Activity activity;
    /**
     * 覆盖布局
     */
    @LayoutRes
    int coverLayoutId;
    /**
     * 遮罩层布局初始化完成回调
     */
    OnCoverViewInflateFinishListener onCoverViewInflateFinishListener;
    /**
     * 遮罩层布局点击回调
     */
    OnDecorClickListener onDecorClickListener;

    private RCoverViewParams(Activity activity) {
        this.activity = activity;
    }

    /**
     * 创建 {@link RHighLightPageParams}
     */
    public static RCoverViewParams create(@NonNull Activity activity) {
        if (activity == null)
            throw new IllegalArgumentException("Params activity is null!");
        return new RCoverViewParams(activity);
    }

    /* ------------------ 设置属性方法 ----------------------*/

    /**
     * 设置覆盖布局
     *
     * @param coverLayoutId 覆盖布局id
     * @return
     */
    public RCoverViewParams setCoverLayoutId(@LayoutRes int coverLayoutId) {
        if (coverLayoutId == -1) {
            throw new IllegalArgumentException("Params coverLayoutId Exception !");
        }

        this.coverLayoutId = coverLayoutId;
        return this;
    }

    /**
     * 遮罩层布局初始化完成回调
     *
     * @param onCoverViewInflateFinishListener
     * @return
     */
    public RCoverViewParams setOnCoverViewInflateFinishListener(OnCoverViewInflateFinishListener onCoverViewInflateFinishListener) {
        this.onCoverViewInflateFinishListener = onCoverViewInflateFinishListener;
        return this;
    }

    /**
     * 设置遮罩层点击回调
     *
     * @param onDecorClickListener
     * @return
     */
    public RCoverViewParams setOnDecorClickListener(OnDecorClickListener onDecorClickListener) {
        this.onDecorClickListener = onDecorClickListener;
        return this;
    }

    /* ------------------ 深度克隆方法 ----------------------*/

    /**
     * 深度克隆出一个新的 {@link RCoverViewParams} 对象，可以在继承老的参数之后进行部分修改
     *
     * @return
     */
    public RCoverViewParams cloneParams() {
        RCoverViewParams cloneCoverViewParams = RCoverViewParams.create(this.activity);
        cloneCoverViewParams.coverLayoutId = this.coverLayoutId;
        cloneCoverViewParams.onCoverViewInflateFinishListener = this.onCoverViewInflateFinishListener;
        cloneCoverViewParams.onDecorClickListener = this.onDecorClickListener;
        return cloneCoverViewParams;
    }

    /**
     * 遮罩层布局初始化完成回调
     */
    public interface OnCoverViewInflateFinishListener {
        /**
         * 遮罩层布局初始化完成回调
         *
         * @param rCoverViewParams 遮罩View信息
         * @param coverView        遮罩View布局
         */
        void onInflateFinish(RCoverViewParams rCoverViewParams, View coverView);
    }
}
