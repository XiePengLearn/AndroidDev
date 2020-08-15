package com.xiaoanjujia.home.composition.unlocking.visitor_audit;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class VisitorAuditPresenterModule {
    private VisitorAuditContract.View view;

    private MainDataManager mainDataManager;

    public VisitorAuditPresenterModule(VisitorAuditContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    VisitorAuditContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
