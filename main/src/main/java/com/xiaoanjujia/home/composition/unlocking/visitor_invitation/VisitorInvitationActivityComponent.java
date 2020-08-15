package com.xiaoanjujia.home.composition.unlocking.visitor_invitation;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = VisitorInvitationPresenterModule.class)
public interface VisitorInvitationActivityComponent {

    void inject(VisitorInvitationActivity activity);
}
