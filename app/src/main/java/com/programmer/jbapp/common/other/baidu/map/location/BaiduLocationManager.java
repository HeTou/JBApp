package com.programmer.jbapp.common.other.baidu.map.location;

import android.content.Context;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.lib.base.log.KLog;


/**
 * Created by Administrator on 2017/2/11.
 */

/***
 * 百度地图定位管理类
 */
public class BaiduLocationManager {
    private Context context;
    private MapView mapView;
    private BaiduMap baiduMap;

    // 定位相关
    LocationClient locClient;
//    BDLocationListener bDLocationListener;

    //    UI相关
    MyLocationConfiguration.LocationMode mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//
    //    COMPASS,    //罗盘态，显示定位方向圈，保持定位图标在地图中心
    //    FOLLOWING,  //跟随态，保持定位图标在地图中心
    //    NORMAL      //普通态： 更新定位数据时不对地图做任何操作
    BitmapDescriptor mCurrentMarker;    //定位目标标注


//    地图方向
    float sensorX;   //传感器的方向；
    BaiduSensorEventListener sensorEventListener;

    public BaiduLocationManager(Context context, final MapView mapView) {
        this.context = context;
        this.mapView = mapView;
        baiduMap = mapView.getMap();
        sensorEventListener = new BaiduSensorEventListener(context);
        sensorEventListener.setmOnOrientationListener(new BaiduSensorEventListener.OnOrientationListener(){
            @Override
            public void onOrientationChanged(float x) {
                sensorX = x;
                BDLocation lastKnownLocation = locClient.getLastKnownLocation();
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(lastKnownLocation.getRadius())
                        .direction(x)
                        .latitude(lastKnownLocation.getLatitude())
                        .longitude(lastKnownLocation.getLongitude())
                        .build();
                baiduMap.setMyLocationData(locData);
            }
        });

    }

    /***
     * 初始化定位
     */
    public void startLocation() {
        Toast.makeText(context, "开始定位", Toast.LENGTH_SHORT).show();
//       开启传感器
        sensorEventListener.start();
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        locClient = new LocationClient(context);
        locClient.registerLocationListener(new MyLocationListenner());
        locClient.setLocOption(getDefaultLocationClientOption());
        locClient.start();
    }

    /***
     * 关闭定位
     * 一般在Activity 的onDestroy()方法中调用
     */
    public void stopLocation() {
        locClient.stop();
        sensorEventListener.stop();
        // 关闭定位图层
        baiduMap.setMyLocationEnabled(false);
    }


    /***
     * 定位是否开启
     *
     * @return
     */
    public boolean isStart() {
        if (locClient == null) return false;
        boolean started = locClient.isStarted();
        return started;
    }


    /***
     * 设置定位图层显示方式
     * @param mode
     * @param enableDirection   是否显示方向
     */
    public void setLocationMode(MyLocationConfiguration.LocationMode mode, boolean enableDirection) {
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mode, enableDirection, mCurrentMarker));
        mCurrentMode = mode;
    }

    /***
     * 设置定位图层目标标注
     * @param marker
     */
    public void setMarker(BitmapDescriptor marker){
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, marker  ));
        mCurrentMarker  = marker;
    }

    /***
     * 设置定位图层目标标注
     * @param resId    资源id
     */
    public void setMarker(int resId){
        BitmapDescriptor marker = BitmapDescriptorFactory.fromResource(resId);
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, marker  ));
        mCurrentMarker  = marker;

    }


    public MyLocationConfiguration.LocationMode getmCurrentMode() {
        return mCurrentMode;
    }

    /***
     * 获取默认的Options
     *
     * @return
     */
    private LocationClientOption getDefaultLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(false);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        return option;
    }


    private boolean isFirstLoc = true;

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                KLog.e("空");
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())    //定位精度
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(sensorX)                     //GPS定位时方向角度
                    .latitude(location.getLatitude())   //百度纬度坐标
                    .longitude(location.getLongitude()) //百度经度坐标
                    .build();
            KLog.d(locData.latitude + "--" + locData.longitude);
//            setLocationMode(MyLocationConfiguration.LocationMode.NORMAL,true);
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();    //地图状态构造器
                builder.target(ll)      //设置地图中心点
                        .zoom(18.0f);   //设置地图缩放级别
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    }

}
