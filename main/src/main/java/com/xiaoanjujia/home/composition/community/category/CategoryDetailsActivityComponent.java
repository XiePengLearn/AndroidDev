package com.xiaoanjujia.home.composition.community.category;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = CategoryDetailsPresenterModule.class)
public interface CategoryDetailsActivityComponent {

    void inject(CategoryDetailsActivity activity);
}
