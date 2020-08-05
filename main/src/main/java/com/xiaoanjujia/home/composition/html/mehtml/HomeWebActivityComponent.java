package com.xiaoanjujia.home.composition.html.mehtml;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = HomeWebPresenterModule.class)
public interface HomeWebActivityComponent {

    void inject(HomeWebActivity activity);
}
