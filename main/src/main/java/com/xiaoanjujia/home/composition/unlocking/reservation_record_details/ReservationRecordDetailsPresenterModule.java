package com.xiaoanjujia.home.composition.unlocking.reservation_record_details;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class ReservationRecordDetailsPresenterModule {
    private ReservationRecordDetailsContract.View view;

    private MainDataManager mainDataManager;

    public ReservationRecordDetailsPresenterModule(ReservationRecordDetailsContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ReservationRecordDetailsContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
