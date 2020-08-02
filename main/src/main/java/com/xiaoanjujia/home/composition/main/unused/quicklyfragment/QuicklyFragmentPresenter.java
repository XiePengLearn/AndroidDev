package com.xiaoanjujia.home.composition.main.unused.quicklyfragment;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description:
 */
public class QuicklyFragmentPresenter extends BasePresenter implements QuicklyFragmentContract.Presenter {

    private MainDataManager mDataManager;

    private QuicklyFragmentContract.View mContractView;
    private static final String TAG = "BeforePagePresenter";

    @Inject
    public QuicklyFragmentPresenter(MainDataManager mDataManager, QuicklyFragmentContract.View view) {
        this.mDataManager = mDataManager;
        this.mContractView = view;

    }

    @Override
    public void destory() {
        if (disposables != null) {
            disposables.clear();
        }
    }

    @Override
    public void saveData() {

    }

    @Override
    public Map getData() {
        return null;
    }

    @Override
    public void getRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getLoginData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private LoginResponse mLoginResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mLoginResponse = gson.fromJson(response, LoginResponse.class);
                    } else {
                        mLoginResponse = new LoginResponse();
                        mLoginResponse.setMessage(ProjectResponse.getMessage(response));
                        mLoginResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setResponseData(mLoginResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mContractView.hiddenProgressDialogView();
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mContractView.hiddenProgressDialogView();
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

}
