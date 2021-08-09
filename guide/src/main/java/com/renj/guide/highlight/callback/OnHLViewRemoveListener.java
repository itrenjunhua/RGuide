package com.renj.guide.highlight.callback;

import com.renj.guide.highlight.RHighLightPageParams;
import com.renj.guide.highlight.RHighLightViewParams;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2021-08-10   1:55
 * <p>
 * 描述：高亮装饰移除监听
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public interface OnHLViewRemoveListener {
    /**
     * 高亮移除监听，如果是手动调用 {@link com.renj.guide.highlight.HighLightViewHelp#skipAllHighLightView()} 方法，不会回调该方法
     *
     * @param hasHighLight           是否还有已添加，但未显示的高亮
     * @param notShownHighLightViews 未显示的高亮信息集合，如果 hasHighLight 为false，集合元素为空
     */
    void onRemoveHighLightView(boolean hasHighLight, List<HighLightPageInfo> notShownHighLightViews);


    class HighLightPageInfo {
        public RHighLightPageParams rHighLightPageParams;
        public List<RHighLightViewParams> highLightViewParams;

        public HighLightPageInfo(RHighLightPageParams rHighLightPageParams, List<RHighLightViewParams> highLightViewParams) {
            this.rHighLightPageParams = rHighLightPageParams;
            this.highLightViewParams = highLightViewParams;
        }
    }
}
