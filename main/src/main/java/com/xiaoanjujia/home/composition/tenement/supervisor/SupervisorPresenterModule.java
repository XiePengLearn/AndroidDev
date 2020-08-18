package com.xiaoanjujia.home.composition.tenement.supervisor;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class SupervisorPresenterModule {
    private SupervisorContract.View view;

    private MainDataManager mainDataManager;

    public SupervisorPresenterModule(SupervisorContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    SupervisorContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
