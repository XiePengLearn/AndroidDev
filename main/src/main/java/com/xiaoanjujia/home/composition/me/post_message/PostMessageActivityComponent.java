package com.xiaoanjujia.home.composition.me.post_message;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:16
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = PostMessagePresenterModule.class)
public interface PostMessageActivityComponent {

    void inject(PostMessageActivity activity);
}
