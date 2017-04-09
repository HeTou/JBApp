package com.programmer.jbapp.module.view.item;

import android.os.Bundle;

import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;


/**
 * zft
 * 2017/3/28.
 */

public class Widget_ZFTRefreshLayout extends AbsBaseActivity implements ItemInfo {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zftrefresh_activity);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {

    }

    private void initData() {

    }

    private void initUI() {

    }

    @Override
    public String getItemName() {
        return "韬哥学习-自定义高级控件";
    }

    @Override
    public String getItemDec() {
        return "学习自定义控件-惯性滑动";
    }
}
