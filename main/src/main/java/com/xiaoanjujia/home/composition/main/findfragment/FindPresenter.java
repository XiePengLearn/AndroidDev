package com.xiaoanjujia.home.composition.main.findfragment;

import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.FindsBean;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by xp on 2017/3/22.
 */

public class FindPresenter extends BasePresenter implements FindContract.Presenter{
    private MainDataManager mDataManager;

    private FindContract.View mFindView;
    @Inject
    public FindPresenter(MainDataManager mDataManager, FindContract.View view) {
        this.mDataManager = mDataManager;
        this.mFindView = view;

    }


    @Override
    public void getFindData() {
        addDisposabe(mDataManager.getData(new ErrorDisposableObserver<FindsBean>() {
            @Override
            public void onNext(FindsBean findsBean) {
                mFindView.setFindData(findsBean);
            }

            @Override
            public void onComplete() {

            }
        },FindsBean.class, "find.txt"));
    }

    @Override
    public void getMoreFindData() {
        addDisposabe(mDataManager.getData(new DisposableObserver<FindsBean>() {
            @Override
            public void onNext(FindsBean findsBean) {
                mFindView.setMoreData(findsBean);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        },FindsBean.class, "find.txt"));
    }
}
