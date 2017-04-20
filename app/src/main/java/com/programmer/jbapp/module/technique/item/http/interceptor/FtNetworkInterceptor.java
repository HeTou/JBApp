package com.programmer.jbapp.module.technique.item.http.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * zft
 * 2017/4/20.
 */

public class FtNetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();
        Response response = chain.proceed(request);

        return response;
    }
}
