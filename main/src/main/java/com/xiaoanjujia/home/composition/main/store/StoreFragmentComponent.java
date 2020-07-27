package com.xiaoanjujia.home.composition.main.store;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = StoreFragmentModule.class)
public interface StoreFragmentComponent {
    void inject(StoreFragment fragment);
}
