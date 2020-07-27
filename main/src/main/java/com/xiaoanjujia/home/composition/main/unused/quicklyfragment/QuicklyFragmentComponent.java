package com.xiaoanjujia.home.composition.main.unused.quicklyfragment;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = QuicklyFragmentModule.class)
public interface QuicklyFragmentComponent {
    void inject(QuicklyFragment fragment);
}
