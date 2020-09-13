package com.xiaoanjujia.home.composition.unlocking.select_housing;


import com.xiaoanjujia.home.entities.SelectHousingResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface SelectHousingContract {

    interface View {


        void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse);

        void setSelectHousingData(SelectHousingResponse mSelectHousingResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getSelectHousingData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
