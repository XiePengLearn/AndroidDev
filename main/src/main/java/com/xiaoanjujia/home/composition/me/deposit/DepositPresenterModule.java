package com.xiaoanjujia.home.composition.me.deposit;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class DepositPresenterModule {
    private DepositContract.View view;

    private MainDataManager mainDataManager;

    public DepositPresenterModule(DepositContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    DepositContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
