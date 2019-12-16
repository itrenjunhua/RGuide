package com.renj.guide.cover;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import com.renj.guide.highlight.RHighLightPageParams;
import com.renj.guide.callback.OnClickCallback;

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
     * 点击回调
     */
    OnClickCallback onClickCallback;

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
     * 设置点击回调
     *
     * @param onClickCallback
     * @return
     */
    public RCoverViewParams setOnClickCallback(OnClickCallback onClickCallback) {
        this.onClickCallback = onClickCallback;
        return this;
    }

    /* ------------------ 深度克隆方法 ----------------------*/

    /**
     * 深度克隆出一个新的 {@link RCoverViewParams} 对象，可以在继承老的参数之后进行部分修改
     *
     * @return
     */
    public RCoverViewParams cloneParams() {
        RCoverViewParams rCoverViewParams = RCoverViewParams.create(this.activity);
        rCoverViewParams.onClickCallback = this.onClickCallback;
        rCoverViewParams.coverLayoutId = this.coverLayoutId;
        return rCoverViewParams;
    }
}
