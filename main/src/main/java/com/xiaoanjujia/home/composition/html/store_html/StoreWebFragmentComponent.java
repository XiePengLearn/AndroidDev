package com.xiaoanjujia.home.composition.html.store_html;

import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerFragment;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */

@PerFragment
@Component(dependencies = AppComponent.class , modules = StoreWebFragmentModule.class)
public interface StoreWebFragmentComponent {
    void inject(StoreWebFragment fragment);
}
