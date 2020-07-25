package com.xiaoanjujia.home.composition.login.forget;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:41
 * @Description:
 */

@Module
public class ForgerPasswordPresenterModule {
    private ForgerPasswordContract.View view;

    private MainDataManager mainDataManager;

    public ForgerPasswordPresenterModule(ForgerPasswordContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ForgerPasswordContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }
}
