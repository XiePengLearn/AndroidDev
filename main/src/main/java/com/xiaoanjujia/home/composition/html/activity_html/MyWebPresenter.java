package com.xiaoanjujia.home.composition.html.activity_html;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.ComExamineStatusResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:09
 * @Description:
 */
public class MyWebPresenter extends BasePresenter implements MyWebContract.Presenter {
    private MainDataManager mDataManager;
    private              MyWebContract.View mContractView;
    private static final String               TAG = "MainPresenter";

    @Inject
    public MyWebPresenter(MainDataManager mDataManager, MyWebContract.View view) {
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
        Disposable disposable = mDataManager.getComexamine(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            private ComExamineStatusResponse mComExamineStatusResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mComExamineStatusResponse = gson.fromJson(response, ComExamineStatusResponse.class);
                    } else {
                        mComExamineStatusResponse = new ComExamineStatusResponse();
                        mComExamineStatusResponse.setMessage(ProjectResponse.getMessage(response));
                        mComExamineStatusResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setResponseData(mComExamineStatusResponse);
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
                LogUtil.e(TAG, "=======onError:======= " + e.toString());
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
