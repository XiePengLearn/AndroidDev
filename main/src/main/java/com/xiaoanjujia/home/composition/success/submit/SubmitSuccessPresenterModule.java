package com.xiaoanjujia.home.composition.success.submit;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class SubmitSuccessPresenterModule {
    private SubmitSuccessContract.View view;

    private MainDataManager mainDataManager;

    public SubmitSuccessPresenterModule(SubmitSuccessContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    SubmitSuccessContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
