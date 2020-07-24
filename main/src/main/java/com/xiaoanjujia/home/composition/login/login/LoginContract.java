package com.xiaoanjujia.home.composition.login.login;

import com.xiaoanjujia.home.entities.LoginResponse;

import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface LoginContract {

    interface View {


        void setLoginData(LoginResponse loginResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getLoginData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
