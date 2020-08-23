package com.xiaoanjujia.home.composition.community;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = CompositionDetailPresenterModule.class)
public interface CompositionDetailActivityComponent {

    void inject(CompositionDetailActivity activity);
}
