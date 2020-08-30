package com.xiaoanjujia.home.composition.me.post_message;


import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.home.entities.FeedBackResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface PostMessageContract {

    interface View {


        void setResponseData(FeedBackResponse feedBackResponse);

        void setUploadImage(UploadImageResponse uploadImageResponse);

        void setUploadPicture(UploadImageResponse uploadImageResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getUploadImage(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList);

        void getUploadPicture(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList);
    }
}
