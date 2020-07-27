package com.xiaoanjujia.home.composition.main.unused.quicklyactivity;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = QuicklyPresenterModule.class)
public interface QuicklyActivityComponent {

    void inject(QuicklyActivity activity);
}
