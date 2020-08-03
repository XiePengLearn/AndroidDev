package com.xiaoanjujia.home.composition.success.publish;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class PublishSuccessPresenterModule {
    private PublishSuccessContract.View view;

    private MainDataManager mainDataManager;

    public PublishSuccessPresenterModule(PublishSuccessContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    PublishSuccessContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
