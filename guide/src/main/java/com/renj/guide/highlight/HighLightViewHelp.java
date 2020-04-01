package com.renj.guide.highlight;

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
 * 描述：操作引导工具帮助类——高亮显示部分控件类型帮助类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class HighLightViewHelp {
    private List<HighLightViewPage> highLightViewPages;

    /**
     * 构造函数
     */
    public HighLightViewHelp() {
        highLightViewPages = new ArrayList<>();
    }

    /**
     * 增加一个高亮的布局。调用 {@link #showHighLightView()} 方法开始显示
     *
     * @param rHighLightPageParams {@link RHighLightPageParams} 对象
     * @param rHighLightViewParams {@link RHighLightViewParams} 对象
     */
    public HighLightViewHelp addHighLightView(final @NonNull RHighLightPageParams rHighLightPageParams,
                                              final @NonNull RHighLightViewParams rHighLightViewParams) {
        checkHighLightPageParams(rHighLightPageParams);
        checkHighLightViewParams(rHighLightViewParams);

        HighLightViewPage highLightViewPage = new HighLightViewPage(rHighLightPageParams);
        highLightViewPage.addHighLight(rHighLightViewParams);
        highLightViewPage.setOnRemoveViewListener(new HighLightViewPage.OnRemoveViewListener() {
            @Override
            public void onRemove(HighLightViewPage highLightViewPage) {
                highLightViewPages.remove(highLightViewPage);
                if (rHighLightPageParams.autoShowNext)
                    showNext();
            }
        });
        highLightViewPages.add(highLightViewPage);
        return this;
    }


    /**
     * 增加一个高亮的布局，一个界面需要有多个地方高亮时调用。调用 {@link #showHighLightView()} 方法开始显示
     *
     * @param rHighLightPageParams   {@link RHighLightPageParams} 对象
     * @param rHighLightBgParamsList {@link RHighLightViewParams} 对象集合
     * @return {@link HighLightViewHelp} 类对象
     */
    public HighLightViewHelp addHighLightView(final @NonNull RHighLightPageParams rHighLightPageParams,
                                              final @NonNull List<RHighLightViewParams> rHighLightBgParamsList) {
        checkHighLightPageParams(rHighLightPageParams);

        if (rHighLightBgParamsList == null) {
            throw new IllegalArgumentException("Params rHighLightBgParamsList is null!");
        }
        if (rHighLightBgParamsList.isEmpty()) return this;

        HighLightViewPage highLightViewPage = new HighLightViewPage(rHighLightPageParams);
        for (RHighLightViewParams rHighLightViewParams : rHighLightBgParamsList) {
            checkHighLightViewParams(rHighLightViewParams);
            highLightViewPage.addHighLight(rHighLightViewParams);
        }
        highLightViewPage.setOnRemoveViewListener(new HighLightViewPage.OnRemoveViewListener() {
            @Override
            public void onRemove(HighLightViewPage highLightViewPage) {
                highLightViewPages.remove(highLightViewPage);
                if (rHighLightPageParams.autoShowNext)
                    showNext();
            }
        });
        highLightViewPages.add(highLightViewPage);
        return this;
    }

    /**
     * 显示下一个高亮布局
     */
    private void showNext() {
        // 如果有没有移除的高亮布局，就先移除
        highLightViewPages.get(0).remove();

        if (highLightViewPages.isEmpty()) return;

        highLightViewPages.get(0).show();
    }

    /**
     * 显示高亮布局，点击之后自动显示下一个高亮视图，如果有上一个高亮没有移除，会自动移除掉
     *
     * @return 当前显示的高亮View
     */
    public void showHighLightView() {
        showNext();
    }

    /**
     * 移除一个高亮View
     */
    public void removeHighLightView() {
        highLightViewPages.get(0).remove();
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
}
