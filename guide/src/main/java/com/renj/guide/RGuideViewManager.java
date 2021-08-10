package com.renj.guide;

import com.renj.guide.cover.CoverViewHelp;
import com.renj.guide.highlight.HighLightViewHelp;

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

    private RGuideViewManager() {
    }

    /**
     * 创建高亮View样式帮助类
     *
     * @return {@link HighLightViewHelp} 对象
     */
    public static HighLightViewHelp createHighLightViewHelp() {
        return new HighLightViewHelp();
    }

    /**
     * 创建遮罩样式帮助类
     *
     * @return {@link CoverViewHelp} 对象
     */
    public static CoverViewHelp createCoverViewHelp() {
        return new CoverViewHelp();
    }
}
