package com.xiaoanjujia.home.composition.unlocking.add_personal_information;


import com.xiaoanjujia.home.MainDataManager;

import dagger.Module;
import dagger.Provides;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
@Module
public class AddPersonalInformationPresenterModule {
    private AddPersonalInformationContract.View view;

    private MainDataManager mainDataManager;

    public AddPersonalInformationPresenterModule(AddPersonalInformationContract.View view, MainDataManager mainDataManager) {
        this.view = view;
        this.mainDataManager = mainDataManager;
    }

    @Provides
    AddPersonalInformationContract.View providerMainContractView() {
        return view;
    }

    @Provides
    MainDataManager providerMainDataManager() {
        return mainDataManager;
    }

}
