package com.xiaoanjujia.home.composition.main.community;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@Module
public class CommunityFragmentModule {

    private CommunityFragmentContract.View view;

    private MainDataManager mainDataManager;

    public CommunityFragmentModule(CommunityFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    CommunityFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
