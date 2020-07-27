package com.xiaoanjujia.home.composition.main.tenement;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = TenementFragmentModule.class)
public interface TenementFragmentComponent {
    void inject(TenementFragment fragment);
}
