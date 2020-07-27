package com.xiaoanjujia.home.composition.main.mine;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MineFragmentModule.class)
public interface MineFragmentComponent {
    void inject(MineFragment fragment);
}
