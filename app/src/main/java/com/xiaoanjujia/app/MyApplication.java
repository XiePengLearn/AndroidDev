package com.xiaoanjujia.app;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.xiaoanjujia.app_common.CommonModule;

/**
 * @author xp
 */
public class MyApplication extends Application {
    private static Context       mContext;
    private static MyApplication instance;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        CommonModule.init(this);
        instance = this;
        mContext = getApplicationContext();
    }
    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }
}
