package com.programmer.jbapp.common;

import android.app.Activity;
import android.os.Build;
import android.view.WindowManager;

/**
 * 配置手机样式的类
 * 2016/11/20.
 */
public class StyleConfig {
    /**
     * 使用沉浸式状态栏
     * @param activity
     * @param bool      //true 为使用沉浸式， false 为 不使用
     */
    public static void immersion(Activity activity, boolean bool) {
        if (bool) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }
}
