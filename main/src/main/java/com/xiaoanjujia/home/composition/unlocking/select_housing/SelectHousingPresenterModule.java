package com.xiaoanjujia.home.composition.unlocking.select_housing;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class SelectHousingPresenterModule {
    private SelectHousingContract.View view;

    private MainDataManager mainDataManager;

    public SelectHousingPresenterModule(SelectHousingContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    SelectHousingContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
