package com.smart.ui;

import android.util.Log;

/**
 * @date : 2019-07-04 15:05
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LogUtils {
    private final static int MAX_LENGTH = 3500;

    private static String DEFAULT_TAG = "SMART";
    private static boolean isLog = true;


    public static void setDefaultTag(String defaultTag) {
        DEFAULT_TAG = defaultTag;
    }

    public static void setIsLog(boolean isLog) {
        LogUtils.isLog = isLog;
    }

    /**
     * debug级别日志
     *
     * @param msg 相关内容
     */
    public static void d(String msg) {
        if (!isLog) {
            return;
        }
        d(DEFAULT_TAG, msg);
    }


    /**
     * debug级别日志
     *
     * @param tag tag
     * @param msg 相关内容
     */
    public static void d(String tag, String msg) {
        if (!isLog) {
            return;
        }
        printLong(0, tag, msg);
    }


    /**
     * info级别日志
     *
     * @param msg 相关内容
     */
    public static void i(String msg) {
        if (!isLog) {
            return;
        }
        i(DEFAULT_TAG, msg);
    }


    /**
     * info级别日志
     *
     * @param tag tag
     * @param msg 相关内容
     */
    public static void i(String tag, String msg) {
        if (!isLog) {
            return;
        }
        printLong(1, tag, msg);
    }


    /**
     * warn级别日志
     *
     * @param msg 相关内容
     */
    public static void w(String msg) {
        if (!isLog) {
            return;
        }
        w(DEFAULT_TAG, msg);
    }


    /**
     * warn级别日志
     *
     * @param tag tag
     * @param msg 相关内容
     */
    public static void w(String tag, String msg) {
        if (!isLog) {
            return;
        }
        printLong(2, tag, msg);
    }


    /**
     * error级别日志
     *
     * @param msg 相关内容
     */
    public static void e(String msg) {
        if (!isLog) {
            return;
        }
        e(DEFAULT_TAG, msg);
    }


    /**
     * error级别日志
     *
     * @param tag tag
     * @param msg 相关内容
     */
    public static void e(String tag, String msg) {
        if (!isLog) {
            return;
        }
        printLong(3, tag, msg);
    }


    /**
     * 打印较长的日志
     *
     * @param level d/i/w/e  0/1/2/3
     * @param tag   tag
     * @param msg   msg
     */
    private static void printLong(int level, String tag, String msg) {

        msg = msg.trim();
        int index = 0;
        int maxLength = MAX_LENGTH;
        String sub = null;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }

            index += maxLength;

            switch (level) {
                case 0: //debug
                    Log.d(tag, msg);
                    break;
                case 1: //info
                    Log.i(tag, msg);
                    break;
                case 2: //warn
                    Log.w(tag, msg);
                    break;
                case 3: //error
                    Log.e(tag, msg);
                    break;
                default:
                    break;
            }

        }


    }
}
