package com.xiaoanjujia.home.composition.main;

import java.util.Map;

/**
 * Created by xp on 2017/3/12.
 */

public interface MainContract {
 interface View {
    void setText(String text);

    void showProgressDialogView();

    void hiddenProgressDialogView();
}

 interface Presenter {
    void getText();

    void destory();

    void saveData();

    Map getData();
}

}