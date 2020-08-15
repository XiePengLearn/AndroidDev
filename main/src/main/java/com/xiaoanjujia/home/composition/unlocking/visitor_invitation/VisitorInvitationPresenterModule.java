package com.xiaoanjujia.home.composition.unlocking.visitor_invitation;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class VisitorInvitationPresenterModule {
    private VisitorInvitationContract.View view;

    private MainDataManager mainDataManager;

    public VisitorInvitationPresenterModule(VisitorInvitationContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    VisitorInvitationContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
