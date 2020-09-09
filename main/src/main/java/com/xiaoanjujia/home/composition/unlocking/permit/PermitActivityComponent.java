package com.xiaoanjujia.home.composition.unlocking.permit;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = PermitPresenterModule.class)
public interface PermitActivityComponent {

    void inject(PermitActivity activity);
}
