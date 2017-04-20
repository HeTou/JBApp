package com.programmer.jbapp.framework;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.programmer.jbapp.R;
import com.programmer.jbapp.common.StyleConfig;

import java.util.List;

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



    /**判断app是否运行在前台*/
    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
//                Toast.makeText(this,appProcessInfo.importance+"", Toast.LENGTH_SHORT).show();
                if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                    Toast.makeText(this, "运行在------前面----------------", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return false;
    }
    // 用来控制应用前后台切换的逻辑
    private boolean isCurrentRunningForeground = true;

    @Override
    protected void onStart() {
        super.onStart();
        if (!isCurrentRunningForeground) {
            // Log.d(TAG, ">>>>>>>>>>>>>>>>>>>切到前台 activity process");
//            Toast.makeText(this, "切到前台", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
            // Log.d(TAG,">>>>>>>>>>>>>>>>>>>切到后台 activity process");
//            Toast.makeText(this, "切到后台", Toast.LENGTH_SHORT).show();
        }
    }
}
