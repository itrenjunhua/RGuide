package com.renj.highlight;

import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-02    17:18
 * <p/>
 * 描述：操作引导工具帮助类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
class HighLightViewHelp {

    private List<RHighLightViewParams> highLightViewParams;
    private RHighLightPageParams rHighLightPageParams;

    /**
     * 构造函数
     */
    HighLightViewHelp(RHighLightPageParams rHighLightPageParams) {
        this.rHighLightPageParams = rHighLightPageParams;
        highLightViewParams = new ArrayList<>();
    }

    /**
     * 增加高亮布局。需要显示的话，<b>需要调用 {@link #show()} 方法</b>
     *
     * @param rHighLightViewParams
     * @return {@link HighLightViewHelp} 类对象
     */
    HighLightViewHelp addHighLight(RHighLightViewParams rHighLightViewParams) {
        ViewGroup parent = (ViewGroup) rHighLightPageParams.anchor;
        if (rHighLightViewParams.highView == null)
            rHighLightViewParams.highView = parent.findViewById(rHighLightViewParams.highViewId);
        if (rHighLightViewParams.highView == null)
            throw new IllegalArgumentException("Couldn't find the highlighted view." +
                    "Call the HighLightBgParams#setHighView(View)/ HighLightBgParams#setHighView(int) method.");
        RectF rect = new RectF(ViewUtils.getLocationInView(parent, rHighLightViewParams.highView));
        HighLightMarginInfo marginInfo = new HighLightMarginInfo();
        rHighLightViewParams.onPosCallback.decorPosInfo(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, marginInfo);
        rHighLightViewParams.setRectF(rect);
        rHighLightViewParams.setMarginInfo(marginInfo);
        highLightViewParams.add(rHighLightViewParams);
        return this;
    }

    /**
     * 显示含有高亮区域的页面
     */
    void show() {
        final HighLightView highLightView = new HighLightView(rHighLightPageParams, highLightViewParams);
        if (rHighLightPageParams.anchor instanceof FrameLayout) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rHighLightPageParams.anchor).addView(highLightView, ((ViewGroup) rHighLightPageParams.anchor).getChildCount(), lp);
        } else {
            FrameLayout frameLayout = new FrameLayout(rHighLightPageParams.activity);
            ViewGroup parent = (ViewGroup) rHighLightPageParams.anchor.getParent();
            parent.removeView(rHighLightPageParams.anchor);
            parent.addView(frameLayout, rHighLightPageParams.anchor.getLayoutParams());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(rHighLightPageParams.anchor, lp);

            frameLayout.addView(highLightView);
        }

//        if (highLightParams.intercept) {
        highLightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(highLightView);
                if (rHighLightPageParams.onClickCallback != null) {
                    rHighLightPageParams.onClickCallback.onClick();
                }
            }
        });
//        }
    }

    /**
     * 移除含有高亮区域的页面
     */
    void remove(HighLightView highLightView) {
        if (highLightView == null) return;
        ViewGroup parent = (ViewGroup) highLightView.getParent();
        if (parent instanceof RelativeLayout || parent instanceof FrameLayout) {
            parent.removeView(highLightView);
        } else {
            parent.removeView(highLightView);
            View origin = parent.getChildAt(0);
            ViewGroup graParent = (ViewGroup) parent.getParent();
            graParent.removeView(parent);
            graParent.addView(origin, parent.getLayoutParams());
        }
        onRemoveViewListener.onRemove(this);
    }

    /**
     * 将一个布局文件加到根布局上，默认点击移除视图。<b>不需要调用 {@link #show()} 方法</b>
     *
     * @param layoutId 布局文件资源id
     * @return {@link HighLightViewHelp} 类对象
     */
//    void addLayout(@LayoutRes int layoutId) {
//        ViewUtils.addView(highLightParams.activity, layoutId, new ViewUtils.OnViewClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (highLightParams.intercept && (highLightParams.onClickCallback != null)) {
//                    highLightParams.onClickCallback.onClick();
//                }
//            }
//        });
//    }

    OnRemoveViewListener onRemoveViewListener;

    void setOnRemoveViewListener(OnRemoveViewListener onRemoveViewListener) {
        this.onRemoveViewListener = onRemoveViewListener;
    }

    /**
     * 移出监听
     */
    interface OnRemoveViewListener {
        void onRemove(HighLightViewHelp highLightViewHelp);
    }

}
