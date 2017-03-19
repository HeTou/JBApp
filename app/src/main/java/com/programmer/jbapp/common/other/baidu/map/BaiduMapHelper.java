package com.programmer.jbapp.common.other.baidu.map;

import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.programmer.jbapp.common.other.baidu.map.interfaces.IBaiduMap;

/**
 * Created by Administrator on 2017/2/10.
 */

public class BaiduMapHelper implements IBaiduMap{

    private static BaiduMapHelper instance;
    public static BaiduMapHelper getInstance(){
        if(instance==null) {
            instance = new BaiduMapHelper();
        }
        return instance;
    }

    @Override
    public void init(Context context) {
        SDKInitializer.initialize(context);
    }

    @Override
    public void setMapType(MapView mapView, int mapType) {
        mapView.getMap().setMapType(mapType);
    }

    @Override
    public void setTrafficEnabled(MapView mapView, boolean bool) {
        mapView.getMap().setTrafficEnabled(bool);
    }

    @Override
    public void setBaiduHeatMapEnabled(MapView mapView, boolean bool) {
        mapView.getMap().setBaiduHeatMapEnabled(bool);
    }

    @Override
    public void clearViewChange(MapView mapView) {
        BaiduMap map = mapView.getMap();
        MapStatus ms = new MapStatus.Builder(map.getMapStatus())
                .rotate(0)
                .overlook(0)
                .build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
        map.animateMapStatus(u);
    }
}
