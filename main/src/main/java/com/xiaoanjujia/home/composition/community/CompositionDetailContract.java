package com.xiaoanjujia.home.composition.community;


import com.xiaoanjujia.home.entities.CommentDetailsResponse;
import com.xiaoanjujia.home.entities.CommentListResponse;
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

        void setCommentDetailsData(CommentListResponse mCommentListResponse);

        void setCommentPublish(CommentPublishResponse mCommentPublishResponse);

        void setCommentLike(CommentPublishResponse mCommentPublishResponse);

        void setAddVisit(CommentPublishResponse mCommentPublishResponse);

        void setAddContact(CommentPublishResponse mCommentPublishResponse);

        void setActionCheckBonus(CommentPublishResponse mCommentPublishResponse);

        void setWatchBonus(CommentPublishResponse mCommentPublishResponse);

        void setMoreData(CommentListResponse mCommentListResponse);

        void setCommentCount(CommentDetailsResponse mCommentDetailsResponse);

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

        void getCommentLike(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getAddVisit(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getAddContact(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getActionCheckBonus(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getWatchBonus(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getCommentCount(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);
    }
}
