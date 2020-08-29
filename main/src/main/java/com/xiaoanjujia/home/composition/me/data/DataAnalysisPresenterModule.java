package com.xiaoanjujia.home.composition.me.data;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class DataAnalysisPresenterModule {
    private DataAnalysisContract.View view;

    private MainDataManager mainDataManager;

    public DataAnalysisPresenterModule(DataAnalysisContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    DataAnalysisContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
