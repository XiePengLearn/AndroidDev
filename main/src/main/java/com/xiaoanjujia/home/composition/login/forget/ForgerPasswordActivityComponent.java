package com.xiaoanjujia.home.composition.login.forget;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:40
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class, modules = ForgerPasswordPresenterModule.class)
public interface ForgerPasswordActivityComponent {
    void inject(ForgerPasswordActivity activity);
}
