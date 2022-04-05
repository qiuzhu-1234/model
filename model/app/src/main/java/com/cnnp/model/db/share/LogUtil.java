package com.cnnp.model.db.share;

import android.util.Log;

import com.cnnp.model.BuildConfig;

public class LogUtil {
    private static final String TAG = "===";
    //Log开关
    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String message){
        if (DEBUG) Log.d(TAG, createMessage(message));
    }

    public static void e(String message){
        if (DEBUG) Log.e(TAG, createMessage(message));
    }

    public static void i(String message){
        if (DEBUG) Log.i(TAG, createMessage(message));
    }

    public static void v(String message){
        if (DEBUG) Log.v(TAG, createMessage(message));
    }

    public static void w(String message){
        if (DEBUG) Log.d(TAG, createMessage(message));
    }

    public static void println(String message){
        if (DEBUG) Log.println(Log.INFO, TAG, message);
    }
    /**
     * 获取有类名与方法名的logString
     */

    private static String createMessage(String rawMessage){
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        String fullClassName = stackTraceElement.getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        return className + "." +stackTraceElement.getMethodName() + "(): " + rawMessage;
    }
}
