package com.xiaoanjujia.home.composition.main.classificationfragment;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xp on 2017/3/12.
 */
@Module
public class ClassificationPresenterModule {
    private ClassificationContract.View  view;

    private MainDataManager mainDataManager;

    public ClassificationPresenterModule(ClassificationContract.View  view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ClassificationContract.View providerMainContractView(){
        return view;
    }
    @Provides
    MainDataManager providerMainDataManager(){
        return mainDataManager;
    }
}
