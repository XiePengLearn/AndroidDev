package com.xiaoanjujia.home.composition.unlocking.visitor_invitation;


import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.home.entities.UploadImageResponse;
import com.xiaoanjujia.home.entities.VisitorFaceScoreResponse;
import com.xiaoanjujia.home.entities.VisitorInvitationResponse;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface VisitorInvitationContract {

    interface View {


        void setResponseData(VisitorInvitationResponse mVisitorInvitationResponse);
        void setUploadPicture(UploadImageResponse uploadImageResponse);
        void setFaceScoreData(VisitorFaceScoreResponse mVisitorFaceScoreResponsee);
        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getUploadPicture(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList);
        void getFaceScoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
