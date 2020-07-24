package com.xiaoanjujia.home.composition.main.homefragment;

import com.xiaoanjujia.common.PerFragment;
import com.xiaoanjujia.common.AppComponent;

import dagger.Component;

/**
 * Created by xp on 2017/3/12.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = HomePresenterModule.class)
public interface HomeFragmentComponent {
    void inject(MainHomeFragment fragment);
}
