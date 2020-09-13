package com.xiaoanjujia.home.composition.unlocking.select_housing;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = SelectHousingPresenterModule.class)
public interface SelectHousingComponent {

    void inject(SelectHousingActivity activity);
}
