package com.xiaoanjujia.home.composition.main.community;


import com.xiaoanjujia.home.entities.ComcateListsResponse;
import com.xiaoanjujia.home.entities.CommunitySearchResponse;
import com.xiaoanjujia.home.entities.StoreHot2Response;
import com.xiaoanjujia.home.entities.StoreHotMoreResponse;
import com.xiaoanjujia.home.entities.StoreHotResponse;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public interface CommunityFragmentContract {

    interface View {


        void setResponseData(CommunitySearchResponse mCommunitySearchResponse);

        void setMoreData(CommunitySearchResponse mCommunitySearchResponse);

        void setTypesOfRoleData(ComcateListsResponse mComcateListsResponse);

        void setStoreHotData(StoreHotResponse mStoreHotResponse);

        void setStoreHot2Data(StoreHot2Response mStoreHot2Response);

        void setStoreHotDataMore(StoreHotMoreResponse mStoreHotMoreResponse);

        void setCommunitySearch(CommunitySearchResponse mCommunitySearchResponse);

        void setCommunityList(CommunitySearchResponse mCommunityListResponse);

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

        void getStoreHotData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getStoreHot2Data(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getStoreHotDataMore(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getCommunitySearch(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

        void getCommunityList(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters);

    }
}
