package com.xiaoanjujia.home.composition.success.audit;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class AuditSuccessPresenterModule {
    private AuditSuccessContract.View view;

    private MainDataManager mainDataManager;

    public AuditSuccessPresenterModule(AuditSuccessContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    AuditSuccessContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
