package com.lib.base.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Function:Activity管理类
 */
public class AppManager
{
    private AppManager()
    {
    }

    private static final class ActivityManagerHolder
    {
        public static AppManager instance = new AppManager();
    }

    public static AppManager getInstance()
    {
        return ActivityManagerHolder.instance;
    }

    private Stack<Activity> mStackList;

    /**
     * 添加一个Activity实例到栈中
     *
     * @param activity 待添加实例
     */
    public void addActivity(Activity activity)
    {
        if (mStackList == null)
            mStackList = new Stack<>();

        mStackList.add(activity);
    }

    /**
     * 从栈中移除一个Activity实例
     *
     * @param activity 待移除实例
     * @return 移除成功/失败
     */
    public boolean removeActivity(Activity activity)
    {
        if (activity != null && mStackList != null && mStackList.contains(activity))
            return mStackList.remove(activity);
        else
            return false;
    }

    /**
     * 获取当前栈顶的Activity实例
     *
     * @return 栈顶的Activity实例
     */
    public Activity getPopActivity()
    {
        if (mStackList == null || mStackList.isEmpty())
            return null;

        return mStackList.lastElement();
    }

    /**
     * 移除栈内所有Activity实例
     */
    public void removeAllActivity()
    {
        if (mStackList != null)
            mStackList.removeAllElements();
    }

    /**
     * finish一个Activity并将其从栈中移除
     *
     * @param activity 待移除实例
     * @return 移除成功/失败
     */
    public boolean finishActivity(Activity activity)
    {
        if (activity != null && !activity.isFinishing())
        {
            activity.finish();
            if (mStackList != null)
                return mStackList.remove(activity);
            else
                return false;
        } else
        {
            return false;
        }
    }

    /**
     * finish所有栈中Activity并从栈中移除
     */
    public void finishAllActivity()
    {
        if (mStackList != null)
        {
            for (Activity activity : mStackList)
            {
                activity.finish();
            }
            mStackList.removeAllElements();
        }
    }

    /**
     * 除了某个特定的Activity之外finish所有栈中Activity并从栈中移除
     *
     * @param actClass 不需要被移除的实例的类
     */
    public void finishAllActivityExceptOne(Class actClass)
    {
        if (mStackList != null)
        {
            List<Activity> list = new ArrayList<>();
            for (Activity eachElement : mStackList)
            {
                if (!eachElement.getClass().equals(actClass))
                {
                    eachElement.finish();
                    list.add(eachElement);
                }
            }

            mStackList.removeAll(list);
        }
    }

    /***
     * 获取app缓存路径
     * @param context
     * @param uniqueName
     * @return
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        File file = new File(cachePath + File.separator + uniqueName);
        if(!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    /***
     * 获取缓存路径
     * @param context
     * @return
     */
    public int getAppVersion(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
       return 1;
    }
}
