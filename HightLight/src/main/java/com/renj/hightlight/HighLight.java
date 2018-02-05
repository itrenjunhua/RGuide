package com.renj.hightlight;

import android.app.Activity;
import android.graphics.RectF;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.renj.hightlight.util.ViewUtils;
import com.renj.hightlight.view.HightLightView;

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
public class HighLight {
    /**
     * 默认模糊边界的大小
     */
    private static final int DEFAULT_WIDTH_BLUR = 15;
    /**
     * 默认圆角度数
     */
    private static final int DEFAULT_RADIUS = 6;
    /**
     * 需要增加高亮区域的根布局
     */
    private View mAnchor;
    /**
     * 保存高亮View的信息的集合
     */
    private List<ViewPosInfo> mViewRects;
    /**
     * Activity对象
     */
    private Activity mContext;
    /**
     * 表示高亮视图的对象
     */
    private HightLightView mHightLightView;
    /**
     * 点击事件的回调，要想点击有效果，必须设置intercept为TRUE
     */
    private OnClickCallback clickCallback;
    /**
     * 是否拦截点击事件
     */
    private boolean intercept = true;
    /**
     * 是否需要模糊化边界，默认不需要
     */
    private boolean shadow = false;
    /**
     * 默认背景颜色
     */
    private int maskColor = 0x99000000;
    /**
     * ViewUtils对象
     */
    private ViewUtils viewUtils;
    /**
     * 设置是否需要边框，默认需要
     */
    private boolean isNeedBorder = true;
    /**
     * 设置边框颜色，默认和背景颜色一样
     */
    private int borderColor = maskColor;
    /**
     * 模糊边界大小，默认15
     */
    private int blurSize = DEFAULT_WIDTH_BLUR;
    /**
     * 圆角大小，默认6
     */
    private int radius = DEFAULT_RADIUS;
    /**
     * 边框类型,默认虚线 HighLight.BorderLineType.DASH_LINE
     */
    private BorderLineType borderLineType = BorderLineType.DASH_LINE;
    /**
     * 边框宽度，单位：dp，默认3dp
     */
    private float borderWidth = 3;
    /**
     * 虚线的排列方式，需要setIsNeedBorder(true)并且边框类型为HighLight.BorderLineType.DASH_LINE，该样式才能生效
     */
    private float[] intervals = new float[]{4, 4};


    /**
     * 表示需要高亮的形状，圆形、矩形
     */
    public enum HightLightShape {
        /**
         * 圆形
         */
        CIRCULAR,
        /**
         * 矩形
         */
        RECTANGULAR
    }

    /**
     * 边框的样式，实线、虚线
     */
    public enum BorderLineType {
        /**
         * 实线
         */
        FULL_LINE,
        /**
         * 虚线
         */
        DASH_LINE
    }

    /**
     * 封装了需要高亮View的信息
     */
    public static class ViewPosInfo {

        public int layoutId = -1;
        public RectF rectF;
        public MarginInfo marginInfo;
        public View view;
        public OnPosCallback onPosCallback;
        public HightLightShape hightLightShape;
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
     * 增加高亮View的回调
     */
    public interface OnPosCallback {
        /**
         * 增加高亮View的回调方法，封装了高亮View的位置信息
         *
         * @param rightMargin  高亮位置右边距
         * @param bottomMargin 高亮位置下边距
         * @param rectF        高亮位置的 {@link RectF} 值
         * @param marginInfo   {@link MarginInfo} 对象信息
         */
        void getPos(float rightMargin, float bottomMargin, RectF rectF, MarginInfo marginInfo);
    }

    /**
     * 点击回调接口
     */
    public interface OnClickCallback {
        /**
         * 点击回调方法，要想点击有效果，必须设置intercept为TRUE
         */
        void onClick();
    }

    /**
     * 构造函数
     *
     * @param activity Activity
     */
    public HighLight(@NonNull Activity activity) {
        mContext = activity;
        viewUtils = ViewUtils.newInstance();
        viewUtils.initActivity(activity);
        mViewRects = new ArrayList<>();
        mAnchor = activity.findViewById(android.R.id.content);
    }

    /**
     * 一个场景可能有多个步骤的高亮。一个步骤完成之后再进行下一个步骤的高亮,
     * 添加点击事件，将每次点击传给应用逻辑
     *
     * @param clickCallback 设置整个引导的点击事件
     * @return {@link HighLight} 类对象
     */
    public HighLight setOnClickCallback(@NonNull OnClickCallback clickCallback) {
        this.clickCallback = clickCallback;
        return this;
    }

    /**
     * 绑定根布局，需要高亮显示部分区域时需要第一个调用的方法(如果是在Activity中调用，可以不调用)
     *
     * @param anchor 根布局
     * @return {@link HighLight} 类对象
     */
    public HighLight anchor(@NonNull View anchor) {
        mAnchor = anchor;
        return this;
    }

    /**
     * 设置是否需要拦截点击事件
     *
     * @param intercept 是否需要拦截点击事件 true：拦截 false：不拦截
     * @return {@link HighLight} 类对象
     */
    public HighLight setIntercept(boolean intercept) {
        this.intercept = intercept;
        return this;
    }

    /**
     * 设置是否需要模糊化边框，默认不需要
     *
     * @param shadow 是否需要模糊边框 true：需要 false：不需要
     * @return {@link HighLight} 类对象
     */
    public HighLight setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * 设置背景颜色，默认 99000000
     *
     * @param maskColor 背景颜色(遮挡颜色)
     * @return {@link HighLight} 类对象
     */
    public HighLight setMaskColor(int maskColor) {
        this.maskColor = maskColor;
        return this;
    }

    /**
     * 设置边框类型，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param borderLineType 边框类型 {@link BorderLineType}
     * @return {@link HighLight} 类对象
     */
    public HighLight setBroderLineType(@NonNull BorderLineType borderLineType) {
        this.borderLineType = borderLineType;
        return this;
    }

    /**
     * 设置边框宽度，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，该方法才能生效；
     * 不需要转换单位，默认dp
     *
     * @param borderWidth 边框宽度
     * @return {@link HighLight} 类对象
     */
    public HighLight setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        return this;
    }

    /**
     * 设置虚线边框的样式，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}
     * 并且边框类型为 {@link HighLight.BorderLineType#DASH_LINE}，该方法才能生效；不需要转换单位，默认dp
     * <p/>
     * 必须是偶数长度,且>=2,指定了多少长度的实线之后再画多少长度的空白.
     * 如在 new float[] { 1, 2, 4, 8}中,表示先绘制长度1的实线,再绘制长度2的空白,再绘制长度4的实线,再绘制长度8的空白,依次重复
     *
     * @param intervals 虚线数组对象
     * @return {@link HighLight} 类对象
     */
    public HighLight setIntervals(@NonNull float[] intervals) {
        int length = intervals.length;
        if ((length >= 2) && (length % 2 == 0)) {
            this.intervals = intervals;
        } else {
            throw new IllegalArgumentException("元素的个数必须大于2并且是偶数");
        }
        return this;
    }

    /**
     * 设置圆角度数
     *
     * @param radius 圆角度数
     * @return {@link HighLight} 类对象
     */
    public HighLight setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * 设置模糊边界的宽度，需要调用 {@link #setShadow(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param blurSize 模糊边界宽度
     * @return {@link HighLight} 类对象
     */
    public HighLight setBlurWidth(int blurSize) {
        this.blurSize = blurSize;
        return this;
    }

    /**
     * 设置是否需要边框
     *
     * @param isNeedBorder 是否需要边框  true：需要 false：不需要
     * @return {@link HighLight} 类对象
     */
    public HighLight setIsNeedBorder(boolean isNeedBorder) {
        this.isNeedBorder = isNeedBorder;
        return this;
    }

    /**
     * 设置边框颜色，需要调用 {@link #setIsNeedBorder(boolean)} 方法设置为 {@code true}，该方法才能生效
     *
     * @param borderColor 边框颜色
     * @return {@link HighLight} 类对象
     */
    public HighLight setBorderColor(@ColorRes int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * 增加高亮的布局
     *
     * @param viewId        需要高亮的控件id
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLight} 类对象
     */
    public HighLight addHighLight(int viewId, @LayoutRes int decorLayoutId,
                                  @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) mAnchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, onPosCallback);
        return this;
    }

    /**
     * 增加高亮布局
     *
     * @param viewId        需要高亮的控件id
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @param shape         指定高亮的形状，枚举类型
     * @return {@link HighLight} 类对象
     */
    public HighLight addHighLight(int viewId, @LayoutRes int decorLayoutId,
                                  @NonNull OnPosCallback onPosCallback, @NonNull HightLightShape shape) {
        ViewGroup parent = (ViewGroup) mAnchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, onPosCallback, shape);
        return this;
    }

    /**
     * 增加高亮布局
     *
     * @param view          高亮布局的视图
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @param shape         指定高亮的形状，枚举类型
     * @return {@link HighLight} 类对象
     */
    public HighLight addHighLight(View view, @LayoutRes int decorLayoutId,
                                  @NonNull OnPosCallback onPosCallback, @NonNull HightLightShape shape) {
        ViewGroup parent = (ViewGroup) mAnchor;
        RectF rect = new RectF(viewUtils.getLocationInView(parent, view));
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
        viewPosInfo.hightLightShape = shape;
        viewPosInfo.onPosCallback = onPosCallback;
        mViewRects.add(viewPosInfo);

        return this;
    }

    /**
     * @param view          需要高亮的View
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLight} 类对象
     */
    public HighLight addHighLight(View view, @LayoutRes int decorLayoutId, @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) mAnchor;
        RectF rect = new RectF(viewUtils.getLocationInView(parent, view));
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
        mViewRects.add(viewPosInfo);

        return this;
    }

    /**
     * 增加高亮布局
     *
     * @param rect          高亮布局的位置
     * @param decorLayoutId 布局文件资源id
     * @param onPosCallback 回调，用于设置位置
     * @return {@link HighLight} 类对象
     */
    public HighLight addHighLight(RectF rect, @LayoutRes int decorLayoutId, @NonNull OnPosCallback onPosCallback) {
        ViewGroup parent = (ViewGroup) mAnchor;
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
        mViewRects.add(viewPosInfo);

        return this;
    }

    /**
     * 更新位置信息
     */
    public void updateInfo() {
        ViewGroup parent = (ViewGroup) mAnchor;
        for (HighLight.ViewPosInfo viewPosInfo : mViewRects) {
            viewPosInfo.onPosCallback.getPos(parent.getWidth() - viewPosInfo.rectF.right, parent.getHeight() - viewPosInfo.rectF.bottom, viewPosInfo.rectF, viewPosInfo.marginInfo);
        }
    }

    /**
     * 显示含有高亮区域的页面
     */
    public void show() {
        if (mHightLightView != null) return;

        HightLightView hightLightView = new HightLightView(mContext, this, maskColor, mViewRects);
        // 设置是否需要模糊边界和模糊边界的大小
        hightLightView.setIsBlur(this.shadow);
        if (this.shadow) hightLightView.setBlurWidth(this.blurSize);

        // 设置边框的相关配置
        hightLightView.setIsNeedBorder(this.isNeedBorder);
        if (this.isNeedBorder) {
            hightLightView.setBorderColor(this.borderColor);
            hightLightView.setBorderWidth(this.borderWidth);
            hightLightView.setBorderLineType(this.borderLineType);
            if (this.borderLineType == BorderLineType.DASH_LINE)// 是虚线才需要设置虚线样式
                hightLightView.setIntervals(this.intervals);
        }
        hightLightView.setRadius(this.radius);
        hightLightView.setMaskColor(maskColor);

        if (mAnchor.getClass().getSimpleName().equals("FrameLayout")) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) mAnchor).addView(hightLightView, ((ViewGroup) mAnchor).getChildCount(), lp);

        } else {
            FrameLayout frameLayout = new FrameLayout(mContext);
            ViewGroup parent = (ViewGroup) mAnchor.getParent();
            parent.removeView(mAnchor);
            parent.addView(frameLayout, mAnchor.getLayoutParams());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mAnchor, lp);

            frameLayout.addView(hightLightView);
        }

        if (intercept) {
            hightLightView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove();
                    if (clickCallback != null) {
                        clickCallback.onClick();
                    }
                }
            });
        }

        mHightLightView = hightLightView;
    }

    /**
     * 移除含有高亮区域的页面
     */
    public void remove() {
        if (mHightLightView == null) return;
        ViewGroup parent = (ViewGroup) mHightLightView.getParent();
        if (parent instanceof RelativeLayout || parent instanceof FrameLayout) {
            parent.removeView(mHightLightView);
        } else {
            parent.removeView(mHightLightView);
            View origin = parent.getChildAt(0);
            ViewGroup graParent = (ViewGroup) parent.getParent();
            graParent.removeView(parent);
            graParent.addView(origin, parent.getLayoutParams());
        }
        mHightLightView = null;
    }

    /**
     * 将一个布局文件加到根布局上，默认点击移除视图
     *
     * @param layoutId 布局文件资源id
     * @return {@link HighLight} 类对象
     */
    public HighLight addLayout(@LayoutRes int layoutId) {
        viewUtils.addView(layoutId);

        viewUtils.setOnViewClickListener(new ViewUtils.OnViewClickListener() {
            @Override
            public void onClick(View view) {
                if (intercept && (HighLight.this.clickCallback != null)) {
                    HighLight.this.clickCallback.onClick();
                }
            }
        });
        return this;
    }
}
