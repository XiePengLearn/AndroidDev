package com.xiaoanjujia.home.composition.html.activity_html;


import com.xiaoanjujia.home.entities.ComExamineStatusResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface MyWebContract {

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
