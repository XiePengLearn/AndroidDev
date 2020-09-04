package com.xiaoanjujia.home.composition.unlocking.qr_code;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class VisitorPresenterModule {
    private VisitorContract.View view;

    private MainDataManager mainDataManager;

    public VisitorPresenterModule(VisitorContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    VisitorContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
