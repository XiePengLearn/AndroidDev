package com.xiaoanjujia.home.composition.me.certification_merchants;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class CertificationMerchantsPresenterModule {
    private CertificationMerchantsContract.View view;

    private MainDataManager mainDataManager;

    public CertificationMerchantsPresenterModule(CertificationMerchantsContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    CertificationMerchantsContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
