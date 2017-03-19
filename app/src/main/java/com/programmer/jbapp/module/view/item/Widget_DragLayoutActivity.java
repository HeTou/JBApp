package com.programmer.jbapp.module.view.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.programmer.jbapp.R;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2016/11/22.
 */
public class Widget_DragLayoutActivity extends AbsBaseActivity implements ItemInfo {
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draglayout_activity);
        initUI();
        initData();
        initEvent();

    }

    private void initEvent() {

    }

    private void initData() {
        initBar(this);
        Intent intent = getIntent();
        setBarTitle( intent.getStringExtra("title"));
//        webview.loadUrl("http://frontend.csq.im/ci/app/to_weigu_zhubao?v=12&t=1479103556288&deviceid=868030027858362&appid=0303013d061&termcode=Android-6.0.1-MI5&role=normal&sign=cb28c4e9604724b032a31373b88f486c");
    }

    private void initUI() {

        this.webview = (WebView) findViewById(R.id.webview);
        WebSettings settings = webview.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webview.setOnKeyListener(mKeyListener);        //回退事件
    }

    /**
     * webview的按鍵監聽
     */
    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键  时的操作
                    webview.goBack();   //后退
                    //webview.goForward();//前进
                    return true;    //已处理
                }
            }
            return false;
        }
    };

    @Override
    public String getItemName() {
        String itemname = "侧滑控件";
        return itemname;
    }

    @Override
    public String getItemDec() {
        String itemdec = "侧滑控件";
        return itemdec;
    }
}
