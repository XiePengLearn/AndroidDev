package com.xiaoanjujia.home.composition.me.certification_merchants;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = CertificationMerchantsPresenterModule.class)
public interface CertificationMerchantsActivityComponent {

    void inject(CertificationMerchantsActivity activity);
}
