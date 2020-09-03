package com.xiaoanjujia.home.composition.community.category;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class CategoryDetailsPresenterModule {
    private CategoryDetailsContract.View view;

    private MainDataManager mainDataManager;

    public CategoryDetailsPresenterModule(CategoryDetailsContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    CategoryDetailsContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
