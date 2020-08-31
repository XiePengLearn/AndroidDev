package com.xiaoanjujia.home.composition.tenement.quary_detail;


import com.xiaoanjujia.home.entities.LogDetailsResponse;
import com.xiaoanjujia.home.entities.LogExamineResponse;
import com.xiaoanjujia.home.entities.LogRefuseResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface QuaryDetailContract {

    interface View {


        void setResponseData(LogDetailsResponse mLogDetailsResponse);

        void setLogExamineData(LogExamineResponse mLogExamineResponse);

        void setLogRefuseData(LogRefuseResponse mLogRefuseResponse);


        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();


        void getLogExamineData(TreeMap<String, String> headers, Map<String, Object> mapParameters);

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getLogRefuseData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
