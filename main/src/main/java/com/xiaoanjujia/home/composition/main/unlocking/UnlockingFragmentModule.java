package com.xiaoanjujia.home.composition.main.unlocking;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@Module
public class UnlockingFragmentModule {

    private UnlockingFragmentContract.View view;

    private MainDataManager mainDataManager;

    public UnlockingFragmentModule(UnlockingFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    UnlockingFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
