package com.kotlin.turbo.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.kotlin.turbo.utils.tray.TrayPreferences;


/**
 * 作者： gassion on 18/10/30 15:43
 * 最可怕的敌人，就是没有坚强的信念。
 */

public class AppPrefs {

    private static final int VERSION = 1;
    private static volatile TrayEMMPrefs mPrefs;
    private static Context mContext;

    /**
     * 继承TrayPreferences以修改模块名
     */
    private static class TrayEMMPrefs extends TrayPreferences {

        public TrayEMMPrefs(Context context) {
            super(context, context.getPackageName(), VERSION);
        }
    }

    private AppPrefs(Context context) {
    }

    private static TrayEMMPrefs getPrefs() {
        if (mPrefs == null) {
            synchronized (AppPrefs.class) {
                if (mPrefs == null) {
                    mPrefs = new TrayEMMPrefs(mContext);
                }
            }
        }

        return mPrefs;
    }

    public static void init(Context context) {
        mContext = context;
        getPrefs();
    }

    /**
     * 设置可被多个进程共享的Boolean值
     */
    public static void putSharedBoolean(String key, boolean value) {
        TrayEMMPrefs prefs = getPrefs();
        prefs.put(key, value);
    }

    /**
     * 设置可被多个进程共享的Int值
     */
    public static void putSharedInt(String key, int value) {
        TrayEMMPrefs prefs = getPrefs();
        prefs.put(key, value);
    }

    /**
     * 设置可被多个进程共享的Long值
     */
    public static void putSharedLong(String key, long value) {
        TrayEMMPrefs prefs = getPrefs();
        prefs.put(key, value);
    }

    /**
     * 设置可被多个进程共享的String值
     */
    public static void putSharedString(String key, String value) {
        TrayEMMPrefs prefs = getPrefs();
        prefs.put(key, value);
    }


    public static void putObject(String key, Object object) {
        String json = new Gson().toJson(object);
        putSharedString(key, json);
    }

    public static <T> T getObject(String key, Class<T> cl) {
        String json = getSharedString(key);
        return new Gson().fromJson(json, cl);
    }

    /**
     * 获取可被多个进程共享的Boolean值,缺省值为false
     */
    public static boolean getSharedBoolean(String key) {
        return getSharedBoolean(key, false);
    }

    /**
     * 获取可被多个进程共享的Boolean值,若key不存在,则返回defaultValue
     */
    public static boolean getSharedBoolean(String key, boolean defaultValue) {
        TrayEMMPrefs prefs = getPrefs();
        return prefs.getBoolean(key, defaultValue);
    }

    /**
     * 获取可被多个进程共享的Int值,若key不存在,则返回0
     */
    public static int getSharedInt(String key) {
        return getSharedInt(key, 0);
    }

    /**
     * 获取可被多个进程共享的Int值,若key不存在,则返回defaultValue
     */
    public static int getSharedInt(String key, int defaultValue) {
        TrayEMMPrefs prefs = getPrefs();
        return prefs.getInt(key, defaultValue);
    }

    /**
     * 获取可被多个进程共享的Long值,若key不存在,则返回0
     */
    public static long getSharedLong(String key) {
        return getSharedLong(key, 0);
    }

    /**
     * 获取可被多个进程共享的Long值,若key不存在,则返回defaultValue
     */
    public static long getSharedLong(String key, long defaultValue) {
        TrayEMMPrefs prefs = getPrefs();
        return prefs.getLong(key, defaultValue);
    }

    /**
     * 获取可被多个进程共享的Int值,若key不存在,则返回null
     */
    public static String getSharedString(String key) {
        return getSharedString(key, null);
    }

    /**
     * 获取可被多个进程共享的Int值,若key不存在,则返回defaultValue
     */
    public static String getSharedString(String key, String defaultValue) {
        TrayEMMPrefs prefs = getPrefs();
        return prefs.getString(key, defaultValue);
    }

    public static void remove(String key) {
        TrayEMMPrefs prefs = getPrefs();
        if (key != null) {
            prefs.remove(key);
        }
    }

    /**
     * 清除配置文件
     */
    public static void clear() {
        TrayEMMPrefs prefs = getPrefs();
        prefs.clear();
    }
}
