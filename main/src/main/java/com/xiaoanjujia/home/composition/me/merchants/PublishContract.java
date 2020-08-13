package com.xiaoanjujia.home.composition.me.merchants;


import com.xiaoanjujia.home.entities.FeedBackResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface PublishContract {

    interface View {


        void setResponseData(FeedBackResponse feedBackResponse);

        void setUploadImage(UploadImageResponse uploadImageResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getUploadImage(TreeMap<String, String> headers, File file_name);
    }
}
