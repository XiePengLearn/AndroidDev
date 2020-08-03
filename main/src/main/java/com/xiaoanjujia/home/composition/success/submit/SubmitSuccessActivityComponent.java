package com.xiaoanjujia.home.composition.success.submit;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = SubmitSuccessPresenterModule.class)
public interface SubmitSuccessActivityComponent {

    void inject(SubmitSuccessActivity activity);
}
