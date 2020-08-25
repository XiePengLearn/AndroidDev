package com.xiaoanjujia.home.composition.community;


import com.xiaoanjujia.home.entities.CommentDetailsResponse;
import com.xiaoanjujia.home.entities.CommentPublishResponse;
import com.xiaoanjujia.home.entities.CommunityDetailsResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface CompositionDetailContract {

    interface View {


        void setResponseData(CommunityDetailsResponse mCommunityDetailsResponse);

        void setCommentDetailsData(CommentDetailsResponse mCommentDetailsResponse);

        void setCommentPublish(CommentPublishResponse mCommentPublishResponse);

        void setMoreData(CommentDetailsResponse mCommentDetailsResponse);

        void showProgressDialogView();

        void hiddenProgressDialogView();
    }

    interface Presenter {


        void destory();

        void saveData();

        Map getData();


        void getCommentDetailsData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getMoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getCommentPublish(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
