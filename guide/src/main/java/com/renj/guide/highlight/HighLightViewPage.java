package com.renj.guide.highlight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
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
 * 描述：高亮显示部分控件类型辅助页面
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
class HighLightViewPage {

    List<RHighLightViewParams> highLightViewParams;
    RHighLightPageParams rHighLightPageParams;
    private HighLightViewHelp highLightViewHelp;
    private HighLightView highLightView; // 显示的高亮View

    /**
     * 构造函数
     */
    HighLightViewPage(RHighLightPageParams rHighLightPageParams, HighLightViewHelp highLightViewHelp) {
        this.rHighLightPageParams = rHighLightPageParams;
        this.highLightViewHelp = highLightViewHelp;
        highLightViewParams = new ArrayList<>();
    }

    /**
     * 增加高亮布局。需要显示的话，<b>需要调用 {@link #show()} 方法</b>
     *
     * @param rHighLightViewParams
     * @return {@link HighLightViewPage} 类对象
     */
    HighLightViewPage addHighLight(RHighLightViewParams rHighLightViewParams) {
        ViewGroup parent = (ViewGroup) rHighLightPageParams.anchor;
        if (rHighLightViewParams.highView == null)
            rHighLightViewParams.highView = parent.findViewById(rHighLightViewParams.highViewId);
        if (rHighLightViewParams.highView == null)
            throw new IllegalArgumentException("Couldn't find the highlighted view." +
                    "Call the HighLightBgParams#setHighView(View)/ HighLightBgParams#setHighView(int) method.");
        RectF rect = new RectF(getLocationInView(parent, rHighLightViewParams.highView));
        HighLightMarginInfo marginInfo = new HighLightMarginInfo();
        rHighLightViewParams.onHLDecorPositionCallback.decorPositionInfo(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, marginInfo);
        rHighLightViewParams.setRectF(rect);
        rHighLightViewParams.setMarginInfo(marginInfo);
        highLightViewParams.add(rHighLightViewParams);
        return this;
    }

    /**
     * 显示含有高亮区域的页面
     */
    void show() {
        highLightView = new HighLightView(rHighLightPageParams, highLightViewParams);
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

        highLightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rHighLightPageParams.autoRemoveView)
                    remove();

                if (rHighLightPageParams.autoShowNext)
                    highLightViewHelp.showNext();

                if (rHighLightPageParams.onDecorClickListener != null)
                    rHighLightPageParams.onDecorClickListener.onClick();
            }
        });
    }

    /**
     * 移除含有高亮区域的页面
     */
    void remove() {
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
     * 获取子View在父View中的位置
     *
     * @param parent 父View
     * @param child  子View
     * @return Rect对象
     */
    private Rect getLocationInView(View parent, View child) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException("parent and child can not be null .");
        }

        Rect result = new Rect();
        if (child == parent) {
            child.getHitRect(result);
            return result;
        }

        View tmp = child;
        Rect tmpRect = new Rect();
        View decorView = null;

        Context context = child.getContext();
        if (context instanceof Activity) {
            decorView = ((Activity) context).getWindow().getDecorView();
        }

        String noSaveStateFrameLayout = "android.support.v4.app.NoSaveStateFrameLayout";
        while (tmp != decorView && tmp != parent) {
            tmp.getHitRect(tmpRect);
            if (!noSaveStateFrameLayout.equals(tmp.getClass().getName())) {
                result.left += tmpRect.left;
                result.top += tmpRect.top;
            }
            tmp = (View) tmp.getParent();
        }

        result.right = result.left + child.getMeasuredWidth();
        result.bottom = result.top + child.getMeasuredHeight();
        return result;
    }

    /*********************** 监听 ************************/
    OnRemoveViewListener onRemoveViewListener;

    void setOnRemoveViewListener(OnRemoveViewListener onRemoveViewListener) {
        this.onRemoveViewListener = onRemoveViewListener;
    }

    /**
     * 移出监听
     */
    interface OnRemoveViewListener {
        void onRemove(HighLightViewPage highLightViewPage);
    }

}
