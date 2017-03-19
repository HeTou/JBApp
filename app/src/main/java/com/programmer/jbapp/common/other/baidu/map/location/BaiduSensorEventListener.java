package com.programmer.jbapp.common.other.baidu.map.location;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Administrator on 2017/2/11.
 */

public class BaiduSensorEventListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context context;
    private Sensor mSensor;// 传感器

    private float lastX;


    public BaiduSensorEventListener(Context context) {
        this.context = context;
    }

    // 开启监听
    @SuppressWarnings("deprecation")
    public void start() {
        //获得系统服务
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            //获得方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    // 结束监听
    public void stop() {
        //停止定位
        mSensorManager.unregisterListener(this);
    }


    /***
     *  // 当数据发生改变
     *
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //如果事件返回的类型是方向传感器
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float x = event.values[SensorManager.DATA_X];
            //如果变化大于一度
            if (Math.abs(x - lastX) > 1.0) {
                //通知主界面进行回掉
                if (mOnOrientationListener != null) {
                    mOnOrientationListener.onOrientationChanged(x);
                }
            }
            lastX = x;
        }
    }


    /***
     * 当精确度发生改变，则回调             Accuracy（精确度）
     *
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private OnOrientationListener mOnOrientationListener;

    public void setmOnOrientationListener(
            OnOrientationListener mOnOrientationListener) {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    //回掉接口
    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }
}
