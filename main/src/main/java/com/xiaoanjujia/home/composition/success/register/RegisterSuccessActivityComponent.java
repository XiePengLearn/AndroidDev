package com.xiaoanjujia.home.composition.success.register;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = RegisterSuccessPresenterModule.class)
public interface RegisterSuccessActivityComponent {

    void inject(RegisterSuccessActivity activity);
}
