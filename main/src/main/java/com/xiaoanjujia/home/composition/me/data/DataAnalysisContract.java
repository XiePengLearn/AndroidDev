package com.xiaoanjujia.home.composition.me.data;


import com.xiaoanjujia.home.entities.DataAnalysisResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface DataAnalysisContract {

    interface View {


        void setResponseData(DataAnalysisResponse mDataAnalysisResponse);

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
