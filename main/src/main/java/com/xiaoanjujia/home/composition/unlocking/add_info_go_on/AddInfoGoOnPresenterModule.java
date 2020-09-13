package com.xiaoanjujia.home.composition.unlocking.add_info_go_on;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class AddInfoGoOnPresenterModule {
    private AddInfoGoOnContract.View view;

    private MainDataManager mainDataManager;

    public AddInfoGoOnPresenterModule(AddInfoGoOnContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    AddInfoGoOnContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
