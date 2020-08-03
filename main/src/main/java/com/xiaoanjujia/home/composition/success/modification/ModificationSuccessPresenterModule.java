package com.xiaoanjujia.home.composition.success.modification;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class ModificationSuccessPresenterModule {
    private ModificationSuccessContract.View view;

    private MainDataManager mainDataManager;

    public ModificationSuccessPresenterModule(ModificationSuccessContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ModificationSuccessContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
