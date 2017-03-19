package com.programmer.jbapp.common.widget.flingswipe;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.programmer.jbapp.R;

import java.util.ArrayList;


/**
 * Created by dionysis_lorentzos on 5/8/14
 * for package com.lorentzos.swipecards
 * and project Swipe cards.
 * Use with caution dinosaurs might appear!
 */

public class SwipeFlingAdapterView extends BaseFlingAdapterView {

    private ArrayList<View> cacheItems = new ArrayList<>();

    //缩放层叠效果
    private int yOffsetStep; // view叠加垂直偏移量的步长
    private static final float SCALE_STEP = 0.08f; // view叠加缩放的步长
    //缩放层叠效果

    private int MAX_VISIBLE = 4; // 值建议最小为4
    private int MIN_ADAPTER_STACK = 6;
    private float ROTATION_DEGREES = 2f;    //旋转的角度
    private int LAST_OBJECT_IN_STACK = 0;

    private Adapter mAdapter;
    private onFlingListener mFlingListener;
    private AdapterDataSetObserver mDataSetObserver;
    private boolean mInLayout = false;
    private View mActiveCard = null;
    private OnItemClickListener mOnItemClickListener;       //点击事件
    private FlingCardListener flingCardListener;            //触摸事件

    // 支持左右滑
    public boolean isNeedSwipe = true;

    public boolean isUp = false;    //是否是向上倾斜;      //ps zft 添加倾斜方向

    private int initTop;
    private int initLeft;

    public SwipeFlingAdapterView(Context context) {
        this(context, null);
    }

    public SwipeFlingAdapterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeFlingAdapterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeFlingAdapterView, defStyle, 0);
        MAX_VISIBLE = a.getInt(R.styleable.SwipeFlingAdapterView_max_visible, MAX_VISIBLE);
        MIN_ADAPTER_STACK = a.getInt(R.styleable.SwipeFlingAdapterView_min_adapter_stack, MIN_ADAPTER_STACK);
        ROTATION_DEGREES = a.getFloat(R.styleable.SwipeFlingAdapterView_rotation_degrees, ROTATION_DEGREES);
        yOffsetStep = a.getDimensionPixelOffset(R.styleable.SwipeFlingAdapterView_y_offset_step, 0);
        a.recycle();

    }

    public void setIsNeedSwipe(boolean isNeedSwipe) {
        this.isNeedSwipe = isNeedSwipe;
    }

    /**
     * A shortcut method to set both the listeners and the adapter.
     *
     * @param context  The activity context which extends onFlingListener, OnItemClickListener or both
     * @param mAdapter The adapter you have to set.
     */
    public void init(final Context context, Adapter mAdapter) {
        if (context instanceof onFlingListener) {
            mFlingListener = (onFlingListener) context;
        } else {
            throw new RuntimeException("Activity does not implement SwipeFlingAdapterView.onFlingListener");
        }
        if (context instanceof OnItemClickListener) {
            mOnItemClickListener = (OnItemClickListener) context;
        }
        setAdapter(mAdapter);
    }

    @Override
    public View getSelectedView() {
        return mActiveCard;
    }


    @Override
    public void requestLayout() {
        if (!mInLayout) {
            super.requestLayout();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        // if we don't have an adapter, we don't need to do anything
        if (mAdapter == null) {
            return;
        }

        mInLayout = true;
        final int adapterCount = mAdapter.getCount();               //获取view的数量
        if (adapterCount == 0) {
//            removeAllViewsInLayout();
            removeAndAddToCache(0);                                 //如果item数目为0，则移除所有子view
        } else {
            View topCard = getChildAt(LAST_OBJECT_IN_STACK);        //最后的那个view
            if (mActiveCard != null && topCard != null && topCard == mActiveCard) {
//                removeViewsInLayout(0, LAST_OBJECT_IN_STACK);
                removeAndAddToCache(1);
                layoutChildren(1, adapterCount);
            } else {
                // Reset the UI and set top view listener
//                removeAllViewsInLayout();
                removeAndAddToCache(0);
                layoutChildren(0, adapterCount);
                setTopView();
            }
        }
        mInLayout = false;

        if (initTop == 0 && initLeft == 0 && mActiveCard != null) {
            initTop = mActiveCard.getTop();
            initLeft = mActiveCard.getLeft();
        }

        if (adapterCount < MIN_ADAPTER_STACK) {
            if (mFlingListener != null) {
                mFlingListener.onAdapterAboutToEmpty(adapterCount);
            }
        }
    }

    /***
     * 移除view(移除view并不会request和invalidate),添加到缓存列表中去
     *
     * @param remain 不移除的view的数目
     */
    private void removeAndAddToCache(int remain) {
        View view;
        for (int i = 0; i < getChildCount() - remain; ) {
            view = getChildAt(i);
            removeViewInLayout(view);
            cacheItems.add(view);
        }
    }

    /***
     * @param startingIndex
     * @param adapterCount  adapter.getCount()
     */
    private void layoutChildren(int startingIndex, int adapterCount) {
        while (startingIndex < Math.min(adapterCount, MAX_VISIBLE)) {
            View item = null;
            if (cacheItems.size() > 0) {
                item = cacheItems.get(0);
                cacheItems.remove(item);
            }
            View newUnderChild = mAdapter.getView(startingIndex, item, this);
            if (newUnderChild.getVisibility() != GONE) {
                makeAndAddView(newUnderChild, startingIndex);
                LAST_OBJECT_IN_STACK = startingIndex;
            }
            startingIndex++;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    /***
     * 设置宽高大小，margin
     *
     * @param child
     * @param index
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void makeAndAddView(View child, int index) {

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
        addViewInLayout(child, 0, lp, true);

        final boolean needToMeasure = child.isLayoutRequested();
        if (needToMeasure) {
            int childWidthSpec = getChildMeasureSpec(getWidthMeasureSpec(), getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin, lp.width);
            int childHeightSpec = getChildMeasureSpec(getHeightMeasureSpec(), getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin, lp.height);
            child.measure(childWidthSpec, childHeightSpec);
        } else {
            cleanupLayoutState(child);
        }

        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();

        int gravity = lp.gravity;
        if (gravity == -1) {
            gravity = Gravity.TOP | Gravity.START;
        }

        int layoutDirection = 0;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
            layoutDirection = getLayoutDirection();
        final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
        final int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;

        int childLeft;
        int childTop;
        switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
            case Gravity.CENTER_HORIZONTAL:
                childLeft = (getWidth() + getPaddingLeft() - getPaddingRight() - w) / 2 + lp.leftMargin - lp.rightMargin;
                break;
            case Gravity.END:
                childLeft = getWidth() + getPaddingRight() - w - lp.rightMargin;
                break;
            case Gravity.START:
            default:
                childLeft = getPaddingLeft() + lp.leftMargin;
                break;
        }
        switch (verticalGravity) {
            case Gravity.CENTER_VERTICAL:
                childTop = (getHeight() + getPaddingTop() - getPaddingBottom() - h) / 2
                        + lp.topMargin - lp.bottomMargin;
                break;
            case Gravity.BOTTOM:
                childTop = getHeight() - getPaddingBottom() - h - lp.bottomMargin;
                break;
            case Gravity.TOP:
            default:
//                childTop = getPaddingTop() + lp.topMargin;
                childTop = getHeight() - getPaddingBottom() - h - lp.bottomMargin;
                break;
        }
        if(!isUp){
//            childTop+=50;
        }
        child.layout(childLeft, childTop, childLeft + w, childTop + h);
        // 缩放层叠效果
        adjustChildView(child, index);
    }

    private void adjustChildView(View child, int index) {
        if (index > -1 && index < MAX_VISIBLE) {
            int multiple;
            if (index > 2) multiple = 2;    //这里控制可以看的卡片数目
            else multiple = index;
            if (isUp) {
                child.offsetTopAndBottom((yOffsetStep * multiple));
            } else {
                child.offsetTopAndBottom(-1 * (yOffsetStep * multiple));
            }
            child.setScaleX(1 - SCALE_STEP * multiple);
            child.setScaleY(1 - SCALE_STEP * multiple);
        }
    }

    private void adjustChildrenOfUnderTopView(float scrollRate) {
        int count = getChildCount();
        if (count > 1) {
            int i;
            int multiple;
            if (count == 2) {
                i = LAST_OBJECT_IN_STACK - 1;
                multiple = 1;
            } else {
                i = LAST_OBJECT_IN_STACK - 2;
                multiple = 2;
            }
            float rate = Math.abs(scrollRate);  // 0.0 ~ 1.0
            for (; i < LAST_OBJECT_IN_STACK; i++, multiple--) {
                View underTopView = getChildAt(i);
                int offset = (int) (yOffsetStep * (multiple - rate));
                int offsetTopAndBottom;
                if (isUp) {
                    offsetTopAndBottom = offset - underTopView.getTop() + initTop;
                } else {
                    offsetTopAndBottom = -offset - underTopView.getTop()+initTop;
                }

                float scaleXY = 1 - SCALE_STEP * multiple + SCALE_STEP * rate;

                underTopView.offsetTopAndBottom(offsetTopAndBottom);
                underTopView.setScaleX(scaleXY);
                underTopView.setScaleY(scaleXY);
            }
        }
    }

    /**
     * Set the top view and add the fling listener
     */
    private void setTopView() {
        if (getChildCount() > 0) {

            mActiveCard = getChildAt(LAST_OBJECT_IN_STACK);
            if (mActiveCard != null && mFlingListener != null) {

                flingCardListener = new FlingCardListener(mActiveCard, mAdapter.getItem(0),
                        ROTATION_DEGREES, new FlingCardListener.FlingListener() {

                    @Override
                    public void onCardExited() {
                        removeViewInLayout(mActiveCard);
                        mActiveCard = null;
                        mFlingListener.removeFirstObjectInAdapter();
                    }

                    @Override
                    public void leftExit(Object dataObject) {
                        mFlingListener.onLeftCardExit(dataObject);
                    }

                    @Override
                    public void rightExit(Object dataObject) {
                        mFlingListener.onRightCardExit(dataObject);
                    }

                    @Override
                    public void onClick(MotionEvent event, View v, Object dataObject) {
                        if (mOnItemClickListener != null)
                            mOnItemClickListener.onItemClicked(event, v, dataObject);
                    }

                    @Override
                    public void onScroll(float progress, float scrollXProgress) {
                        Log.e("Log", "onScroll " + progress);
                        adjustChildrenOfUnderTopView(progress);
                        mFlingListener.onScroll(progress, scrollXProgress);
                    }
                });
                // 设置是否支持左右滑
                flingCardListener.setIsNeedSwipe(isNeedSwipe);

                mActiveCard.setOnTouchListener(flingCardListener);
            }
        }
    }

    public FlingCardListener getTopCardListener() throws NullPointerException {
        if (flingCardListener == null) {
            throw new NullPointerException("flingCardListener is null");
        }
        return flingCardListener;
    }

    public void setMaxVisible(int MAX_VISIBLE) {
        this.MAX_VISIBLE = MAX_VISIBLE;
    }

    public void setMinStackInAdapter(int MIN_ADAPTER_STACK) {
        this.MIN_ADAPTER_STACK = MIN_ADAPTER_STACK;
    }

    @Override
    public Adapter getAdapter() {
        return mAdapter;
    }


    @Override
    public void setAdapter(Adapter adapter) {
        if (mAdapter != null && mDataSetObserver != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mDataSetObserver = null;
        }

        mAdapter = adapter;

        if (mAdapter != null && mDataSetObserver == null) {
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    public void setFlingListener(onFlingListener onFlingListener) {
        this.mFlingListener = onFlingListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FrameLayout.LayoutParams(getContext(), attrs);
    }


    private class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            requestLayout();
        }

    }


    /***
     * topView的点击事件
     */
    public interface OnItemClickListener {
        void onItemClicked(MotionEvent event, View v, Object dataObject);
    }

    public interface onFlingListener {
        void removeFirstObjectInAdapter();

        void onLeftCardExit(Object dataObject);

        void onRightCardExit(Object dataObject);

        void onAdapterAboutToEmpty(int itemsInAdapter);

        void onScroll(float progress, float scrollXProgress);
    }


}
