package com.programmer.jbapp.module.technique.item.http.interceptor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * zft
 * 2017/4/20.
 */

public class FtInterceptor implements Interceptor {
    private Context context;

    public FtInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        if (isNetworkConnected()) {
            int maxAge = 60 * 60;// 有网 就1个小时可用
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 7;// 没网 就1周可用
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
    /**
     * 手机是否联网
     */
    private boolean isNetworkConnected() {
        //6.0 之后得使用 getApplicationContext()..getSystemService(...)
        //否则会内存泄漏
        ConnectivityManager manager = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo.isConnected();
    }
}
