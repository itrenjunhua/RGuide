package com.renj.hightlight.util;

import android.util.Log;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2016-08-02    17:19
 * <p/>
 * 描述：Log日志工具类，打印结果形式 类名.方法名(所在行数)：打印的信息
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class RLog {
    /**
     * Application TAG,use "logcat -s TAG"
     */
    private static String TAG = "RLog";


    private static boolean IS_FULL_CLASSNAME;

    /**
     * log level
     */
    private static int LOG_LEVEL = Log.VERBOSE;

    /**
     * print full class name or not
     *
     * @param isFullClassName
     */
    public static void setFullClassName(boolean isFullClassName) {
        RLog.IS_FULL_CLASSNAME = isFullClassName;
    }

    /**
     * set log level, default Log.VERBOSE
     *
     * @param level
     */
    public static void setLogLevel(int level) {
        RLog.LOG_LEVEL = level;
    }

    /**
     * set application TAG, default "RLog"
     *
     * @param tag
     */
    public static void setAppTAG(String tag) {
        RLog.TAG = tag;
    }


    public static void v(String msg) {
        if (LOG_LEVEL <= Log.VERBOSE) {
            Log.v(TAG, getLogTitle() + msg);
        }
    }


    public static void d(String msg) {
        if (LOG_LEVEL <= Log.DEBUG) {
            Log.d(TAG, getLogTitle() + msg);
        }
    }

    public static void i(String msg) {
        if (LOG_LEVEL <= Log.INFO) {
            Log.i(TAG, getLogTitle() + msg);
        }
    }

    public static void w(String msg) {
        if (LOG_LEVEL <= Log.WARN) {
            Log.w(TAG, getLogTitle() + msg);
        }
    }

    public static void e(String msg) {
        if (LOG_LEVEL <= Log.ERROR) {
            Log.e(TAG, getLogTitle() + msg);
        }
    }

    /**
     * make log title
     *
     * @return
     */
    private static String getLogTitle() {
        StackTraceElement elm = Thread.currentThread().getStackTrace()[4];
        String className = elm.getClassName();
        if (!IS_FULL_CLASSNAME) {
            int dot = className.lastIndexOf('.');
            if (dot != -1) {
                className = className.substring(dot + 1);
            }
        }
        return className + "." + elm.getMethodName() + "(" + elm.getLineNumber() + ")" + ": ";
    }

}
