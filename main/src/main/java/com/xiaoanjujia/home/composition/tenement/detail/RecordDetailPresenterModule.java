package com.xiaoanjujia.home.composition.tenement.detail;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class RecordDetailPresenterModule {
    private RecordDetailContract.View view;

    private MainDataManager mainDataManager;

    public RecordDetailPresenterModule(RecordDetailContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    RecordDetailContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
