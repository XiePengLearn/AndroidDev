package com.xiaoanjujia.home.composition.me.deposit;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = DepositPresenterModule.class)
public interface DepositActivityComponent {

    void inject(DepositActivity activity);
}
