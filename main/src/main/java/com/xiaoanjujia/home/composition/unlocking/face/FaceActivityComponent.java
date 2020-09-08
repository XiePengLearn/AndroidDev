package com.xiaoanjujia.home.composition.unlocking.face;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = FacePresenterModule.class)
public interface FaceActivityComponent {

    void inject(FaceActivity activity);
}
