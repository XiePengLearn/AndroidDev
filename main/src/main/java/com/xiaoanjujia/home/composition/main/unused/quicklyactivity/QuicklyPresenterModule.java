package com.xiaoanjujia.home.composition.main.unused.quicklyactivity;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class QuicklyPresenterModule {
    private QuicklyContract.View view;

    private MainDataManager mainDataManager;

    public QuicklyPresenterModule(QuicklyContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    QuicklyContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
