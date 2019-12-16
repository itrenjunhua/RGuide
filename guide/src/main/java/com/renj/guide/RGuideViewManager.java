package com.renj.guide;

import android.support.annotation.NonNull;

import com.renj.guide.cover.CoverViewHelp;
import com.renj.guide.cover.RCoverViewParams;
import com.renj.guide.highlight.HighLightViewHelp;
import com.renj.guide.highlight.RHighLightPageParams;
import com.renj.guide.highlight.RHighLightViewParams;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2019-12-16   10:16
 * <p>
 * 描述：操作引导工具类入口
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RGuideViewManager {
    private final static RGuideViewManager instance = new RGuideViewManager();
    private static HighLightViewHelp highLightViewHelp;
    private static CoverViewHelp coverViewHelp;

    private RGuideViewManager() {
        highLightViewHelp = new HighLightViewHelp();
        coverViewHelp = new CoverViewHelp();
    }

    public static RGuideViewManager getInstance() {
        return instance;
    }

    /* ------------------------- 高亮显示部分控件类型 ----------------------------- */

    /**
     * 增加一个高亮的布局。调用 {@link #showHighLightView()} 方法开始显示
     *
     * @param rHighLightPageParams {@link RHighLightPageParams} 对象
     * @param rHighLightViewParams {@link RHighLightViewParams} 对象
     * @return {@link RGuideViewManager} 类对象
     */
    public RGuideViewManager addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
                                              @NonNull RHighLightViewParams rHighLightViewParams) {
        highLightViewHelp.addHighLightView(rHighLightPageParams, rHighLightViewParams);
        return this;
    }

    /**
     * 增加一个高亮的布局，一个界面需要有多个地方高亮时调用。调用 {@link #showHighLightView()} 方法开始显示
     *
     * @param rHighLightPageParams   {@link RHighLightPageParams} 对象
     * @param rHighLightBgParamsList {@link RHighLightViewParams} 对象集合
     * @return {@link RGuideViewManager} 类对象
     * @see #addHighLightView(RHighLightPageParams, List)
     */
    public RGuideViewManager addHighLightView(@NonNull RHighLightPageParams rHighLightPageParams,
                                              @NonNull List<RHighLightViewParams> rHighLightBgParamsList) {
        highLightViewHelp.addHighLightView(rHighLightPageParams, rHighLightBgParamsList);
        return this;
    }

    /**
     * 开始显示高亮布局
     */
    public void showHighLightView() {
        highLightViewHelp.show();
    }

    /* ------------------------- 高亮显示部分控件类型 ----------------------------- */

    /**
     * 在整个窗体上面增加一层布局，默认点击移除视图。<b>需要调用 {@link #showCoverView()} 方法</b>
     *
     * @param rCoverViewParams {@link RCoverViewParams} 对象
     * @return {@link CoverViewHelp} 类对象
     */
    public RGuideViewManager addCoverView(@NonNull RCoverViewParams rCoverViewParams) {
        coverViewHelp.addCoverView(rCoverViewParams);
        return this;
    }

    /**
     * 开始显示覆盖布局
     */
    public void showCoverView() {
        coverViewHelp.show();
    }
}
