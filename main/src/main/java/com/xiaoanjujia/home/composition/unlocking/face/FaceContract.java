package com.xiaoanjujia.home.composition.unlocking.face;


import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.home.entities.AddFaceResponse;
import com.xiaoanjujia.home.entities.QueryFaceResponse;
import com.xiaoanjujia.home.entities.UpdateFaceResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;
import com.xiaoanjujia.home.entities.VisitorFaceScoreResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface FaceContract {

    interface View {

        void setPersonalInformationData(VisitorPersonInfoResponse mVisitorPersonInfoResponse);
        void setFaceScoreData(VisitorFaceScoreResponse mVisitorFaceScoreResponsee);
        void setUploadPicture(UploadImageResponse uploadImageResponse);
        void setAddFace(AddFaceResponse mAddFaceResponse);
        void setUpdateFace(UpdateFaceResponse mUpdateFaceResponse);
        void seQueryFace(QueryFaceResponse mQueryFaceResponse);
        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();
        void getPersonalInformationData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getFaceScoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
        void getUploadPicture(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList);
        void getAddFace(TreeMap<String, String> headers, Map<String, Object> mapParameters);
        void getUpdateFace(TreeMap<String, String> headers, Map<String, Object> mapParameters);
        void getQueryFace(TreeMap<String, String> headers, Map<String, Object> mapParameters);
    }
}
