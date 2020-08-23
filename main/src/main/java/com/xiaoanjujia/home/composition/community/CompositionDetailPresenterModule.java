package com.xiaoanjujia.home.composition.community;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class CompositionDetailPresenterModule {
    private CompositionDetailContract.View view;

    private MainDataManager mainDataManager;

    public CompositionDetailPresenterModule(CompositionDetailContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    CompositionDetailContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
