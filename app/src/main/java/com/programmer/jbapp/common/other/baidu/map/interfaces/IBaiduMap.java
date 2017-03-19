package com.programmer.jbapp.common.other.baidu.map.interfaces;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

/**
 * Created by Administrator on 2017/2/10.
 */

/***
 * 使用步骤：
 * 1、初始化
 * 2、在使用地图的activity 生命周期添加 mapview的生命周期管理，三个
 *      mMapView.onDestroy();
 *      mMapView.onResume();
 *      mMapView.onPause();
 * 3、
 */
public interface IBaiduMap {


    /***
     * 在使用SDK各组件之前初始化context信息，传入ApplicationContext
     * 注意该方法要再setContentView方法之前实现
     * 我们建议该方法放在Application的初始化方法中
     *
     * @param context ApplicationContext(注意这里是applicationContext)
     */
    void init(Context context);

    /***
     * 设置地图类型
     * @param mapView   地图控件
     * @param MapType   BaiduMap.MAP_TYPE_NORMAL    普通地图;
     *                  BaiduMap.MAP_TYPE_SATELLITE 卫星地图;
     *                  BaiduMap.MAP_TYPE_NONE      白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
     */
    void setMapType(MapView mapView, int MapType);

    /***
     * 开关实时交通图
     * @param mapView   地图控件
     * @param bool      true:打开，false：关闭
     */
    void setTrafficEnabled(MapView mapView ,boolean bool);

    /***
     * 开关城市热力图
     * @param mapView   地图控件
     * @param bool      true:打开，false:关闭
     */
    void setBaiduHeatMapEnabled(MapView mapView,boolean bool);


    /***
     * 清除旋转，倾斜
     * @param mapView
     */
    void clearViewChange(MapView mapView);

    class MapType{
        public static final int MAP_TYPE_NORMAL = BaiduMap.MAP_TYPE_NORMAL;
        public static final int MAP_TYPE_MAP_TYPE_SATELLITENORMAL = BaiduMap.MAP_TYPE_SATELLITE;
        public static final int MAP_TYPE_NONE = BaiduMap.MAP_TYPE_NONE;
    }


}
