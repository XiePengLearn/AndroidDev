package com.xiaoanjujia.home.composition.html.store_html;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@Module
public class StoreWebFragmentModule {

    private StoreWebFragmentContract.View view;

    private MainDataManager mainDataManager;

    public StoreWebFragmentModule(StoreWebFragmentContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    StoreWebFragmentContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
