package com.programmer.jbapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.programmer.jbapp.framework.AbsBaseActivity;

/***
 *  欢迎界面
 */
public class SplashActivity extends AbsBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        welcome(this);   //两秒后进入主界面
    }
    /**
     * 两秒后进入主界面
     * */
    private void welcome(final Activity activity){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(SplashActivity.this,MainActivity.class));
                activity.finish();
            }
        },2000);
    }
}
