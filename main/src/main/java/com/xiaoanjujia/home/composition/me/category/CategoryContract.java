package com.xiaoanjujia.home.composition.me.category;


import com.xiaoanjujia.home.entities.ComcateListsResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface CategoryContract {

    interface View {


        void setResponseData(ComcateListsResponse mComcateListsResponse);

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
