package com.xiaoanjujia.home.composition.unlocking.reservation_record;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = ReservationRecordPresenterModule.class)
public interface ReservationRecordActivityComponent {

    void inject(ReservationRecordActivity activity);
}
