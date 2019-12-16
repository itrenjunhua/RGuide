package com.renj.guide;

import android.support.annotation.NonNull;
import android.view.View;

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

    /**
     * 移除指定的遮罩层，默认清除其他的遮罩层，{@link #removeCoverView(RCoverViewParams, View, boolean)}
     *
     * @param rCoverViewParams 需要移除的 {@link RCoverViewParams} 信息
     * @param coverView        需要移除的遮罩层View
     * @see #removeCoverView(RCoverViewParams, View, boolean)
     * @see #skipAllCoverView()
     */
    public void removeCoverView(@NonNull RCoverViewParams rCoverViewParams, @NonNull View coverView) {
        removeCoverView(rCoverViewParams, coverView, true);
    }

    /**
     * 移除指定的遮罩层，并设置是否需要移除其他的遮罩层。<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果移除，那么该页面就不会在显示遮罩层了，除非再次添加和显示<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 如果不移除并且后面还有，那么可以继续调用 {@link #showCoverView()} 方法显示。<br/><br/>
     * <b>特别注意：当当前页面不在需要显示并且后面还有未显示完的遮罩层时，必须清除其他的遮罩层，<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 方式1：该方法的参数{@code clearOtherCoverView} 值传 {@code true}；<br/>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
     * 方式2：手动调用 {@link #skipAllCoverView()} 方法跳过当前页面后面所有的遮罩层。<br/>
     * 如果不试用上述的方法清除遮罩层，那么后面的遮罩层使用(包括当前页或者其他页面)将会出现问题</b>
     *
     * @param rCoverViewParams    需要移除的 {@link RCoverViewParams} 信息
     * @param coverView           需要移除的遮罩层View
     * @param clearOtherCoverView 是否清除其他的遮罩层
     * @see #removeCoverView(RCoverViewParams, View)
     * @see #skipAllCoverView()
     */
    public void removeCoverView(@NonNull RCoverViewParams rCoverViewParams, @NonNull View coverView, boolean clearOtherCoverView) {
        coverViewHelp.removeCoverView(rCoverViewParams, coverView);
        if (clearOtherCoverView) skipAllCoverView();
    }

    /**
     * 跳过后面所有的遮罩层，移除后面的遮罩层
     *
     * @see #removeCoverView(RCoverViewParams, View)
     * @see #removeCoverView(RCoverViewParams, View, boolean)
     */
    public void skipAllCoverView() {
        coverViewHelp.skipAll();
    }
}
