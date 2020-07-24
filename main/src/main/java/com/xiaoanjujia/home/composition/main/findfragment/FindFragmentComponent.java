package com.xiaoanjujia.home.composition.main.findfragment;

import com.xiaoanjujia.common.PerFragment;
import com.xiaoanjujia.common.AppComponent;

import dagger.Component;

/**
 * Created by xp on 2017/3/12.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = FindPresenterModule.class)
public interface FindFragmentComponent {
    void inject(FindFragment fragment);
}
