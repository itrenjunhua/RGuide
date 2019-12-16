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
    private static List<RCoverViewParams> viewArrayList = new ArrayList<>();

    public CoverViewHelp() {
    }

    /**
     * 在整个窗体上面增加一层布局，默认点击移除视图。<b>需要调用 {@link #show()} 方法</b>
     *
     * @param rCoverViewParams {@link RCoverViewParams} 对象
     * @return {@link CoverViewHelp} 类对象
     */
    public void addCoverView(@NonNull RCoverViewParams rCoverViewParams) {
        if (rCoverViewParams == null) {
            throw new IllegalArgumentException("Params coverViewParams is null!");
        }
        viewArrayList.add(rCoverViewParams);
    }

    /**
     * 显示高亮布局，点击之后自动显示下一个高亮视图
     */
    public void show() {
        showNext();
    }

    /**
     * 显示下一个高亮布局
     */
    private void showNext() {
        if (viewArrayList.isEmpty()) return;

        final RCoverViewParams rCoverViewParams = viewArrayList.get(0);
        final FrameLayout rootView = (FrameLayout) getRootView(rCoverViewParams.activity);
        final View coverView = View.inflate(rCoverViewParams.activity, rCoverViewParams.coverLayoutId, null);
        if (rCoverViewParams.onCoverViewListener != null) {
            rCoverViewParams.onCoverViewListener.onCoverView(rCoverViewParams, coverView);
        }
        coverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewArrayList.remove(rCoverViewParams);
                rootView.removeView(coverView);
                showNext();
                if (rCoverViewParams.onDecorClickListener != null) {
                    rCoverViewParams.onDecorClickListener.onClick();
                }
            }
        });
        rootView.addView(coverView);
    }

    /**
     * 移除指定的遮罩层
     *
     * @param rCoverViewParams
     * @param coverView
     */
    public void removeCoverView(@NonNull RCoverViewParams rCoverViewParams, @NonNull View coverView) {
        FrameLayout rootView = (FrameLayout) getRootView(rCoverViewParams.activity);
        viewArrayList.remove(rCoverViewParams);
        rootView.removeView(coverView);
    }

    /**
     * 跳过后面所有的遮罩层
     */
    public void skipAll() {
        viewArrayList.clear();
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
