package com.xiaoanjujia.home.composition.unlocking.house_manager;


import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface HouseManagerContract {

    interface View {


        void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse);

        void setMoreData(VisitorPersonInfoResponse mVisitorPersonInfoResponse);
        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getMoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
