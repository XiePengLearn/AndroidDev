package com.xiaoanjujia.home.composition.tenement.issue_query;


import com.xiaoanjujia.home.entities.PropertyManagementListLogResponse;
import com.xiaoanjujia.home.entities.TypeOfRoleResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface IssueQueryContract {

    interface View {


        void setResponseData(PropertyManagementListLogResponse mPropertyManagementListLogResponse);
        void setMoreData(PropertyManagementListLogResponse mPropertyManagementListLogResponse);
        void setTypesOfRoleData(TypeOfRoleResponse mTypeOfRoleResponse);
        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getTypesOfRoleData(TreeMap<String, String> headers, Map<String, Object> mapParameters);
        void getMoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
