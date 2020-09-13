package com.xiaoanjujia.home.composition.unlocking.add_personal_information;


import com.xiaoanjujia.home.entities.RootAreaResponse;
import com.xiaoanjujia.home.entities.RootNextRegionResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface AddPersonalInformationContract {

    interface View {


        void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse);

        void setRootAreaData(RootAreaResponse mRootAreaResponse);

        void setRootNextRegionData(RootNextRegionResponse mRootNextRegionResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getRootAreaData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getRootNextRegionData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
