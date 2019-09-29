package com.kotlin.turbo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * 文件名：StatusBarUtil
 * 作者：Turbo
 * 时间：2019-09-26 11:31
 * 开心对待每一天，真心对待每一个
 */
public class StatusBarUtil {


    /**
     * 设置透明状态栏
     *
     * @param activity 目标界面
     */
    public static void setTransparentForWindow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置纯色状态栏（自定义颜色，alpha）
     *
     * @param activity 目标activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度
     */
    public static void setColor(@NonNull Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(cipherColor(color, alpha));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentView((ViewGroup) activity.getWindow().getDecorView(), color, alpha);
            setRootView(activity, true);
        }
    }

    /**
     * 计算alpha色值
     *
     * @param color 状态栏颜色值
     * @param alpha 状态栏透明度
     */
    private static int cipherColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 创建透明View
     *
     * @param viewGroup 目标视图
     * @param color     状态栏颜色值
     * @param alpha     状态栏透明度
     */
    private static void setTranslucentView(ViewGroup viewGroup, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int cipherColor = cipherColor(color, alpha);
            View translucentView = viewGroup.findViewById(android.R.id.custom);
            if (translucentView == null && cipherColor != 0) {
                translucentView = new View(viewGroup.getContext());
                translucentView.setId(android.R.id.custom);
                ViewGroup.LayoutParams params =
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(viewGroup.getContext()));
                viewGroup.addView(translucentView, params);
            }
            if (translucentView != null) {
                translucentView.setBackgroundColor(cipherColor);
            }
        }

    }

    /**
     * 设置根布局参数
     *
     * @param activity         目标activity
     * @param fitSystemWindows 是否预留toolbar的高度
     */
    private static void setRootView(Activity activity, boolean fitSystemWindows) {
        ViewGroup parent = activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(fitSystemWindows);
                ((ViewGroup) childView).setClipToPadding(fitSystemWindows);
            }
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context 目标Context
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

}
