package com.programmer.jbapp.module.technique.item;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;

import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2017/1/23.
 */
public class WaveViewActivity extends AbsBaseActivity implements ItemInfo {

    private com.programmer.jbapp.common.widget.WaveView waveView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave_activity);
        initUI();
        initData();
    }

    private void initData() {

    }

    private void initUI() {
        waveView = (com.programmer.jbapp.common.widget.WaveView) findViewById(R.id.wave);
        waveView.setDuration(5000);
        waveView.setStyle(Paint.Style.STROKE);
        waveView.setSpeed(400);
        waveView.setColor(Color.parseColor("#ff0000"));
        waveView.setInterpolator(new AccelerateInterpolator(1.2f));
        waveView.start();
    }

    @Override
    public String getItemName() {
        return "水波纹控件";
    }

    @Override
    public String getItemDec() {
        return "http://www.jianshu.com/p/cba46422de67";
    }
}
