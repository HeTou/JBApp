package com.programmer.jbapp.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/2/8.
 */

public class DialView extends View {

    private int width;   //控件的宽度

    private int height;     //控件的高度

    public DialView(Context context) {
        super(context);
    }

    public DialView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width/2,height/2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }
}
