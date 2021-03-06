package com.xiaoanjujia.home.composition.unlocking.house_manager;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = HouseManagerPresenterModule.class)
public interface HouseManagerActivityComponent {

    void inject(HouseManagerActivity activity);
}
