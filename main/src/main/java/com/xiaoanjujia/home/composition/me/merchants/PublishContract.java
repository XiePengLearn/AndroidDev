package com.xiaoanjujia.home.composition.me.merchants;


import java.io.File;
import java.util.Map;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:10
 * @Description:
 */
public interface PublishContract {

    interface View {


        void setResponseData(FeedBackResponse feedBackResponse);
        void setUploadImage(UploadImageResponse uploadImageResponse);
        void setUploadVideo(UploadVideoResponse uploadVideoResponse);
        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(Map<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getUploadImage(Map<String, String> headers, File file_name);
        void getUploadVideo(Map<String, String> headers, File file_name);
    }
}
