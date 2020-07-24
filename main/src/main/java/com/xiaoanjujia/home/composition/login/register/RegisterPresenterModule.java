package com.xiaoanjujia.home.composition.login.register;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:41
 * @Description:
 */

@Module
public class RegisterPresenterModule {
    private RegisterContract.View view;

    private MainDataManager mainDataManager;

    public RegisterPresenterModule(RegisterContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    RegisterContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }
}
