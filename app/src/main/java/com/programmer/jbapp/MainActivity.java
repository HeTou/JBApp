package com.programmer.jbapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.GridView;

import com.programmer.jbapp.framework.AbsBaseActivity;

/***
 * 主页
 */
public class MainActivity extends AbsBaseActivity {

    private android.support.v4.view.ViewPager mBananer;
    private android.widget.GridView mModule_Gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {

    }

    private void initData() {
        setBarLeftImg(0);
        setBarTitle("主页");
        refreshData();
    }

    private void initUI() {
        initBar(this);
        mModule_Gv = (GridView) findViewById(R.id.main_module_gv);
        mBananer = (ViewPager) findViewById(R.id.bananer_vp);
    }

    /**
     *  刷新数据
     */
    private void refreshData() {

    }
}
