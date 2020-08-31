package com.xiaoanjujia.home.composition.tenement.quary_detail;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = QuaryDetailPresenterModule.class)
public interface QuaryDetailActivityComponent {

    void inject(QuaryDetailActivity activity);
}
