package com.renj.hightlight.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-02    17:24
 * <p/>
 * 描述：操作View的工具类，采用单例设计模式
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class ViewUtils {
    private static final String FRAGMENT_CON = "NoSaveStateFrameLayout";
    private volatile static ViewUtils viewUtils = new ViewUtils();
    private Activity mActivity;

    private OnViewClickListener clickLisstener;

    /**
     * 设置点击监听
     *
     * @param clickLisstener {@link OnViewClickListener} 类
     */
    public void setOnViewClickListener(@NonNull OnViewClickListener clickLisstener) {
        this.clickLisstener = clickLisstener;
    }

    private ViewUtils() {
    }

    /**
     * 获取 {@link ViewUtils} 的实例
     *
     * @return {@link ViewUtils} 的实例
     */
    @org.jetbrains.annotations.Contract(pure = true)
    public static ViewUtils newInstance() {
        return viewUtils;
    }

    /**
     * 初始化Activity
     *
     * @param activity {@link Activity} 对象
     */
    public void initActivity(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    /**
     * @return 返回最顶层视图
     */
    @SuppressWarnings("deprecation")
    public ViewGroup getDeCorView() {
        return (ViewGroup) mActivity.getWindow().getDecorView();
    }

    /**
     * @return 返回内容区域根视图
     */
    private ViewGroup getRootView() {
        return (ViewGroup) mActivity.findViewById(android.R.id.content);
    }

    /**
     * 在整个窗体上面增加一层布局
     *
     * @param layoutId 布局id
     */
    public void addView(@LayoutRes int layoutId) {
        final View view = View.inflate(mActivity, layoutId, null);
        FrameLayout frameLayout = (FrameLayout) getRootView();
        frameLayout.addView(view);

        // 设置整个布局的单击监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(view);
                if (clickLisstener != null) {
                    clickLisstener.onClick(view);
                }
            }
        });
    }

    /**
     * 移除View
     *
     * @param view 需要移出的视图
     */
    private void removeView(@NonNull View view) {
        FrameLayout frameLayout = (FrameLayout) getRootView();
        frameLayout.removeView(view);
    }

    /**
     * 获取子View在父View中的位置
     *
     * @param parent 父View
     * @param child  子View
     * @return Rect对象
     */
    public Rect getLocationInView(View parent, View child) {
        if (child == null || parent == null) {
            throw new IllegalArgumentException(
                    "parent and child can not be null .");
        }

        View decorView = null;
        Context context = child.getContext();
        if (context instanceof Activity) {
            decorView = ((Activity) context).getWindow().getDecorView();
        }

        Rect result = new Rect();
        Rect tmpRect = new Rect();

        View tmp = child;

        if (child == parent) {
            child.getHitRect(result);
            return result;
        }

        while (tmp != decorView && tmp != parent) {
            tmp.getHitRect(tmpRect);
            if (!tmp.getClass().equals(FRAGMENT_CON)) {
                result.left += tmpRect.left;
                result.top += tmpRect.top;
            }
            tmp = (View) tmp.getParent();
        }

        result.right = result.left + child.getMeasuredWidth();
        result.bottom = result.top + child.getMeasuredHeight();
        return result;
    }

    /**
     * 单击视图监听，用于多个引导页面时连续调用
     */
    public interface OnViewClickListener {
        /**
         * 单击监听回调
         *
         * @param view 点击的View
         */
        void onClick(View view);
    }
}
