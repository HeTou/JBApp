package com.programmer.jbapp.module.technique.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lib.base.toast.ToastUtils;
import com.lib.shortvideo.RecordShortVideoActivity;
import com.programmer.jbapp.R;
import com.programmer.jbapp.common.FCCache;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2016/12/1.
 */
public class ShortVideoActivity extends AbsBaseActivity implements View.OnClickListener,ItemInfo {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shortvideo_activity);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {
        btn.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        setBarTitle( intent.getStringExtra("title"));
    }

    private void initUI() {
        initBar(this);
        btn = (Button) findViewById(R.id.btn);
    }


    //跳转到短视频界面的requestCode
    private static final int REQUEST_CODE_SHORT_VIDEO = 1001;
    //跳转到短视频界面的resultCode
    private static final int RESULT_CODE_SHORT_VIDEO = 1002;

    private void recordVideo(){
        RecordShortVideoActivity.start(this, 10, 1, 1.0f, FCCache.getInstance().getVideoCachePath()
                , REQUEST_CODE_SHORT_VIDEO, RESULT_CODE_SHORT_VIDEO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CODE_SHORT_VIDEO:
                if (resultCode == RESULT_CODE_SHORT_VIDEO)
                {
                    String videoPath = data.getStringExtra(RecordShortVideoActivity.INTENT_KEY_RESULT_PATH);
                    long time = data.getLongExtra(RecordShortVideoActivity.INTENT_KEY_RESULT_DURATION, 0);
                    if (videoPath != null && time > 0)
//                        mPresenter.sendVideoMessage(mConType, mConversationId, videoPath, time);
                        ToastUtils.showShortMsg(this,videoPath);
                }
                break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn:
                recordVideo();
                break;
        }

    }



    @Override
    public String getItemName() {
        return "短视频录制";
    }

    @Override
    public String getItemDec() {
        return "应用第三方类库（ShortVideo）";
    }


}
