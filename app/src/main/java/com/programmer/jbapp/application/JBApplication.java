package com.programmer.jbapp.application;

import com.lib.base.app.BaseApplication;
import com.lib.base.log.KLog;
import com.programmer.jbapp.common.db.GreenDaoHelper;
import com.programmer.jbapp.common.other.baidu.map.BaiduMapHelper;

import cn.jpush.android.api.JPushInterface;

import static com.lib.base.utils.AppUtil.getDeviceInfo;

/**
 * zft
 * 2016/11/21.
 */
public class JBApplication extends BaseApplication {
    public static JBApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initGreenDAO();
        initJPush();
        initBaiduMap();
        KLog.json(getDeviceInfo(this));
    }

    private void initGreenDAO() {
        GreenDaoHelper.initDatabase(this);
    }

    public static JBApplication getInstance() {
        return instance;
    }

    /**
     * 初始化jpush
     */
    private void initJPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /***
     * 初始化百度地图
     */
    private void initBaiduMap() {
        BaiduMapHelper.getInstance().init(getApplicationContext());
    }
}
