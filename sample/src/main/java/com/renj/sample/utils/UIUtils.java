package com.renj.sample.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.renj.sample.MyApplication;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * <p>
 * 创建时间：2018-02-06   11:18
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class UIUtils {
    public static Context getContext() {
        return MyApplication.application;
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isApkInDebug() {
        try {
            ApplicationInfo info = getContext().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }
}
