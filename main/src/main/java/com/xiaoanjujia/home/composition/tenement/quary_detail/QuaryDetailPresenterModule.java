package com.xiaoanjujia.home.composition.tenement.quary_detail;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class QuaryDetailPresenterModule {
    private QuaryDetailContract.View view;

    private MainDataManager mainDataManager;

    public QuaryDetailPresenterModule(QuaryDetailContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    QuaryDetailContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
