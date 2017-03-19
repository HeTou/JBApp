package com.programmer.jbapp.module.technique.item;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lib.base.toast.ToastUtils;
import com.lib.shortvideo.RecordShortVideoActivity;
import com.programmer.jbapp.R;
import com.programmer.jbapp.common.FCCache;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;
import com.yqritc.scalablevideoview.ScalableVideoView;

import java.io.File;
import java.io.IOException;

/**
 * zft
 * 2016/12/1.
 */
public class ShortVideoActivity extends AbsBaseActivity implements View.OnClickListener, ItemInfo {
    Button btn, btn2;
    private com.yqritc.scalablevideoview.ScalableVideoView videoview;

    private String videoPath;

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
        btn2.setOnClickListener(this);
    }

    private void initData() {
        Intent intent = getIntent();
        setBarTitle(intent.getStringExtra("title"));
    }

    private void initUI() {
        initBar(this);
        this.videoview = (ScalableVideoView) findViewById(R.id.videoview);
        this.btn2 = (Button) findViewById(R.id.btn2);
        this.btn = (Button) findViewById(R.id.btn);
    }


    //跳转到短视频界面的requestCode
    private static final int REQUEST_CODE_SHORT_VIDEO = 1001;
    //跳转到短视频界面的resultCode
    private static final int RESULT_CODE_SHORT_VIDEO = 1002;

    private void recordVideo() {
        RecordShortVideoActivity.start(this, 10, 1, 1.0f, FCCache.getInstance().getVideoCachePath()
                , REQUEST_CODE_SHORT_VIDEO, RESULT_CODE_SHORT_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SHORT_VIDEO:
                if (resultCode == RESULT_CODE_SHORT_VIDEO) {
                    String videoPath = data.getStringExtra(RecordShortVideoActivity.INTENT_KEY_RESULT_PATH);
                    long time = data.getLongExtra(RecordShortVideoActivity.INTENT_KEY_RESULT_DURATION, 0);
                    if (videoPath != null && time > 0)
//                        mPresenter.sendVideoMessage(mConType, mConversationId, videoPath, time);
                        this.videoPath = videoPath;
                    ToastUtils.showShortMsg(this, videoPath);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                recordVideo();
                break;
            case R.id.btn2:
                if (videoPath != null) {
                    File file = new File(videoPath);
                    if(file.exists()) {
                        setVideo(videoview);
                    }else{
                        Toast.makeText(this,videoPath+"不存在",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }

    }

    private void setVideo(final ScalableVideoView videoView) {
        btn2.setText(videoPath);
        showError("开始播放",false);
        try {
            videoView.setDataSource(videoPath);
            videoView.setVolume(1f, 1f);
            videoView.setLooping(true);
            videoView.prepareAsync(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    videoView.start();
                }
            });
        } catch (IOException e) {
            showError("数据异常！无法播放视频", true);
        }

    }

    public void showError(String errMsgResId, boolean needFinish) {
        Toast.makeText(this, errMsgResId, Toast.LENGTH_SHORT).show();
        if (needFinish)
            finish();
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
