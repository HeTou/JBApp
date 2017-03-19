package com.programmer.jbapp.module.technique.item;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.lib.base.log.KLog;
import com.programmer.jbapp.R;
import com.programmer.jbapp.common.other.baidu.map.BaiduMapHelper;
import com.programmer.jbapp.common.other.baidu.map.location.BaiduLocationManager;
import com.programmer.jbapp.framework.ItemInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/10.
 */

public class BaiduMapActivity extends Activity implements ItemInfo, View.OnClickListener {
    private MapView mapView;
    private BaiduMapHelper helper;

    private Button btn1, btn2, btn3;
    private BaiduLocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidumap_activity);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    private void initData() {
        helper = BaiduMapHelper.getInstance();
        locationManager = new BaiduLocationManager(this, mapView);

    }

    private void initUI() {
        setMapCustomFile(BaiduMapActivity.this);

        mapView = (MapView) findViewById(R.id.bmapView);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                showSettingDialog();
                break;
            case R.id.btn2:
                showLocationDialog();
                break;
            case R.id.btn3:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationManager.stopLocation();
    }

    @Override
    public String getItemName() {
        return "百度地图的使用";
    }

    @Override
    public String getItemDec() {
        return "将百度地图的接口统一起来用类来管理";
    }


    private Dialog locationDialog;

    private void showLocationDialog() {
        if (locationDialog == null) {
            locationDialog = new Dialog(this);
            locationDialog.setContentView(R.layout.baidumap_locationdilaog);
        }
        LocationOnClick click = new LocationOnClick();
        locationDialog.findViewById(R.id.setting_btn1).setOnClickListener(click);
        locationDialog.findViewById(R.id.setting_btn2).setOnClickListener(click);
        locationDialog.findViewById(R.id.setting_btn3).setOnClickListener(click);
        locationDialog.findViewById(R.id.setting_btn4).setOnClickListener(click);
//        locationDialog.findViewById(R.id.setting_btn5).setOnClickListener(click);
//        locationDialog.findViewById(R.id.setting_btn6).setOnClickListener(click);

        locationDialog.show();
    }

    private void dismissLocationDialog() {
        if (locationDialog != null) {
            locationDialog.dismiss();
        }
    }

    private class LocationOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_btn1: {  //开关定位
                    Button btn = (Button) v;
                    if (!locationManager.isStart()) {
                        btn.setText("关闭定位");
                        locationManager.startLocation();
                    } else {
                        btn.setText("开启定位");
                        locationManager.stopLocation();
                    }
                }
                break;
                case R.id.setting_btn2: {  //设置定位图层显示方式
                    Button btn = (Button) v;
                    switch (locationManager.getmCurrentMode()) {
                        case NORMAL:
                            locationManager.setLocationMode(MyLocationConfiguration.LocationMode.COMPASS, true);
                            btn.setText("罗盘");
                            break;
                        case COMPASS:
                            locationManager.setLocationMode(MyLocationConfiguration.LocationMode.FOLLOWING, true);
                            helper.clearViewChange(mapView);
                            btn.setText("跟随");
                            break;
                        case FOLLOWING:
                            locationManager.setLocationMode(MyLocationConfiguration.LocationMode.NORMAL, true);
                            helper.clearViewChange(mapView);
                            btn.setText("普通");
                            break;
                    }
                }
                break;
                case R.id.setting_btn3:
                    locationManager.setMarker(R.drawable.arrow);
                    break;
                case R.id.setting_btn4:
                    break;
            }
        }
    }


    private Dialog settingDialog;

    private void showSettingDialog() {
        if (settingDialog == null) {
            settingDialog = new Dialog(this);
        }
        settingDialog.setContentView(R.layout.baidumap_settingdilaog);
        SettingOnClick click = new SettingOnClick();
        settingDialog.findViewById(R.id.setting_btn1).setOnClickListener(click);
        settingDialog.findViewById(R.id.setting_btn2).setOnClickListener(click);
        settingDialog.findViewById(R.id.setting_btn3).setOnClickListener(click);
        settingDialog.findViewById(R.id.setting_btn4).setOnClickListener(click);
        settingDialog.findViewById(R.id.setting_btn5).setOnClickListener(click);
        settingDialog.findViewById(R.id.setting_btn6).setOnClickListener(click);

        settingDialog.show();
    }

    private void dismissSettingDialog() {
        if (settingDialog != null) {
            settingDialog.dismiss();
        }
    }

    private boolean isTrafficEnabled = false;
    private boolean isHeatMapEnabled = false;
    private boolean isMapCustomEnable = false;

    private class SettingOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_btn1:
                    helper.setMapType(mapView, BaiduMapHelper.MapType.MAP_TYPE_NORMAL);
                    break;
                case R.id.setting_btn2:
                    helper.setMapType(mapView, BaiduMapHelper.MapType.MAP_TYPE_MAP_TYPE_SATELLITENORMAL);
                    break;
                case R.id.setting_btn3:
                    helper.setMapType(mapView, BaiduMapHelper.MapType.MAP_TYPE_NONE);
                    break;
                case R.id.setting_btn4:
                    isTrafficEnabled = !isTrafficEnabled;
                    helper.setTrafficEnabled(mapView, isTrafficEnabled);
                    break;
                case R.id.setting_btn5:
                    isHeatMapEnabled = !isHeatMapEnabled;
                    helper.setBaiduHeatMapEnabled(mapView, isHeatMapEnabled);
                    break;
                case R.id.setting_btn6:
                    isMapCustomEnable = !isMapCustomEnable;
                    MapView.setMapCustomEnable(isMapCustomEnable);
                    break;

            }
            dismissSettingDialog();
        }
    }

    // 设置个性化地图config文件路径
    private void setMapCustomFile(Context context) {
        FileOutputStream out = null;
        InputStream inputStream = null;
        String moduleName = null;
        try {
            inputStream = context.getAssets().open("customConfigdir/custom_config.txt");
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            moduleName = context.getFilesDir().getAbsolutePath();
            KLog.e(moduleName);
            File f = new File(moduleName + "/" + "custom_config.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();
            out = new FileOutputStream(f);
            out.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        MapView.setCustomMapStylePath(moduleName + "/custom_config.txt");

    }
}
