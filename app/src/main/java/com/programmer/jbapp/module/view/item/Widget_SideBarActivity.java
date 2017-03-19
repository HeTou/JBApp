package com.programmer.jbapp.module.view.item;

import android.os.Bundle;

import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2017/2/5.
 */
public class Widget_SideBarActivity extends AbsBaseActivity implements ItemInfo {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidebar_activity);
    }

    @Override
    public String getItemName() {
        return "垂直字母索引";
    }

    @Override
    public String getItemDec() {
        return "简单的字母索引（传智视频的控件）";
    }
}
