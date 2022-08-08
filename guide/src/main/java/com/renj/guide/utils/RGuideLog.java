package com.renj.guide.utils;

import android.util.Log;

import com.renj.guide.BuildConfig;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2022-08-08   15:34
 * <p>
 * 描述：日志打印工具
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RGuideLog {
    private static String TAG = "RGuide";
    private static boolean showLog = BuildConfig.DEBUG;

    public static void i(String msg) {
        if (showLog)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (showLog)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (showLog)
            Log.e(TAG, msg);
    }
}
