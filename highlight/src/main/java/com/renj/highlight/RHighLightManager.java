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
     * @param rHighLightBgParams
     * @param autoNextView       有多个高亮View时，点击消失之后是否自动进入下一个高亮View
     * @return {@link RHighLightManager} 类对象
     */
    public RHighLightManager addHighLightView(@NonNull RHighLightBgParams rHighLightBgParams,
                                              @NonNull RHighLightViewParams rHighLightViewParams,
                                              final boolean autoNextView) {
        if (rHighLightBgParams == null) {
            throw new IllegalArgumentException("Params rHighLightBgParams is null!");
        }
        HighLightViewHelp highLightViewHelp = new HighLightViewHelp(rHighLightBgParams);
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
     * @param rHighLightBgParamsList
     * @param autoNextView           有多个高亮View时，点击消失之后是否自动进入下一个高亮View
     * @return {@link RHighLightManager} 类对象
     */
    public RHighLightManager addHighLightView(@NonNull RHighLightBgParams rHighLightBgParams,
                                              final @NonNull List<RHighLightViewParams> rHighLightBgParamsList, final boolean autoNextView) {
        if (rHighLightBgParamsList == null) {
            throw new IllegalArgumentException("Params rHighLightBgParams is null!");
        }
        if (rHighLightBgParamsList.isEmpty()) return this;

        HighLightViewHelp highLightViewHelp = new HighLightViewHelp(rHighLightBgParams);
        for (RHighLightViewParams rHighLightViewParams : rHighLightBgParamsList) {
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

    /**
     * 将一个布局文件加到根布局上，默认点击移除视图。<b>不需要调用 {@link #show()} 方法</b>
     *
     * @param layoutId 布局文件资源id
     * @return {@link RHighLightManager} 类对象
     */
    public void addLayout(@LayoutRes int layoutId) {

    }
}
