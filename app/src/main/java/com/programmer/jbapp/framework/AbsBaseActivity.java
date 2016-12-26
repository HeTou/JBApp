package com.programmer.jbapp.framework;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.StyleConfig;

public class AbsBaseActivity extends AppCompatActivity {


    private ImageView barleftimg;
    private TextView bartitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StyleConfig.immersion(this,true);  //使用沉浸式状态栏
        setContentView(R.layout.activity_title);
    }

    public void initBar(Activity activity) {
        bartitle = (TextView) findViewById(R.id.bar_title);
        barleftimg = (ImageView) findViewById(R.id.bar_leftimg);
        barleftimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void setBarLeftImg(int resId){
        if(resId<=0){
            barleftimg.setVisibility(View.GONE);
        }else {
            barleftimg.setVisibility(View.VISIBLE);
            barleftimg.setImageResource(resId);
        }
    }
    public void setBarTitle(String title){
        bartitle.setText(title);
    }
}
