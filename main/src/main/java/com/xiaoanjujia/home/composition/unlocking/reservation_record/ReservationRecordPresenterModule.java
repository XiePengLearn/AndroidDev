package com.xiaoanjujia.home.composition.unlocking.reservation_record;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class ReservationRecordPresenterModule {
    private ReservationRecordContract.View view;

    private MainDataManager mainDataManager;

    public ReservationRecordPresenterModule(ReservationRecordContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    ReservationRecordContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
