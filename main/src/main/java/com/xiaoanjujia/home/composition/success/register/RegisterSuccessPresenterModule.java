package com.xiaoanjujia.home.composition.success.register;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class RegisterSuccessPresenterModule {
    private RegisterSuccessContract.View view;

    private MainDataManager mainDataManager;

    public RegisterSuccessPresenterModule(RegisterSuccessContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    RegisterSuccessContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
