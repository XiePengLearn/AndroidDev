package com.xiaoanjujia.home.composition.me.category;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = CategoryPresenterModule.class)
public interface CategoryActivityComponent {

    void inject(CategoryActivity activity);
}
