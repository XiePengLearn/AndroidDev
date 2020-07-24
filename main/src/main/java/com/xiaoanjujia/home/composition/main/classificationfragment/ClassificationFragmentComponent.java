package com.xiaoanjujia.home.composition.main.classificationfragment;

import com.xiaoanjujia.common.PerFragment;
import com.xiaoanjujia.common.AppComponent;

import dagger.Component;

/**
 * Created by xp on 2017/3/12.
 */
@PerFragment
@Component(dependencies = AppComponent.class , modules = ClassificationPresenterModule.class)
public interface ClassificationFragmentComponent {
    void inject(ClassificationFragment fragment);
}
