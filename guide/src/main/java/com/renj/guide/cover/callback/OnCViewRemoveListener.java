package com.renj.guide.cover.callback;

import com.renj.guide.cover.RCoverViewParams;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-09   23:20
 * <p>
 * 描述：遮罩移除监听
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnCViewRemoveListener {
    /**
     * 遮罩移除监听，如果是手动调用 {@link com.renj.guide.cover.CoverViewHelp#skipAllCoverView()} 方法，不会回调该方法
     *
     * @param hasCoverView       是否还有已添加，但未显示的遮罩
     * @param notShownCoverViews 未显示的遮罩参数信息集合，如果 hasCoverView 为false，集合元素为空
     */
    void onRemoveCoverView(boolean hasCoverView, List<RCoverViewParams> notShownCoverViews);
}
