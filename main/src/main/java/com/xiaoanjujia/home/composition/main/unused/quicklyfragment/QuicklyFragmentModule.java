package com.xiaoanjujia.home.composition.main.unused.quicklyfragment;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@Module
public class QuicklyFragmentModule {

    private QuicklyFragmentContract.View view;

    private MainDataManager mainDataManager;

    public QuicklyFragmentModule(QuicklyFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    QuicklyFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
