package com.xiaoanjujia.home.composition.tenement.supervisor;


import com.xiaoanjujia.home.entities.LoginResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface SupervisorContract {

    interface View {


        void setResponseData(LoginResponse loginResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
