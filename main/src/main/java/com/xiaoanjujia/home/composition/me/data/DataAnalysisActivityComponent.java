package com.xiaoanjujia.home.composition.me.data;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = DataAnalysisPresenterModule.class)
public interface DataAnalysisActivityComponent {

    void inject(DataAnalysisActivity activity);
}
