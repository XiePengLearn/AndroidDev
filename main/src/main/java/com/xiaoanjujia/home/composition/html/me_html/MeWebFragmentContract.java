package com.xiaoanjujia.home.composition.html.me_html;


import com.xiaoanjujia.home.entities.ComExamineStatusResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface MeWebFragmentContract {

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
