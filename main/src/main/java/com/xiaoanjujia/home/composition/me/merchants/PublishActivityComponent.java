package com.xiaoanjujia.home.composition.me.merchants;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = PublishPresenterModule.class)
public interface PublishActivityComponent {

    void inject(PublishActivity activity);
}
