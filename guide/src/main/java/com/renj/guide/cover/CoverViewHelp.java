package com.renj.guide.cover;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.renj.guide.callback.OnDecorScrollListener;
import com.renj.guide.cover.callback.OnCViewRemoveListener;
import com.renj.guide.utils.RGuideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-02    17:18
 * <p/>
 * 描述：操作引导工具帮助类——覆盖页面类型帮助类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class CoverViewHelp {
    private List<RCoverViewParams> viewArrayList;
    private View showCoverView;
    private int scaledTouchSlop;
    private OnCViewRemoveListener onCViewRemoveListener;

    public CoverViewHelp() {
        viewArrayList = new ArrayList<>();
    }

    /**
     * 在整个窗体上面增加一层布局，默认点击移除视图。<b>需要调用 {@link #showCoverView()} 方法</b>
     *
     * @param rCoverViewParams {@link RCoverViewParams} 对象
     * @return {@link CoverViewHelp} 类对象
     */
    public CoverViewHelp addCoverView(@NonNull RCoverViewParams rCoverViewParams) {
        if (rCoverViewParams == null) {
            throw new IllegalArgumentException("Params coverViewParams is null!");
        }
        viewArrayList.add(rCoverViewParams);
        return this;
    }

    /**
     * 显示高亮布局，点击之后自动显示下一个高亮视图
     */
    public void showCoverView() {
        showNext();
    }

    /**
     * 显示下一个高亮布局
     */
    private void showNext() {
        // 如果有没有移除的高亮布局，就先移除
        if (showCoverView != null)
            removeCoverView(false);
        if (viewArrayList.isEmpty()) return;

        final RCoverViewParams rCoverViewParams = viewArrayList.get(0);
        final FrameLayout rootView = (FrameLayout) getRootView(rCoverViewParams.activity);
        if (rCoverViewParams.coverView == null)
            rCoverViewParams.coverView = View.inflate(rCoverViewParams.activity, rCoverViewParams.coverLayoutId, null);
        if (rCoverViewParams.onCViewInflateListener != null) {
            rCoverViewParams.onCViewInflateListener.onInflateFinish(rCoverViewParams, rCoverViewParams.coverView);
        }
        scaledTouchSlop = ViewConfiguration.get(rCoverViewParams.activity).getScaledTouchSlop();
        // 触摸监听
        rCoverViewParams.coverView.setOnTouchListener(new View.OnTouchListener() {
            /**
             * 滑动坐标
             */
            private float downX, downY;
            private float moveX, moveY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX = event.getX();
                        moveY = event.getY();
                        if (rCoverViewParams.onDecorScrollListener != null) {
                            float offsetX = Math.abs(moveX - downX);
                            float offsetY = Math.abs(moveY - downY);
                            if ((offsetX > offsetY) && (offsetX > scaledTouchSlop)) {
                                int axis = (moveX > downX) ? OnDecorScrollListener.AXIS_POSITIVE : OnDecorScrollListener.AXIS_NEGATIVE;
                                rCoverViewParams.onDecorScrollListener.onScroll(rCoverViewParams.coverView,
                                        OnDecorScrollListener.SCROLL_HORIZONTAL, axis);
                            } else if (offsetY > scaledTouchSlop) {
                                int axis = (moveY > downY) ? OnDecorScrollListener.AXIS_POSITIVE : OnDecorScrollListener.AXIS_NEGATIVE;
                                rCoverViewParams.onDecorScrollListener.onScroll(rCoverViewParams.coverView,
                                        OnDecorScrollListener.SCROLL_VERTICAL, axis);
                            }
                        }
                        downX = moveX;
                        downY = moveY;
                        break;
                }
                return false;
            }
        });
        // 点击监听
        rCoverViewParams.coverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rCoverViewParams.autoRemoveView) {
                    // 从集合和父布局中移除
                    viewArrayList.remove(rCoverViewParams);
                    rootView.removeView(rCoverViewParams.coverView);
                    if (rCoverViewParams.blurView != null)
                        rootView.removeView(rCoverViewParams.blurView);
                    // 回调移除方法
                    callBackRemoveListener(viewArrayList.isEmpty(), viewArrayList);
                    // 修改变量 showCoverView 的值，防止异常现象
                    if (viewArrayList.isEmpty()) showCoverView = null;
                }

                // 自动显示下一个
                if (rCoverViewParams.autoShowNext)
                    showNext();

                // 遮罩点击监听
                if (rCoverViewParams.onDecorClickListener != null) {
                    rCoverViewParams.onDecorClickListener.onClick();
                }
            }
        });
        if (rCoverViewParams.maskIsBlur && rCoverViewParams.maskBlurRadius > 0) {
            rCoverViewParams.blurView = new ImageView(rCoverViewParams.activity);
            rCoverViewParams.blurView.setImageBitmap(
                    RGuideUtils.viewToBlurBitmap(rCoverViewParams.activity, rootView, rCoverViewParams.maskBlurRadius)
            );
            rootView.addView(rCoverViewParams.blurView);
        }
        rootView.addView(rCoverViewParams.coverView);
        showCoverView = rCoverViewParams.coverView;
    }

    /**
     * 移除当前的遮罩层，默认会同时清除其他的遮罩层，{@link #removeCoverView(boolean)}
     *
     * @see #removeCoverView(boolean)
     * @see #skipAllCoverView()
     */
    public void removeCoverView() {
        removeCoverView(true);
    }

    /**
     * 移除当前的遮罩层，并设置是否需要移除其他的遮罩层。<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果移除（clearOtherCoverView值传true），那么该页面就不会在显示遮罩层了，除非再次添加和显示<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果不移除（false）并且后面还有，那么可以继续调用 {@link #showCoverView()} 方法显示。
     *
     * @param clearOtherCoverView 是否清除其他的遮罩层
     * @see #removeCoverView()
     * @see #skipAllCoverView()
     */
    public void removeCoverView(boolean clearOtherCoverView) {
        if (viewArrayList.isEmpty()) return;
        RCoverViewParams rCoverViewParams = viewArrayList.get(0);
        FrameLayout rootView = (FrameLayout) getRootView(rCoverViewParams.activity);
        viewArrayList.remove(rCoverViewParams);
        rootView.removeView(rCoverViewParams.coverView);
        if (rCoverViewParams.blurView != null)
            rootView.removeView(rCoverViewParams.blurView);

        showCoverView = null;

        if (clearOtherCoverView) {
            skipAllCoverView();
            callBackRemoveListener(false, viewArrayList);
        } else {
            callBackRemoveListener(viewArrayList.isEmpty(), viewArrayList);
        }
    }

    /**
     * 移除后面的遮罩层/跳过后面所有的遮罩层
     *
     * @see #removeCoverView()
     * @see #removeCoverView(boolean)
     */
    public void skipAllCoverView() {
        viewArrayList.clear();
        showCoverView = null;
    }

    /**
     * 设置遮罩移除监听
     *
     * @param onCViewRemoveListener
     */
    public void setOnCViewRemoveListener(OnCViewRemoveListener onCViewRemoveListener) {
        this.onCViewRemoveListener = onCViewRemoveListener;
    }

    /**
     * 回调方法
     *
     * @param hasCoverView       是否还有已添加，但未显示的遮罩
     * @param notShownCoverViews 未显示的遮罩参数信息集合，如果 hasCoverView 为false，集合元素为空
     */
    private void callBackRemoveListener(boolean hasCoverView, List<RCoverViewParams> notShownCoverViews) {
        if (onCViewRemoveListener != null)
            onCViewRemoveListener.onRemoveCoverView(hasCoverView, notShownCoverViews);
    }

    /**
     * 获取内容区域根视图
     *
     * @param activity {@link Activity}
     * @return 返回内容区域根视图
     */
    private ViewGroup getRootView(@NonNull Activity activity) {
        return (ViewGroup) activity.findViewById(android.R.id.content);
    }
}
