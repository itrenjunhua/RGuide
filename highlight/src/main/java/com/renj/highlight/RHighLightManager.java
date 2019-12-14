package com.renj.highlight;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

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
public class RHighLightManager {
    private static volatile RHighLightManager instance = new RHighLightManager();
    private static List<HighLightViewHelp> highLightViewHelps = new ArrayList<>();

    /**
     * 构造函数
     */
    private RHighLightManager() {
    }

    public static RHighLightManager getInstance() {
        return instance;
    }

    /**
     * 增加一个高亮的布局。调用 {@link #show()} 方法开始显示
     *
     * @param rHighLightPageParams {@link RHighLightPageParams} 对象
     * @param rHighLightViewParams {@link RHighLightViewParams} 对象
     * @return {@link RHighLightManager} 类对象
     * @see #addHighLightView(RHighLightPageParams, RHighLightViewParams, boolean)
     */
    public RHighLightManager addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
                                              @NonNull RHighLightViewParams rHighLightViewParams) {
        return addHighLightView(rHighLightPageParams, rHighLightViewParams, true);
    }

    /**
     * 增加一个高亮的布局。调用 {@link #show()} 方法开始显示
     *
     * @param rHighLightPageParams {@link RHighLightPageParams} 对象
     * @param rHighLightViewParams {@link RHighLightViewParams} 对象
     * @param autoNextView         有多个高亮View时，点击消失之后是否自动进入下一个高亮View
     * @return {@link RHighLightManager} 类对象
     * @see #addHighLightView(RHighLightPageParams, RHighLightViewParams)
     */
    public RHighLightManager addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
                                              @NonNull RHighLightViewParams rHighLightViewParams,
                                              final boolean autoNextView) {
        checkHighLightPageParams(rHighLightPageParams);
        checkHighLightViewParams(rHighLightViewParams);

        HighLightViewHelp highLightViewHelp = new HighLightViewHelp(rHighLightPageParams);
        highLightViewHelp.addHighLight(rHighLightViewParams);
        highLightViewHelp.setOnRemoveViewListener(new HighLightViewHelp.OnRemoveViewListener() {
            @Override
            public void onRemove(HighLightViewHelp highLightViewHelp) {
                highLightViewHelps.remove(highLightViewHelp);
                if (autoNextView)
                    showNext();
            }
        });
        highLightViewHelps.add(highLightViewHelp);
        return this;
    }

    /**
     * 增加一个高亮的布局，一个界面需要有多个地方高亮时调用。调用 {@link #show()} 方法开始显示
     *
     * @param rHighLightPageParams   {@link RHighLightPageParams} 对象
     * @param rHighLightBgParamsList {@link RHighLightViewParams} 对象集合
     * @return {@link RHighLightManager} 类对象
     * @see #addHighLightView(RHighLightPageParams, List, boolean)
     */
    public RHighLightManager addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
                                              @NonNull List<RHighLightViewParams> rHighLightBgParamsList) {
        return addHighLightView(rHighLightPageParams, rHighLightBgParamsList, true);
    }

    /**
     * 增加一个高亮的布局，一个界面需要有多个地方高亮时调用。调用 {@link #show()} 方法开始显示
     *
     * @param rHighLightPageParams   {@link RHighLightPageParams} 对象
     * @param rHighLightBgParamsList {@link RHighLightViewParams} 对象集合
     * @param autoNextView           有多个高亮View时，点击消失之后是否自动进入下一个高亮View
     * @return {@link RHighLightManager} 类对象
     * @see #addHighLightView(RHighLightPageParams, List)
     */
    public RHighLightManager addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
                                              final @NonNull List<RHighLightViewParams> rHighLightBgParamsList,
                                              final boolean autoNextView) {
        checkHighLightPageParams(rHighLightPageParams);

        if (rHighLightBgParamsList == null) {
            throw new IllegalArgumentException("Params rHighLightBgParamsList is null!");
        }
        if (rHighLightBgParamsList.isEmpty()) return this;

        HighLightViewHelp highLightViewHelp = new HighLightViewHelp(rHighLightPageParams);
        for (RHighLightViewParams rHighLightViewParams : rHighLightBgParamsList) {
            checkHighLightViewParams(rHighLightViewParams);
            highLightViewHelp.addHighLight(rHighLightViewParams);
        }
        highLightViewHelp.setOnRemoveViewListener(new HighLightViewHelp.OnRemoveViewListener() {
            @Override
            public void onRemove(HighLightViewHelp highLightViewHelp) {
                highLightViewHelps.remove(highLightViewHelp);
                if (autoNextView)
                    showNext();
            }
        });
        highLightViewHelps.add(highLightViewHelp);
        return this;
    }

    /**
     * 显示下一个高亮布局
     */
    private void showNext() {
        if (highLightViewHelps.isEmpty()) return;

        highLightViewHelps.get(0).show();
    }

    /**
     * 显示高亮布局，点击之后自动显示下一个高亮视图
     */
    public void show() {
        showNext();
    }

    private void checkHighLightPageParams(RHighLightPageParams rHighLightPageParams) {
        if (rHighLightPageParams == null) {
            throw new IllegalArgumentException("addHighLightView() params rHighLightPageParams is null!");
        }
    }

    private void checkHighLightViewParams(RHighLightViewParams rHighLightViewParams) {
        if (rHighLightViewParams == null) {
            throw new IllegalArgumentException("addHighLightView() params rHighLightViewParams is null!");
        }
        if (rHighLightViewParams.onPosCallback == null) {
            throw new IllegalArgumentException("Couldn't find the OnPosCallback." +
                    "Call the RHighLightViewParams#setOnPosCallback(OnPosCallback) method.");
        }

        if (rHighLightViewParams.decorLayoutId == -1) {
            throw new IllegalArgumentException("Params decorLayoutId Exception !");
        }
    }

    /**
     * 将一个布局文件加到根布局上，默认点击移除视图。<b>不需要调用 {@link #show()} 方法</b>
     *
     * @param layoutId 布局文件资源id
     * @return {@link RHighLightManager} 类对象
     */
    public void addLayout(@LayoutRes int layoutId) {

    }
}
