package com.programmer.jbapp.module.view.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * zft
 * 2017/4/14.
 */

public class CustomDrawable extends Drawable {

    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    /***
     * 设置透明度
     * @param alpha
     */
    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    /****
     * 设置颜色过滤器
     * @param colorFilter
     */
    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    /****
     * 设置图像的级别，如PixelFormat.OPAQUE ,不透明
     * @return
     */
    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    /***
     * drawable的宽度
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    /***
     * drawable的高度
     * @return
     */
    @Override
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }
}
