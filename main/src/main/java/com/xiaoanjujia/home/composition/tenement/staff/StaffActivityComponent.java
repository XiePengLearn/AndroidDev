package com.xiaoanjujia.home.composition.tenement.staff;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = StaffPresenterModule.class)
public interface StaffActivityComponent {

    void inject(StaffActivity activity);
}
