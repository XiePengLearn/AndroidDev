package com.xiaoanjujia.home.composition.community;


import com.xiaoanjujia.home.entities.CommunityDetailsResponse;
import com.xiaoanjujia.home.entities.LogExamineResponse;
import com.xiaoanjujia.home.entities.LogRefuseResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface CompositionDetailContract {

    interface View {


        void setResponseData(CommunityDetailsResponse mCommunityDetailsResponse);

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
