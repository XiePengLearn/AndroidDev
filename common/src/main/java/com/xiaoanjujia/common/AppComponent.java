package com.xiaoanjujia.common;

import android.content.Context;

import com.xiaoanjujia.common.model.DataManager;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xp on 2017/3/10.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface AppComponent {
    Context getContext();
    DataManager getDataManager();


}
