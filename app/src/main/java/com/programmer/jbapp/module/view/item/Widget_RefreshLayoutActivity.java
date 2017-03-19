package com.programmer.jbapp.module.view.item;

import android.os.Bundle;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.widget.refreshlayout.TwinklingRefreshLayout;
import com.programmer.jbapp.common.widget.refreshlayout.header.bezierlayout.BezierLayout;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2017/3/15.
 */

public class Widget_RefreshLayoutActivity extends AbsBaseActivity implements ItemInfo {
    private TwinklingRefreshLayout aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refreshlayout_activity);
        aa  = (TwinklingRefreshLayout) findViewById(R.id.aa);
        BezierLayout headerView = new BezierLayout(this);
//        aa.setHeaderView(headerView);
        aa.setPureScrollModeOn(true);
    }

    @Override
    public String getItemName() {
        return "下拉刷新的Layout";
    }

    @Override
    public String getItemDec() {
        return "可下拉刷新，回弹";
    }
}
