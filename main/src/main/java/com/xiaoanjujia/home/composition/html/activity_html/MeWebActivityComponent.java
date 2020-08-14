package com.xiaoanjujia.home.composition.html.activity_html;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = MeWebPresenterModule.class)
public interface MeWebActivityComponent {

    void inject(MeWebActivity activity);
}
