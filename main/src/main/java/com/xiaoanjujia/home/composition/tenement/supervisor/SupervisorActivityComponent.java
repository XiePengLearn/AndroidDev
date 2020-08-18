package com.xiaoanjujia.home.composition.tenement.supervisor;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = SupervisorPresenterModule.class)
public interface SupervisorActivityComponent {

    void inject(SupervisorActivity activity);
}
