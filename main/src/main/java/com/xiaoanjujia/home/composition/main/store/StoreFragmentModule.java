package com.xiaoanjujia.home.composition.main.store;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@Module
public class StoreFragmentModule {

    private StoreFragmentContract.View view;

    private MainDataManager mainDataManager;

    public StoreFragmentModule(StoreFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    StoreFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
