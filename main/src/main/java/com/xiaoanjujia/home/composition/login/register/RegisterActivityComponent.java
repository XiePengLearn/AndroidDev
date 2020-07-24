package com.xiaoanjujia.home.composition.login.register;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:40
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = RegisterPresenterModule.class)
public interface RegisterActivityComponent {
    void inject(RegisterActivity activity);
}
