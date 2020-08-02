package com.xiaoanjujia.home.composition.login.code_login;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.RegisterCodeResponse;
import com.xiaoanjujia.home.entities.RegisterResponse;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * @Auther: xp
 * @Date: 2019/9/15 06:43
 * @Description:
 */
public class CodeLoginPresenter extends BasePresenter implements CodeLoginContract.Presenter {
    private MainDataManager mDataManager;
    private              CodeLoginContract.View mLoginView;
    private static final String                TAG = "ForgerPasswordPresenter";

    @Inject
    public CodeLoginPresenter(MainDataManager mDataManager, CodeLoginContract.View view) {
        this.mDataManager = mDataManager;
        this.mLoginView = view;

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
        mLoginView.showProgressDialogView();
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
                    mLoginView.setResponseData(mLoginResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mLoginView.hiddenProgressDialogView();
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime+"  ms");
                mLoginView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

    @Override
    public void getCodeRequestData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mLoginView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getRegisretCodeData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private RegisterCodeResponse mRegisterCodeResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean isJsonArrayData = ProjectResponse.isJsonArrayData(response);
                    if (isJsonArrayData) {
                        mRegisterCodeResponse = gson.fromJson(response, RegisterCodeResponse.class);
                    } else {
                        mRegisterCodeResponse = new RegisterCodeResponse();
                        mRegisterCodeResponse.setMessage(ProjectResponse.getMessage(response));
                        mRegisterCodeResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mLoginView.setCodeResponseData(mRegisterCodeResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mLoginView.hiddenProgressDialogView();
                LogUtil.e(TAG, "=======onError:======= " + e.toString());
                if(e instanceof HttpException){
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();

                    try {
                        if(responseBody != null){
                            responseBody.toString();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime+"  ms");
                mLoginView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }
}
