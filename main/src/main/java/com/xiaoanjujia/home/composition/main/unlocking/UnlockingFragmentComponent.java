package com.xiaoanjujia.home.composition.main.unlocking;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = UnlockingFragmentModule.class)
public interface UnlockingFragmentComponent {
    void inject(UnlockingFragment fragment);
}
