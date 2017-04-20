package com.programmer.jbapp.module.technique.item;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lib.base.app.AppManager;
import com.lib.base.log.KLog;
import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;
import com.programmer.jbapp.module.technique.item.http.interceptor.FtInterceptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * zft
 * 2017/4/20.
 */

public class OKHttpActivity extends AbsBaseActivity implements ItemInfo, View.OnClickListener {
    /**
     * 同步get请求
     */
    private Button mBtn1;
    /**
     * 同步post请求
     */
    private Button mBtn2;
    /**
     * 异步get请求
     */
    private Button mBtn3;
    /**
     * 异步post请求
     */
    private Button mBtn4;
    /**
     * 下载请求
     */
    private Button mBtn5;
    /**
     * 上传请求
     */
    private Button mBtn6;

    private OkHttpClient okHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_activity);
        initUI();
        initData();
        initEvent();
    }
    private void initUI() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn6 = (Button) findViewById(R.id.btn6);
    }

    private void initEvent() {
        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
        mBtn5.setOnClickListener(this);
        mBtn6.setOnClickListener(this);
    }

    private void initData() {
        File sdcache = AppManager.getInstance().getDiskCacheDir(this,"http");
        int cacheSize = 10 * 1024 * 1024;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
//                .addNetworkInterceptor(new FtNetworkInterceptor())
                .addNetworkInterceptor(new FtInterceptor(getApplicationContext()))
                .cache(new Cache(sdcache,cacheSize));
        okHttpClient = builder.build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                synGet();
                break;
            case R.id.btn2:
                synPost();
                break;
            case R.id.btn3:
                asynGet();
                break;
            case R.id.btn4:
                asynPost();
                break;
            case R.id.btn5:
               download();
                break;
            case R.id.btn6:
                upload();
                break;

        }
    }

    private void upload() {

    }

    private void download() {

    }

    private void asynPost() {
        RequestBody requestBody = new FormBody.Builder()
                .add("USERNAME","123456@qq.com")
                .add("PASSWORD","Axon1234")
                .build();
        Request request = new Request.Builder().post(requestBody).url("http://lead.360vt.cn/lead/checkLoginV2.do")
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i("wangshu", "cache---" + str);
                } else {
                    String body = response.body().string();
                    KLog.json(body);
                    String str = response.networkResponse().toString();
                    Log.i("wangshu", "network---" + str);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void asynGet() {
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .url("http://show.reedaxon.com/api/getPushAppMessage?time=2000-01-01 00:00:00&pageNum=1&pageSize=10")
                .cacheControl(new CacheControl.Builder().maxAge(240,TimeUnit.SECONDS).build())
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    String header = response.headers().toString();
                    KLog.json(header);
                    String body = response.body().string();
                    KLog.json(body);
                    Log.i("wangshu", "cache---" + str);
                } else {
                    String header = response.headers().toString();
                    KLog.json(header);
                    String body = response.body().string();
                    KLog.json(body);
                    String str = response.networkResponse().toString();
                    Log.i("wangshu", "network---" + str);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void synPost() {

    }

    //  同步get
    private void synGet() {

    }

    @Override
    public String getItemName() {
        return "okhttp的使用";
    }

    @Override
    public String getItemDec() {
        return "okhttp的简单使用";
    }


}


