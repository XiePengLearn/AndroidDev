package com.xiaoanjujia.home.composition.success.modification;

import com.xiaoanjujia.home.entities.LoginResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface ModificationSuccessContract {

    interface View {


        void setLoginData(LoginResponse loginResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getLoginData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
