package com.xiaoanjujia.home.composition.main.homefragment;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xp on 2017/3/12.
 */
@Module
public class HomePresenterModule {
    private HomeContract.View view;

    private MainDataManager mainDataManager;

    public HomePresenterModule(HomeContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    HomeContract.View providerMainContractView(){
        return view;
    }
    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
