package com.xiaoanjujia.home.composition.main;


import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.entities.ProDisplayDataResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by admin on 2017/3/12.
 */

public interface MainContract {
    interface View {
        void setLoginData(LoginResponse loginResponse);

        void setGetProDisplayData(ProDisplayDataResponse mProDisplayDataResponse);
        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {

        void destory();

        void saveData();

        Map getData();
        void getLoginData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getGetProDisplayData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }

}