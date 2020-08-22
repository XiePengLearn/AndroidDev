package com.xiaoanjujia.home.composition.html.store_html;


import com.xiaoanjujia.home.entities.ComExamineStatusResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface StoreWebFragmentContract {

    interface View {


        void setResponseData(ComExamineStatusResponse mComExamineStatusResponse);

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
