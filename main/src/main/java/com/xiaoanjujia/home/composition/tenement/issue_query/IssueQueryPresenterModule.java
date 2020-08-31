package com.xiaoanjujia.home.composition.tenement.issue_query;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class IssueQueryPresenterModule {
    private IssueQueryContract.View view;

    private MainDataManager mainDataManager;

    public IssueQueryPresenterModule(IssueQueryContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    IssueQueryContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
