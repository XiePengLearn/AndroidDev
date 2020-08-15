package com.xiaoanjujia.home.composition.unlocking.visitor;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = VisitorPresenterModule.class)
public interface VisitorActivityComponent {

    void inject(VisitorActivity activity);
}
