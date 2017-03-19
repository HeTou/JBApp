package com.programmer.jbapp.common.widget.flingswipe;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by dionysis_lorentzos on 5/8/14
 * for package com.lorentzos.swipecards
 * and project Swipe cards.
 * Use with caution dinausaurs might appear!
 */
public class FlingCardListener implements View.OnTouchListener {

    private final float objectX;        //控件到父控件的x轴距离
    private final float objectY;        //控件到父控件的Y轴距离
    private final int objectH;          //控件的高度
    private final int objectW;          //控件的宽度
    private final int parentWidth;      //frame的父控件的宽度
    private final FlingListener mFlingListener;
    private final Object dataObject;
    private final float halfWidth;      //frame的宽度的一半
    private float BASE_ROTATION_DEGREES;        //旋转的角度，attr可控制

    private float aPosX;        //控件到父控件的x轴距离
    private float aPosY;        //控件到父控件的Y轴距离
    private float aDownTouchX;  //按下的坐标 X
    private float aDownTouchY;  //按下的坐标 Y
    private static final int INVALID_POINTER_ID = -1;

    // The active pointer is the one currently moving our object.
    private int mActivePointerId = INVALID_POINTER_ID;  //第一次触控时，点的标识id
    private View frame = null;

    private final int TOUCH_ABOVE = 0;      //上倾斜
    private final int TOUCH_BELOW = 1;      //下倾斜
    private int touchPosition;              //frame 滑动的倾斜方式
    // private final Object obj = new_cover Object();
    private boolean isAnimationRunning = false;
    private float MAX_COS = (float) Math.cos(Math.toRadians(45));
    // 支持左右滑
    private boolean isNeedSwipe = true;

    private float aTouchUpX;

    private int animDuration = 200;   //frame 返回原位的动画时间
    private int animOutDuration = 200;  //移除的动画时间
    private float scale;

    /**
     * every time we touch down,we should stop the {@link #animRun}
     */
    private boolean resetAnimCanceled = false;

    public boolean isLeftSwipeDel = false;  //ps 是否左滑删除
    public boolean isRightSwipeDel = true; //ps 是否右滑删除


    public FlingCardListener(View frame, Object itemAtPosition, float rotation_degrees, FlingListener flingListener) {
        super();
        this.frame = frame;
        this.objectX = frame.getX();
        this.objectY = frame.getY();
        this.objectW = frame.getWidth();
        this.objectH = frame.getHeight();
        this.halfWidth = objectW / 2f;
        this.dataObject = itemAtPosition;
        this.parentWidth = ((ViewGroup) frame.getParent()).getWidth();
        this.BASE_ROTATION_DEGREES = rotation_degrees;
        this.mFlingListener = flingListener;
    }

    public void setIsNeedSwipe(boolean isNeedSwipe) {
        this.isNeedSwipe = isNeedSwipe;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        try {
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    // remove the listener because 'onAnimationEnd' will still be called if we cancel the animation.
                    this.frame.animate().setListener(null);
                    this.frame.animate().cancel();

                    resetAnimCanceled = true;

                    // Save the ID of this pointer
                    mActivePointerId = event.getPointerId(0);   //返回一个点的标识（如果多点触控）
                    final float x = event.getX(mActivePointerId);
                    final float y = event.getY(mActivePointerId);

                    // Remember where we started
                    aDownTouchX = x;
                    aDownTouchY = y;
                    // to prevent an initial jump of the magnifier, aposX and aPosY must
                    // have the values from the magnifier frame
                    aPosX = frame.getX();
                    aPosY = frame.getY();

                    if (y < objectH / 2) {
                        touchPosition = TOUCH_ABOVE;
                    } else {
                        touchPosition = TOUCH_BELOW;
                    }
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:   //多点触控
//                    Log.d("FlingCardListener", "多点触控");
                    break;

                case MotionEvent.ACTION_POINTER_UP:
                    // Extract the index of the pointer that left the touch sensor
                    final int pointerIndex = (event.getAction() &
                            MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    final int pointerId = event.getPointerId(pointerIndex);
                    if (pointerId == mActivePointerId) {
                        // This was our active pointer going up. Choose a new_cover
                        // active pointer and adjust accordingly.
                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                        mActivePointerId = event.getPointerId(newPointerIndex);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:

                    // Find the index of the active pointer and fetch its position
                    final int pointerIndexMove = event.findPointerIndex(mActivePointerId);
                    final float xMove = event.getX(pointerIndexMove);
                    final float yMove = event.getY(pointerIndexMove);

                    // from http://android-developers.blogspot.com/2010/06/making-sense-of-multitouch.html
                    // Calculate the distance moved
                    final float dx = xMove - aDownTouchX;       //移动的x距离
                    final float dy = yMove - aDownTouchY;       //移动的y距离

                    // Move the frame（frame移动）
                    aPosX += dx;
                    aPosY += dy;

                    // calculate the rotation degrees
                    float distObjectX = aPosX - objectX;    //x偏移量
                    float rotation = BASE_ROTATION_DEGREES * 2f * distObjectX / parentWidth;
                    if (touchPosition == TOUCH_BELOW) {
                        rotation = -rotation;
                    }

                    // in this area would be code for doing something with the view as the frame moves.
                    if (isNeedSwipe) {
                        frame.setX(aPosX);
                        frame.setY(aPosY);
                        frame.setAlpha(1 - getXScrollProgress());
//                        frame.setRotation(rotation);
                        mFlingListener.onScroll(getScrollProgress(), getScrollXProgressPercent());
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    //mActivePointerId = INVALID_POINTER_ID;
                    int pointerCount = event.getPointerCount();
                    int activePointerId = Math.min(mActivePointerId, pointerCount - 1);
                    aTouchUpX = event.getX(activePointerId);
                    mActivePointerId = INVALID_POINTER_ID;
                    resetCardViewOnStack(event);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    /***
     * 0.0f~ 1.0f   //偏移比例
     *
     * @return
     */
    private float getScrollProgress() {
        float dx = aPosX - objectX;
        float dy = aPosY - objectY;
        float dis = Math.abs(dx) + Math.abs(dy);
        return Math.min(dis, 400f) / 400f;
    }

    private float getXScrollProgress() {
        float dx = aPosX - objectX;
        dx = Math.abs(dx);
        return Math.min(dx, parentWidth) / parentWidth;
    }

    /***
     * 移动后的结果
     *
     * @return -1 左移除; 1 右移除
     */
    private float getScrollXProgressPercent() {
        if (movedBeyondLeftBorder()) {      //左移除
            return -1f;
        } else if (movedBeyondRightBorder()) {  //右移除
            return 1f;
        } else {
            float zeroToOneValue = (aPosX + halfWidth - leftBorder()) / (rightBorder() - leftBorder());
            return zeroToOneValue * 2f - 1f;
        }
    }

    private boolean resetCardViewOnStack(MotionEvent event) {
        if (isNeedSwipe) {
            //动画时间

            if (movedBeyondLeftBorder() && isLeftSwipeDel) {        //左移删除,(ps 可以让该条件不成立禁止左滑删除，{右滑同理})
                // Left Swipe
                onSelected(true, getExitPoint(-objectW), animOutDuration);
                mFlingListener.onScroll(1f, -1.0f);
            } else if (movedBeyondRightBorder() && isRightSwipeDel) {     //ps 右滑删除的条件
                // Right Swipe
                onSelected(false, getExitPoint(parentWidth), animOutDuration);
                mFlingListener.onScroll(1f, 1.0f);
            } else {
                float absMoveXDistance = Math.abs(aPosX - objectX);
                float absMoveYDistance = Math.abs(aPosY - objectY);
                if (absMoveXDistance < 4 && absMoveYDistance < 4) {
                    mFlingListener.onClick(event, frame, dataObject);
                } else {
                    frame.animate()
                            .setDuration(animDuration)
                            .setInterpolator(new OvershootInterpolator(1.5f))
                            .x(objectX)
                            .y(objectY)
                            .alpha(1)
                            .rotation(0)
                            .start();
                    scale = getScrollProgress();
                    this.frame.postDelayed(animRun, 0);
                    resetAnimCanceled = false;
                }
                aPosX = 0;
                aPosY = 0;
                aDownTouchX = 0;
                aDownTouchY = 0;
            }
        } else {
            float distanceX = Math.abs(aTouchUpX - aDownTouchX);
            if (distanceX < 4)
                mFlingListener.onClick(event, frame, dataObject);
        }
        return false;
    }

    private Runnable animRun = new Runnable() {
        @Override
        public void run() {
            mFlingListener.onScroll(scale, 0);
            if (scale > 0 && !resetAnimCanceled) {
                scale = scale - 0.1f;
                if (scale < 0)
                    scale = 0;
                frame.postDelayed(this, animDuration / 20);
            }
        }
    };

    private Runnable animOutRun = new Runnable() {
        @Override
        public void run() {
            mFlingListener.onScroll(scale, 0);
            Log.d("FlingCardListener", "scale:" + scale);
            if (scale < 1 && !resetAnimCanceled) {
                scale = scale + 0.1f;
                if (scale >1)
                    scale = 1;
                frame.postDelayed(this, animOutDuration / 10);
            }
        }
    };


    private boolean movedBeyondLeftBorder() {
        return aPosX + halfWidth < leftBorder();
    }

    private boolean movedBeyondRightBorder() {
        return aPosX + halfWidth > rightBorder();
    }


    public float leftBorder() {
        return parentWidth * 1 / 4f;
    }

    public float rightBorder() {
        return parentWidth * 3 / 4f;
    }


    /***
     * 删除动画
     *
     * @param isLeft   是否是左移
     * @param exitY
     * @param duration 动画时间
     */
    public void onSelected(final boolean isLeft, float exitY, long duration) {
        isAnimationRunning = true;
        float exitX;
        if (isLeft) {
            exitX = -objectW - getRotationWidthOffset();
        } else {
            exitX = parentWidth + getRotationWidthOffset();
        }

        this.frame.animate()
                .setDuration(duration)
                .setInterpolator(new LinearInterpolator())
                .translationX(exitX)
                .translationY(exitY)
                .alpha(0)
                //.rotation(isLeft ? -BASE_ROTATION_DEGREES:BASE_ROTATION_DEGREES)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (isLeft) {
                            mFlingListener.onCardExited();
                            mFlingListener.leftExit(dataObject);
                        } else {
                            mFlingListener.onCardExited();
                            mFlingListener.rightExit(dataObject);
                        }
                        isAnimationRunning = false;
                    }
                }).start();
    }

    /**
     * Starts a default left exit animation.
     */
    public void selectLeft() {
        if (!isAnimationRunning)
            selectLeft(animDuration);
    }

    /**
     * Starts a default left exit animation.
     */
    public void selectLeft(long duration) {
        if (!isAnimationRunning)
            onSelected(true, objectY, duration);
    }

    /**
     * Starts a default right exit animation.
     */
    public void selectRight() {
        if (!isAnimationRunning) {
            selectRight(animDuration);
//            scale = getScrollProgress();
//            this.frame.postDelayed(animOutRun, 0);
//            resetAnimCanceled = false;
        }
    }

    /**
     * Starts a default right exit animation.
     */
    public void selectRight(long duration) {
        if (!isAnimationRunning) {
            onSelected(false, objectY, duration);
        }
    }

    private float getExitPoint(int exitXPoint) {
        float[] x = new float[2];
        x[0] = objectX;     //frame原本的x坐标
        x[1] = aPosX;       //frame现在的x坐标

        float[] y = new float[2];
        y[0] = objectY;     //frame原本的y坐标
        y[1] = aPosY;       //frame现在的y坐标

        LinearRegression regression = new LinearRegression(x, y);

        //Your typical y = ax+b linear regression
        return (float) regression.slope() * exitXPoint + (float) regression.intercept();
    }

    private float getExitRotation(boolean isLeft) {
        float rotation = BASE_ROTATION_DEGREES * 2f * (parentWidth - objectX) / parentWidth;
        if (touchPosition == TOUCH_BELOW) {
            rotation = -rotation;
        }
        if (isLeft) {
            rotation = -rotation;
        }
        return rotation;
    }

    /**
     * When the object rotates it's width becomes bigger.
     * The maximum width is at 45 degrees.
     * <p>
     * The below method calculates the width offset of the rotation.
     */
    private float getRotationWidthOffset() {
        return objectW / MAX_COS - objectW;
    }


    public void setRotationDegrees(float degrees) {
        this.BASE_ROTATION_DEGREES = degrees;
    }


    protected interface FlingListener {
        void onCardExited();

        void leftExit(Object dataObject);

        void rightExit(Object dataObject);

        void onClick(MotionEvent event, View v, Object dataObject);

        void onScroll(float progress, float scrollXProgress);
    }


}

