package com.xiaoanjujia.home.composition.unlocking.add_info_go_on;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = AddInfoGoOnPresenterModule.class)
public interface AddInfoGoOnComponent {

    void inject(AddInfoGoOnActivity activity);
}
