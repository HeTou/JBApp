package com.programmer.jbapp.module.view.item;

import android.os.Bundle;

import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2017/4/14.
 */

public class Widget_DrawableActivity extends AbsBaseActivity implements ItemInfo{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawable_activity);
    }

    @Override
    public String getItemName() {
        return null;
    }

    @Override
    public String getItemDec() {
        return null;
    }
}
