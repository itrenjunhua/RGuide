package com.renj.hightlight;

import android.graphics.RectF;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.renj.hightlight.callback.OnPosCallback;
import com.renj.hightlight.type.BorderLineType;
import com.renj.hightlight.type.HighLightShape;

import java.util.ArrayList;
import java.util.List;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-02    17:18
 * <p/>
 * 描述：操作引导工具类入口类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class HighLightManager {
    private HighLightBuilder builder;
    /**
     * 保存高亮View的信息的集合
     */
    private List<ViewPosInfo> mViewReacts;
    /**
     * 表示高亮视图的对象
     */
    private HighLightView mHighLightView;

    /**
     * 封装了需要高亮View的信息
     */
    static class ViewPosInfo {
        int layoutId = -1;
        RectF rectF;
        MarginInfo marginInfo;
        View view;
        OnPosCallback onPosCallback;
        HighLightShape highLightShape;
    }

    /**
     * 封装了左上右下的边距
     */
    public static class MarginInfo {
        public float topMargin;
        public float leftMargin;
        public float rightMargin;
        public float bottomMargin;
    }

    /**
     * 构造函数
     */
    HighLightManager(@NonNull HighLightBuilder builder) {
        this.builder = builder;
        mViewReacts = new ArrayList<>();
    }

    /**
     * 增加高亮的布局。需要显示的话，需要调用 {@link #show()} 方法
     *
     * @param viewId        需要高亮的控件id
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLightManager} 类对象
     */
    @SuppressWarnings("unused")
    public HighLightManager addHighLight(int viewId, @LayoutRes int decorLayoutId,
                                         @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) builder.anchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, onPosCallback);
        return this;
    }

    /**
     * 增加高亮布局。需要显示的话，<b>需要调用 {@link #show()} 方法</b>
     *
     * @param viewId        需要高亮的控件id
     * @param decorLayoutId 布局文件资源id
     * @param shape         指定高亮的形状，枚举类型
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLightManager} 类对象
     */
    @SuppressWarnings("unused")
    public HighLightManager addHighLight(int viewId, @LayoutRes int decorLayoutId,
                                         @NonNull HighLightShape shape,
                                         @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) builder.anchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, shape, onPosCallback);
        return this;
    }

    /**
     * 增加高亮布局。需要显示的话，<b>需要调用 {@link #show()} 方法</b>
     *
     * @param view          需要高亮的View
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLightManager} 类对象
     */
    @SuppressWarnings("unused")
    public HighLightManager addHighLight(View view, @LayoutRes int decorLayoutId,
                                         @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) builder.anchor;
        RectF rect = new RectF(ViewUtils.getLocationInView(parent, view));
        ViewPosInfo viewPosInfo = new ViewPosInfo();
        viewPosInfo.layoutId = decorLayoutId;
        viewPosInfo.rectF = rect;
        viewPosInfo.view = view;
        if (onPosCallback == null && decorLayoutId != -1) {
            throw new IllegalArgumentException("参数错误：OnPosCallback == null && decorLayoutId != -1");
        }
        MarginInfo marginInfo = new MarginInfo();
        onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, marginInfo);
        viewPosInfo.marginInfo = marginInfo;
        viewPosInfo.onPosCallback = onPosCallback;
        mViewReacts.add(viewPosInfo);

        return this;
    }

    /**
     * 增加高亮布局。需要显示的话，<b>需要调用 {@link #show()} 方法</b>
     *
     * @param view          高亮布局的视图
     * @param decorLayoutId 布局文件资源id
     * @param shape         指定高亮的形状，枚举类型
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLightManager} 类对象
     */
    @SuppressWarnings("unused")
    public HighLightManager addHighLight(View view, @LayoutRes int decorLayoutId,
                                         @NonNull HighLightShape shape,
                                         @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) builder.anchor;
        RectF rect = new RectF(ViewUtils.getLocationInView(parent, view));
        ViewPosInfo viewPosInfo = new ViewPosInfo();
        viewPosInfo.layoutId = decorLayoutId;
        viewPosInfo.rectF = rect;
        viewPosInfo.view = view;
        if (onPosCallback == null && decorLayoutId != -1) {
            throw new IllegalArgumentException("参数错误：OnPosCallback == null && decorLayoutId != -1");
        }
        MarginInfo marginInfo = new MarginInfo();
        onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, marginInfo);
        viewPosInfo.marginInfo = marginInfo;
        viewPosInfo.highLightShape = shape;
        viewPosInfo.onPosCallback = onPosCallback;
        mViewReacts.add(viewPosInfo);

        return this;
    }

    /**
     * 增加高亮布局。需要显示的话，<b>需要调用 {@link #show()} 方法</b>
     *
     * @param rect          高亮布局的位置
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLightManager} 类对象
     */
    @SuppressWarnings("unused")
    public HighLightManager addHighLight(RectF rect, @LayoutRes int decorLayoutId,
                                         @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) builder.anchor;
        ViewPosInfo viewPosInfo = new ViewPosInfo();
        viewPosInfo.layoutId = decorLayoutId;
        viewPosInfo.rectF = rect;
        if (onPosCallback == null && decorLayoutId != -1) {
            throw new IllegalArgumentException("参数错误：OnPosCallback == null && decorLayoutId != -1");
        }
        MarginInfo marginInfo = new MarginInfo();
        onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, marginInfo);
        viewPosInfo.marginInfo = marginInfo;
        viewPosInfo.onPosCallback = onPosCallback;
        mViewReacts.add(viewPosInfo);

        return this;
    }

    /**
     * 更新位置信息
     */
    @SuppressWarnings("unused")
    void updateInfo() {
        ViewGroup parent = (ViewGroup) builder.anchor;
        for (HighLightManager.ViewPosInfo viewPosInfo : mViewReacts) {
            viewPosInfo.onPosCallback.getPos(parent.getWidth() - viewPosInfo.rectF.right,
                    parent.getHeight() - viewPosInfo.rectF.bottom, viewPosInfo.rectF, viewPosInfo.marginInfo);
        }
    }

    /**
     * 显示含有高亮区域的页面
     */
    @SuppressWarnings("unused")
    public void show() {
        if (mHighLightView != null) return;

        HighLightView highLightView = new HighLightView(builder.activity, this, builder.maskColor, mViewReacts);
        // 设置是否需要模糊边界和模糊边界的大小
        highLightView.setIsBlur(builder.isBlur);
        if (builder.isBlur) highLightView.setBlurWidth(builder.blurSize);

        // 设置边框的相关配置
        highLightView.setIsNeedBorder(builder.isNeedBorder);
        if (builder.isNeedBorder) {
            highLightView.setBorderColor(builder.borderColor);
            highLightView.setBorderWidth(builder.borderWidth);
            highLightView.setBorderLineType(builder.borderLineType);
            if (builder.borderLineType == BorderLineType.DASH_LINE)// 是虚线才需要设置虚线样式
                highLightView.setIntervals(builder.intervals);
        }
        highLightView.setRadius(builder.radius);
        highLightView.setMaskColor(builder.maskColor);

        if (builder.anchor.getClass().getSimpleName().equals("FrameLayout")) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) builder.anchor).addView(highLightView, ((ViewGroup) builder.anchor).getChildCount(), lp);

        } else {
            FrameLayout frameLayout = new FrameLayout(builder.activity);
            ViewGroup parent = (ViewGroup) builder.anchor.getParent();
            parent.removeView(builder.anchor);
            parent.addView(frameLayout, builder.anchor.getLayoutParams());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(builder.anchor, lp);

            frameLayout.addView(highLightView);
        }

        if (builder.intercept) {
            highLightView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove();
                    if (builder.clickCallback != null) {
                        builder.clickCallback.onClick();
                    }
                }
            });
        }

        mHighLightView = highLightView;
    }

    /**
     * 移除含有高亮区域的页面
     */
    @SuppressWarnings("unused")
    private void remove() {
        if (mHighLightView == null) return;
        ViewGroup parent = (ViewGroup) mHighLightView.getParent();
        if (parent instanceof RelativeLayout || parent instanceof FrameLayout) {
            parent.removeView(mHighLightView);
        } else {
            parent.removeView(mHighLightView);
            View origin = parent.getChildAt(0);
            ViewGroup graParent = (ViewGroup) parent.getParent();
            graParent.removeView(parent);
            graParent.addView(origin, parent.getLayoutParams());
        }
        mHighLightView = null;
    }

    /**
     * 将一个布局文件加到根布局上，默认点击移除视图。<b>不需要调用 {@link #show()} 方法</b>
     *
     * @param layoutId 布局文件资源id
     * @return {@link HighLightManager} 类对象
     */
    @SuppressWarnings("unused")
    public void addLayout(@LayoutRes int layoutId) {
        ViewUtils.addView(builder.activity, layoutId, new ViewUtils.OnViewClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.intercept && (builder.clickCallback != null)) {
                    builder.clickCallback.onClick();
                }
            }
        });
    }
}
