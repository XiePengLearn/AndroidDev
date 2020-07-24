package com.xiaoanjujia.home.composition.main.findfragment;

import com.xiaoanjujia.home.entities.FindsBean;

/**
 * @author xp
 */

public interface FindContract {
    interface View {
        void setFindData(FindsBean find);
        void setMoreData(FindsBean find);
    }

    interface Presenter {
        void getFindData();
        void getMoreFindData();
    }

}