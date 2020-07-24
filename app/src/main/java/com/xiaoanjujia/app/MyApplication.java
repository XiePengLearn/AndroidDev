package com.xiaoanjujia.app;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.xiaoanjujia.app_common.CommonModule;
import com.xiaoanjujia.common.BaseApplication;

/**
 * @author xp
 */
public class MyApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CommonModule.init(this);
    }
}
