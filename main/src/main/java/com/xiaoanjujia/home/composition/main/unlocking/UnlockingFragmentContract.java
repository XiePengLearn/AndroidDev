package com.xiaoanjujia.home.composition.main.unlocking;


import com.xiaoanjujia.home.entities.PhoneResponse;
import com.xiaoanjujia.home.entities.ProDisplayDataResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface UnlockingFragmentContract {

    interface View {


        void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse);
        void setGetPhoneData(PhoneResponse mPhoneResponse);
        void setGetProDisplayData(ProDisplayDataResponse mProDisplayDataResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getGetPhoneData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getGetProDisplayData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

    }
}
