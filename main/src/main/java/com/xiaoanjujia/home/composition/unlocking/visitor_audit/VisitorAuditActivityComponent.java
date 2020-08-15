package com.xiaoanjujia.home.composition.unlocking.visitor_audit;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = VisitorAuditPresenterModule.class)
public interface VisitorAuditActivityComponent {

    void inject(VisitorAuditActivity activity);
}
