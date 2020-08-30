package com.xiaoanjujia.home.composition.me.post_message;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class PostMessagePresenterModule {
    private PostMessageContract.View view;

    private MainDataManager mainDataManager;

    public PostMessagePresenterModule(PostMessageContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    PostMessageContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
