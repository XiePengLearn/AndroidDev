package com.xiaoanjujia.home.composition.login.code_login;

import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:41
 * @Description:
 */

@Module
public class CodeLoginPresenterModule {
    private CodeLoginContract.View view;

    private MainDataManager mainDataManager;

    public CodeLoginPresenterModule(CodeLoginContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    CodeLoginContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }
}
