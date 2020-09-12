package com.xiaoanjujia.home.composition.unlocking.reservation_record_details;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ReservationRecordDetailsPresenterModule.class)
public interface ReservationRecordDetailsComponent {

    void inject(ReservationRecordDetailsActivity activity);
}
