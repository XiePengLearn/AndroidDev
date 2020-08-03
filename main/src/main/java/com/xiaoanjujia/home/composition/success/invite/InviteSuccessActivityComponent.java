package com.xiaoanjujia.home.composition.success.invite;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = InviteSuccessPresenterModule.class)
public interface InviteSuccessActivityComponent {

    void inject(InviteSuccessActivity activity);
}
