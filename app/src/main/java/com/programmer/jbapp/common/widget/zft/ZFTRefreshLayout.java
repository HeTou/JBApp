package com.programmer.jbapp.common.widget.zft;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * zft
 * 2017/3/28.
 */

public class ZFTRefreshLayout extends ViewGroup {


    private static String TAG = "MultiViewGroup";
    private final Context mContext;

    private int curScreen = 0;  //当前屏幕
    private Scroller mScroller = null; //Scroller对象实例
    private float mLastionMotionY;

    public ZFTRefreshLayout(Context context) {
        this(context, null);
    }

    public ZFTRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mScroller = new Scroller(context);
        init();
    }

    //初始化
    private void init() {
        //初始化Scroller实例
        mScroller = new Scroller(mContext);
        //初始化3个 LinearLayout控件
        //初始化一个最小滑动距离
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    // 由父视图调用用来请求子视图根据偏移值 mScrollX,mScrollY重新绘制
    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        Log.e(TAG, "computeScroll");
        // 如果返回true，表示动画还没有结束
        // 因为前面startScroll，所以只有在startScroll完成时 才会为false
        if (mScroller.computeScrollOffset()) {
            Log.e(TAG, mScroller.getCurrX() + "======" + mScroller.getCurrY());
            // 产生了动画效果，根据当前值 每次滚动一点
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            Log.e(TAG, "### getleft is " + getLeft() + " ### getRight is " + getRight());
            //此时同样也需要刷新View ，否则效果可能有误差
            postInvalidate();
        } else
            Log.i(TAG, "have done the scoller -----");
    }

    //两种状态: 是否处于滑屏状态
    private static final int TOUCH_STATE_REST = 0;  //什么都没做的状态
    private static final int TOUCH_STATE_SCROLLING = 1;  //开始滑屏的状态
    private int mTouchState = TOUCH_STATE_REST; //默认是什么都没做的状态
    //--------------------------
    //处理触摸事件 ~
    public static int SNAP_VELOCITY = 600;  //最小的滑动速率
    private int mTouchSlop = 0;              //最小滑动距离，超过了，才认为开始滑动
    private float mLastionMotionX = 0;       //记住上次触摸屏的位置
    //处理触摸的速率
    private VelocityTracker mVelocityTracker = null;

    // 这个感觉没什么作用 不管true还是false 都是会执行onTouchEvent的 因为子view里面onTouchEvent返回false了
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        Log.e(TAG, "onInterceptTouchEvent-slop:" + mTouchSlop);

        final int action = ev.getAction();
        //表示已经开始滑动了，不需要走该Action_MOVE方法了(第一次时可能调用)。
        //该方法主要用于用户快速松开手指，又快速按下的行为。此时认为是出于滑屏状态的。
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST)) {
            return true;
        }

        final float x = ev.getX();
        final float y = ev.getY();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onInterceptTouchEvent down");
                mLastionMotionX = x;
                mLastionMotionY = y;
                Log.e(TAG, mScroller.isFinished() + "");
                mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;

                break;

            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "onInterceptTouchEvent move");
                final int xDiff = (int) Math.abs(mLastionMotionX - x);
                //超过了最小滑动距离，就可以认为开始滑动了
                if (xDiff > mTouchSlop) {
                    mTouchState = TOUCH_STATE_SCROLLING;
                }
                break;


            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "onInterceptTouchEvent up or cancel");
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        Log.e(TAG, mTouchState + "====" + TOUCH_STATE_REST);
        return mTouchState != TOUCH_STATE_REST;
    }


    public boolean onTouchEvent(MotionEvent event) {

        super.onTouchEvent(event);

        Log.i(TAG, "--- onTouchEvent--> ");

        // TODO Auto-generated method stub
        Log.e(TAG, "onTouchEvent start");
        //获得VelocityTracker对象，并且添加滑动对象
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        //触摸点
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //如果屏幕的动画还没结束，你就按下了，我们就结束上一次动画，即开始这次新ACTION_DOWN的动画
                if (mScroller != null) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                }
                mLastionMotionX = x; //记住开始落下的屏幕点
                break;
            case MotionEvent.ACTION_MOVE:
                int detaX = (int) (mLastionMotionX - x); //每次滑动屏幕，屏幕应该移动的距离
                scrollBy(detaX, 0);//开始缓慢滑屏咯。 detaX > 0 向右滑动 ， detaX < 0 向左滑动 ，

                Log.e(TAG, "--- MotionEvent.ACTION_MOVE--> detaX is " + detaX);
                mLastionMotionX = x;
                break;
            case MotionEvent.ACTION_UP:

                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000);
                //计算速率
                int velocityX = (int) velocityTracker.getXVelocity();
                Log.e(TAG, "---velocityX---" + velocityX);

                //滑动速率达到了一个标准(快速向右滑屏，返回上一个屏幕) 马上进行切屏处理
                if (velocityX > SNAP_VELOCITY && curScreen > 0) {
                    // Fling enough to move left
                    Log.e(TAG, "snap left");
                    snapToScreen(curScreen - 1);
                }
                //快速向左滑屏，返回下一个屏幕)
                else if (velocityX < -SNAP_VELOCITY && curScreen < (getChildCount() - 1)) {
                    Log.e(TAG, "snap right");
                    snapToScreen(curScreen + 1);
                }
                //以上为快速移动的 ，强制切换屏幕
                else {
                    //我们是缓慢移动的，因此先判断是保留在本屏幕还是到下一屏幕
                    snapToDestination();
                }
                //回收VelocityTracker对象
                if (mVelocityTracker != null) {
                    mVelocityTracker.recycle();
                    mVelocityTracker = null;
                }
                //修正mTouchState值
                mTouchState = TOUCH_STATE_REST;

                break;
            case MotionEvent.ACTION_CANCEL:
                mTouchState = TOUCH_STATE_REST;
                break;
        }

        return true;
    }

    ////我们是缓慢移动的，因此需要根据偏移值判断目标屏是哪个？
    private void snapToDestination() {
        //当前的偏移位置
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        Log.e(TAG, "### onTouchEvent snapToDestination ### scrollX is " + scrollX);
        //判断是否超过下一屏的中间位置，如果达到就抵达下一屏，否则保持在原屏幕
        //直接使用这个公式判断是哪一个屏幕 前后或者自己
        //判断是否超过下一屏的中间位置，如果达到就抵达下一屏，否则保持在原屏幕
        // 这样的一个简单公式意思是：假设当前滑屏偏移值即 scrollCurX 加上每个屏幕一半的宽度，除以每个屏幕的宽度就是
        //  我们目标屏所在位置了。 假如每个屏幕宽度为320dip, 我们滑到了500dip处，很显然我们应该到达第二屏
        int destScreen = (getScrollX() + getContext().getResources().getDisplayMetrics().widthPixels / 2) / getContext().getResources().getDisplayMetrics().heightPixels;

        Log.e(TAG, "### onTouchEvent  ACTION_UP### dx destScreen " + destScreen);

        snapToScreen(destScreen);
    }

    //真正的实现跳转屏幕的方法
    private void snapToScreen(int whichScreen) {
        //简单的移到目标屏幕，可能是当前屏或者下一屏幕
        //直接跳转过去，不太友好
        //scrollTo(mLastScreen * MultiScreenActivity.screenWidth, 0);
        //为了友好性，我们在增加一个动画效果
        //需要再次滑动的距离 屏或者下一屏幕的继续滑动距离

        curScreen = whichScreen;
        //防止屏幕越界，即超过屏幕数
        if (curScreen > getChildCount() - 1)
            curScreen = getChildCount() - 1;
        //为了达到下一屏幕或者当前屏幕，我们需要继续滑动的距离.根据dx值，可能想左滑动，也可能像又滑动
        int dx = curScreen * getWidth() - getScrollX();

        Log.e(TAG, "### onTouchEvent  ACTION_UP### dx is " + dx);

        mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 2);

        //由于触摸事件不会重新绘制View，所以此时需要手动刷新View 否则没效果
        invalidate();
    }

    //开始滑动至下一屏
    public void startMove() {
    }

    //理解停止滑动
    public void stopMove() {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0,0,child.getMeasuredWidth(),child.getMeasuredHeight());
    }

}
