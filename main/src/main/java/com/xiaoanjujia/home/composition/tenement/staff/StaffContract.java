package com.xiaoanjujia.home.composition.tenement.staff;


import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.home.entities.AddPropertyLogResponse;
import com.xiaoanjujia.home.entities.TypeOfRoleResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface StaffContract {

    interface View {


        void setResponseData(AddPropertyLogResponse mAddPropertyLogResponse);

        void setTypesOfRoleData(TypeOfRoleResponse mTypeOfRoleResponse);

        void setUploadImage(UploadImageResponse uploadImageResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getUploadImage(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList);

        void getTypesOfRoleData(TreeMap<String, String> headers, Map<String, Object> mapParameters);

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
