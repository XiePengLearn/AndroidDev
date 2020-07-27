package com.xiaoanjujia.home.composition.main;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * Created by admin on 2017/3/12.
 */
@PerActivity
@Component(dependencies = AppComponent.class , modules = MainPresenterModule .class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
