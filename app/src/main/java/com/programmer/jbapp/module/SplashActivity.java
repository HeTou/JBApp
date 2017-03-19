package com.programmer.jbapp.module;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lib.base.utils.AnimationController;
import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;

/***
 * 欢迎界面
 * 1、两秒后进入广告
 * 2、判断广告页是否加载完成，如果没有的就直接跳转的主页，有的话就显示广告页
 */
public class SplashActivity extends AbsBaseActivity implements View.OnClickListener {

    private android.widget.ImageView banner;
    private android.widget.RelativeLayout skip;

    private Handler goHandler = new Handler();
    private AnimationController animationController = new AnimationController();
    //  标识
    private boolean isReady = false;    //图片是否加载完成


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        initUI();
        initData();
        initEvent();

    }

    private void initEvent() {
        skip.setOnClickListener(this);
    }

    private void initData() {
        Glide.with(this)
                .load("http://img2.imgtn.bdimg.com/it/u=806514227,2446044831&fm=23&gp=0.jpg")
                .asBitmap()
                .into(target);
        welcome();   //两秒后进入主界面
    }

    private void initUI() {
        this.skip = (RelativeLayout) findViewById(R.id.skip);
        this.banner = (ImageView) findViewById(R.id.banner);
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            isReady = true;
            Toast.makeText(SplashActivity.this, "onResourceReady" + bitmap.getWidth() + "---" + bitmap.getHeight(), Toast.LENGTH_SHORT).show();
            banner.setImageBitmap(bitmap);
        }
    };

    /**
     * 两秒后进入主界面
     */
    private void welcome() {
        goHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isReady) {
//                   显示广告页
                    banner.setVisibility(View.VISIBLE);
                    skip.setVisibility(View.VISIBLE);
                    animationController.fadeIn(banner, 500, 0);
                    goHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toMainActivity();
                        }
                    }, 3000);
                } else {
                    toMainActivity();
                }
            }
        }, 2000);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip:
                toMainActivity();
                goHandler.removeCallbacksAndMessages(null);
                break;
        }
    }

    /**
     * 跳转到主界面
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void toMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        SplashActivity.this.finish();
    }

    /**
     * 屏蔽后退键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
