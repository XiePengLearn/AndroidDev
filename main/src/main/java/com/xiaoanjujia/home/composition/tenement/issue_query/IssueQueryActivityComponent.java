package com.xiaoanjujia.home.composition.tenement.issue_query;


import com.xiaoanjujia.common.AppComponent;
import com.xiaoanjujia.common.PerActivity;

import dagger.Component;

/**
 * @Auther: xp
 * @Date: 2019/10/
 * @Description:
 */

@PerActivity
@Component(dependencies = AppComponent.class , modules = IssueQueryPresenterModule.class)
public interface IssueQueryActivityComponent {

    void inject(IssueQueryActivity activity);
}
