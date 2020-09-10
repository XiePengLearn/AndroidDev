package com.xiaoanjujia.home.composition.unlocking.permit;


import com.xiaoanjujia.home.entities.PermitResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface PermitContract {

    interface View {


        void setResponseData(PermitResponse mPermitResponse);

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
