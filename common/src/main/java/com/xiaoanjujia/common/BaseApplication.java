package com.xiaoanjujia.common;

import android.app.Application;
import android.os.Handler;

/**
 * @Auther: xp
 * @Date: 2020/7/24 09:52
 * @Description:
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static Handler mHandler;
    public static BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mHandler = new Handler();
    }
    public static Handler getHandler() {
        return mHandler;
    }
}
