package com.xiaoanjujia.home.composition.login.login;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:17
 * @Description:
 */
@Module
public class LoginPresenterModule {
    private com.xiaoanjujia.home.composition.login.login.LoginContract.View view;

    private MainDataManager mainDataManager;

    public LoginPresenterModule(com.xiaoanjujia.home.composition.login.login.LoginContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    com.xiaoanjujia.home.composition.login.login.LoginContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
