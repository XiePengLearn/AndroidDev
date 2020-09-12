package com.xiaoanjujia.home.composition.unlocking.house_manager;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class HouseManagerPresenterModule {
    private HouseManagerContract.View view;

    private MainDataManager mainDataManager;

    public HouseManagerPresenterModule(HouseManagerContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    HouseManagerContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
