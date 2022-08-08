package com.renj.guide.cover;

import android.app.Activity;
import android.support.annotation.IntRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.renj.guide.callback.OnDecorClickListener;
import com.renj.guide.callback.OnDecorScrollListener;
import com.renj.guide.cover.callback.OnCViewInflateListener;
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
     * 遮罩层布局(覆盖布局) 布局文件id
     */
    @LayoutRes
    int coverLayoutId;
    /**
     * 是否自动移除高亮遮罩，默认true
     */
    boolean autoRemoveView = true;
    /**
     * 当移除(手动或自动)之后是否自动显示下一个高亮，如果有的话。默认true
     */
    boolean autoShowNext = true;
    /**
     * 遮罩层布局初始化完成回调
     */
    OnCViewInflateListener onCViewInflateListener;
    /**
     * 遮罩层布局点击回调
     */
    OnDecorClickListener onDecorClickListener;
    /**
     * 遮罩层布局滑动回调
     */
    OnDecorScrollListener onDecorScrollListener;
    /**
     * 遮罩层布局(覆盖布局)
     */
    View coverView;
    /**
     * 模糊布局
     */
    ImageView blurView;
    /**
     * 背景是否需要模糊效果，默认不需要
     */
    boolean maskIsBlur = false;
    /**
     * 模糊半径大小，越大表示越模糊，取值[0,25]
     */
    int maskBlurRadius = 18;

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
     * 设置覆盖布局。<b>和方法 {@link #setCoverLayoutView(View)} 二选一即可，两个都设置了，该方法优先级更低</b>
     *
     * @param coverLayoutId 覆盖布局id
     * @return 当前 {@link RCoverViewParams} 对象，方便链式调用
     * @see #setCoverLayoutView(View)
     */
    public RCoverViewParams setCoverLayoutId(@LayoutRes int coverLayoutId) {
        if (coverLayoutId == -1) {
            throw new IllegalArgumentException("Params coverLayoutId Exception !");
        }

        this.coverLayoutId = coverLayoutId;
        return this;
    }

    /**
     * 设置覆盖布局。<b>和方法 {@link #setCoverLayoutId(int)} 二选一即可，两个都设置了，该方法优先级更高</b>
     *
     * @param coverView 覆盖布局
     * @return 当前 {@link RCoverViewParams} 对象，方便链式调用
     * @see #setCoverLayoutId(int)
     */
    public RCoverViewParams setCoverLayoutView(@NonNull View coverView) {
        if (coverView == null) {
            throw new IllegalArgumentException("Params coverView is null !");
        }

        this.coverView = coverView;
        return this;
    }

    /**
     * 设置点击任意位置是否自动移除覆盖布局并且自动显示下一个覆盖布局，默认true。<br/>
     * <b>与 {@link #setAutoRemoveView(boolean)} 和 {@link #setAutoShowNext(boolean)} 方法互斥，后设置生效</b>
     *
     * @param autoRemoveAndShowNextView true：移除 false：不移除
     */
    public RCoverViewParams setAutoRemoveAndShowNextView(boolean autoRemoveAndShowNextView) {
        this.setAutoRemoveView(autoRemoveAndShowNextView);
        this.setAutoShowNext(autoRemoveAndShowNextView);
        return this;
    }

    /**
     * 设置点击任意位置是否自动移除覆盖布局，默认true<br/>
     * <b>与 {@link #setAutoRemoveAndShowNextView(boolean)}方法互斥，后设置生效</b>
     *
     * @param autoRemoveView true：移除 false：不移除
     */
    public RCoverViewParams setAutoRemoveView(boolean autoRemoveView) {
        this.autoRemoveView = autoRemoveView;
        return this;
    }

    /**
     * 当移除(手动或自动)之后是否自动显示下一个覆盖布局，如果有的话。默认true<br/>
     * <b>与 {@link #setAutoRemoveAndShowNextView(boolean)}方法互斥，后设置生效</b>
     *
     * @param autoShowNext true：自动显示 false：不自动显示
     */
    public RCoverViewParams setAutoShowNext(boolean autoShowNext) {
        this.autoShowNext = autoShowNext;
        return this;
    }

    /**
     * 遮罩层布局初始化完成回调
     *
     * @param onCViewInflateListener {@link OnCViewInflateListener}
     * @return 当前 {@link RCoverViewParams} 对象，方便链式调用
     */
    public RCoverViewParams setOnCViewInflateListener(OnCViewInflateListener onCViewInflateListener) {
        this.onCViewInflateListener = onCViewInflateListener;
        return this;
    }

    /**
     * 设置遮罩层点击回调
     *
     * @param onDecorClickListener {@link OnDecorClickListener}
     * @return 当前 {@link RCoverViewParams} 对象，方便链式调用
     */
    public RCoverViewParams setOnDecorClickListener(OnDecorClickListener onDecorClickListener) {
        this.onDecorClickListener = onDecorClickListener;
        return this;
    }

    /**
     * 设置遮罩层滑动监听
     *
     * @param onDecorScrollListener {@link OnDecorScrollListener}
     * @return 当前 {@link RCoverViewParams} 对象，方便链式调用
     */
    public RCoverViewParams setOnDecorScrollListener(OnDecorScrollListener onDecorScrollListener) {
        this.onDecorScrollListener = onDecorScrollListener;
        return this;
    }

    /**
     * 设置背景是否需要模糊效果，默认大小为 18
     *
     * @param maskIsBlur true：需要 false：不需要
     * @see #setMaskBlur(boolean, int)
     */
    public RCoverViewParams setMaskIsBlur(boolean maskIsBlur) {
        this.maskIsBlur = maskIsBlur;
        return this;
    }

    /**
     * 设置模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     *
     * @param maskBlurRadius 模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @see #setMaskBlur(boolean, int)
     */
    public RCoverViewParams setMaskBlurRadius(@IntRange(from = 0, to = 25) int maskBlurRadius) {
        if (maskBlurRadius < 0) maskBlurRadius = 0;
        if (maskBlurRadius > 25) maskBlurRadius = 25;
        this.maskBlurRadius = maskBlurRadius;
        return this;
    }

    /**
     * 设置背景是否需要模糊效果，并且指定模糊大小。默认大小为 18
     *
     * @param maskIsBlur     true：需要 false：不需要
     * @param maskBlurRadius 模糊半径大小，值越大，模糊效果越明显，取值 [0,25]
     * @see #setMaskIsBlur(boolean)
     * @see #setMaskBlurRadius(int)
     */
    public RCoverViewParams setMaskBlur(boolean maskIsBlur, @IntRange(from = 0, to = 25) int maskBlurRadius) {
        setMaskIsBlur(maskIsBlur);
        setMaskBlurRadius(maskBlurRadius);
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
     * 获取遮罩布局ID
     *
     * @return 方法 {@link #setCoverLayoutId(int)} 设置的值
     */
    public int getCoverLayoutId() {
        return coverLayoutId;
    }

    /**
     * 获取遮罩布局
     *
     * @return 方法 {@link #setCoverLayoutView(View)}  设置的值
     */
    public View getCoverView() {
        return coverView;
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
}
