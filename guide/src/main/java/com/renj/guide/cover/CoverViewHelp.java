package com.renj.guide.cover;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
        rCoverViewParams.coverView = View.inflate(rCoverViewParams.activity, rCoverViewParams.coverLayoutId, null);
        if (rCoverViewParams.onCoverViewInflateFinishListener != null) {
            rCoverViewParams.onCoverViewInflateFinishListener.onInflateFinish(rCoverViewParams, rCoverViewParams.coverView);
        }
        rCoverViewParams.coverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rCoverViewParams.autoRemoveView) {
                    viewArrayList.remove(rCoverViewParams);
                    rootView.removeView(rCoverViewParams.coverView);
                    if (viewArrayList.isEmpty()) showCoverView = null;
                }

                if (rCoverViewParams.autoShowNext)
                    showNext();

                if (rCoverViewParams.onDecorClickListener != null) {
                    rCoverViewParams.onDecorClickListener.onClick();
                }
            }
        });
        rootView.addView(rCoverViewParams.coverView);
        showCoverView = rCoverViewParams.coverView;
    }

    /**
     * 移除指定的遮罩层，默认会同时清除其他的遮罩层，{@link #removeCoverView(boolean)}
     *
     * @see #removeCoverView(boolean)
     * @see #skipAllCoverView()
     */
    public void removeCoverView() {
        removeCoverView(true);
    }

    /**
     * 移除指定的遮罩层，并设置是否需要移除其他的遮罩层。<br/>
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

        showCoverView = null;

        if (clearOtherCoverView) skipAllCoverView();
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
     * 获取内容区域根视图
     *
     * @param activity {@link Activity}
     * @return 返回内容区域根视图
     */
    private ViewGroup getRootView(@NonNull Activity activity) {
        return (ViewGroup) activity.findViewById(android.R.id.content);
    }
}
