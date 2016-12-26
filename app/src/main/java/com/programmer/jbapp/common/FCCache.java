package com.programmer.jbapp.common;

import android.content.Context;

import com.lib.base.utils.SdUtils;
import com.programmer.jbapp.application.JBApplication;

import java.io.File;

/**
 * Created by LWK
 * TODO 各类缓存文件地址
 * 2016/8/15
 */
public class FCCache
{
    private FCCache(Context context)
    {
        ROOT_PATH = SdUtils.getDefaultCachePath(context);
    }

    private static final class FCCacheHolder
    {
        private static FCCache instance = new FCCache(JBApplication.getInstance());
    }

    public static FCCache getInstance()
    {
        return FCCacheHolder.instance;
    }

    private final String USER_HEAD_FLODER_NAME = "userhead";
    private final String VOICE_MSG_FLODER_NAME = "voice";
    private final String IMAGE_FLODER_NAME = "image";
    private final String VIDEO_FLODER_NAME = "video";

    private String ROOT_PATH;

    private String mUserHeadCachePath;

    private String mVoiceMsgCachePath;

    private String mImageCachePath;

    private String mVideoCachePath;

    /**
     * 获取用户头像缓存地址
     */
    public String getUserHeadCachePath()
    {
        if (mUserHeadCachePath == null)
        {
            File file = new File(ROOT_PATH, USER_HEAD_FLODER_NAME);
            mUserHeadCachePath = file.getAbsolutePath();
            if (!file.exists() && !file.mkdirs())
                mUserHeadCachePath = ROOT_PATH;
        }
        return mUserHeadCachePath;
    }

    /**
     * 获取语音消息缓存地址
     */
    public String getVoiceCachePath()
    {
        if (mVoiceMsgCachePath == null)
        {
            File file = new File(ROOT_PATH, VOICE_MSG_FLODER_NAME);
            mVoiceMsgCachePath = file.getAbsolutePath();
            if (!file.exists() && !file.mkdirs())
                mVoiceMsgCachePath = ROOT_PATH;
        }
        return mVoiceMsgCachePath;
    }

    /**
     * 获取语音消息缓存地址
     */
    public String getImageCachePath()
    {
        if (mImageCachePath == null)
        {
            File file = new File(ROOT_PATH, IMAGE_FLODER_NAME);
            mImageCachePath = file.getAbsolutePath();
            if (!file.exists() && !file.mkdirs())
                mImageCachePath = ROOT_PATH;
        }
        return mImageCachePath;
    }

    /**
     * 获取短视频消息缓存地址
     */
    public String getVideoCachePath()
    {
        if (mVideoCachePath == null)
        {
            File file = new File(ROOT_PATH, VIDEO_FLODER_NAME);
            mVideoCachePath = file.getAbsolutePath();
            if (!file.exists() && !file.mkdirs())
                mVideoCachePath = ROOT_PATH;
        }
        return mVideoCachePath;
    }
}
