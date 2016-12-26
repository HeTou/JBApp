package com.programmer.jbapp.module.technique.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lib.base.toast.ToastUtils;
import com.lib.imrecordbutton.IMRecordButton;
import com.lib.imrecordbutton.IMRecordListener;
import com.lib.shortvideo.RecordShortVideoActivity;
import com.programmer.jbapp.R;
import com.programmer.jbapp.common.FCCache;
import com.programmer.jbapp.common.utils.voice.VoiceMessagePlayListener;
import com.programmer.jbapp.common.utils.voice.VoiceMessagePlayUtils;
import com.programmer.jbapp.framework.AbsBaseActivity;
import com.programmer.jbapp.framework.ItemInfo;

/**
 * zft
 * 2016/12/1.
 */
public class RecordVoiceActivity extends AbsBaseActivity implements View.OnClickListener,ItemInfo {
    IMRecordButton btn;
    Button play;
    private VoiceMessagePlayUtils mVoiceMessagePlayUtils;
    private String mFilepath ="";
    private int mCurPlayVoicePosition = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordvoice_activity);
        initUI();
        initData();
        initEvent();
    }

    private void initEvent() {
//        btn.setOnClickListener(this);
        play.setOnClickListener(this);
        btn.setOnRecordListener(new IMRecordListener() {
            @Override
            public void startRecord() {
                mVoiceMessagePlayUtils.stopVoice();
                mCurPlayVoicePosition = -1;
            }

            @Override
            public void recordFinish(float seconds, String filePath) {
                ToastUtils.showShortMsg(RecordVoiceActivity.this,"录制完成---"+filePath);
                mFilepath = filePath;
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        setBarTitle( intent.getStringExtra("title"));

        mVoiceMessagePlayUtils = new VoiceMessagePlayUtils(this);
    }

    private void initUI() {
        initBar(this);
        btn = (IMRecordButton) findViewById(R.id.btn);
        play = (Button) findViewById(R.id.play);
        btn.setCachePath(FCCache.getInstance().getVoiceCachePath());
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
            case R.id.play:
                mVoiceMessagePlayUtils.playVoice(mFilepath, new VoiceMessagePlayListener() {
                    @Override
                    public void startPlay(boolean isHandFree) {

                    }

                    @Override
                    public void endPlay(boolean isHandFree) {

                    }

                    @Override
                    public void error(int errorCode, int errMsgResId) {

                    }
                });
                break;
        }

    }



    @Override
    public String getItemName() {
        return "音频录制";
    }

    @Override
    public String getItemDec() {
        return "应用第三方类库";
    }


}
