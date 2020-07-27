package com.xiaoanjujia.home.composition.main.community;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = CommunityFragmentModule.class)
public interface CommunityFragmentComponent {
    void inject(CommunityFragment fragment);
}
