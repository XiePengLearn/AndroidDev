package com.xiaoanjujia.home.composition.html.me_html;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = MeWebFragmentModule.class)
public interface MeWebFragmentComponent {
    void inject(MeWebFragment fragment);
}
