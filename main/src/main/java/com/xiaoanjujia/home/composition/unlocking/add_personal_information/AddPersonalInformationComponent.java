package com.xiaoanjujia.home.composition.unlocking.add_personal_information;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = AddPersonalInformationPresenterModule.class)
public interface AddPersonalInformationComponent {

    void inject(AddPersonalInformationActivity activity);
}
