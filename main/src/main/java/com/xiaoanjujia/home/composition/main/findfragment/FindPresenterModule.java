package com.xiaoanjujia.home.composition.main.findfragment;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xp on 2017/3/12.
 */
@Module
public class FindPresenterModule {
    private FindContract.View view;

    private MainDataManager mainDataManager;

    public FindPresenterModule(FindContract.View  view,MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    FindContract.View providerMainContractView(){
        return view;
    }


    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
