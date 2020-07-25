package com.xiaoanjujia.home.composition.login.code_login;


import com.xiaoanjujia.home.entities.RegisterCodeResponse;
import com.xiaoanjujia.home.entities.RegisterResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/9/14 23:30
 * @Description:
 */
public interface CodeLoginContract {
    interface View {


        void setResponseData(RegisterResponse registerResponse);
        void setCodeResponseData(RegisterCodeResponse registerResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getCodeRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
