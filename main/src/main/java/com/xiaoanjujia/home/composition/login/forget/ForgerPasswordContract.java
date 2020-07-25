package com.xiaoanjujia.home.composition.login.forget;


import com.xiaoanjujia.home.entities.ForgerResponse;
import com.xiaoanjujia.home.entities.RegisterCodeResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/9/14 23:30
 * @Description:
 */
public interface ForgerPasswordContract {
    interface View {


        void setResponseData(ForgerResponse forgerResponse);
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
