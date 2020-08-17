package com.xiaoanjujia.home.composition.tenement.staff;


import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.home.entities.LoginResponse;
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


        void setResponseData(LoginResponse loginResponse);
        void setUploadImage(UploadImageResponse uploadImageResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();
        void getUploadImage(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList);

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
