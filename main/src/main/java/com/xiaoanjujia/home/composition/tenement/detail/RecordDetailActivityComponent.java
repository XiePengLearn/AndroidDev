package com.xiaoanjujia.home.composition.tenement.detail;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = RecordDetailPresenterModule.class)
public interface RecordDetailActivityComponent {

    void inject(RecordDetailActivity activity);
}
