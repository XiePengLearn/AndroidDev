package com.xiaoanjujia.home.composition.main.unlocking;


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
