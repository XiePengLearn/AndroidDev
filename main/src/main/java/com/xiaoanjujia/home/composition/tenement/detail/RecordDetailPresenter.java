package com.xiaoanjujia.home.composition.tenement.detail;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.LogDetailsResponse;
import com.xiaoanjujia.home.entities.LogExamineResponse;
import com.xiaoanjujia.home.entities.LogRefuseResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description: ChangeAuthenticationPresenter
 */
public class RecordDetailPresenter extends BasePresenter implements RecordDetailContract.Presenter {
    private MainDataManager mDataManager;
    private RecordDetailContract.View mContractView;
    private static final String TAG = "ChangeAuthenticationPresenter";

    @Inject
    public RecordDetailPresenter(MainDataManager mDataManager, RecordDetailContract.View view) {
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
    public void getRequestData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getLogDetails(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private LogDetailsResponse mLogDetailsResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response+"---mapParameters---:"+mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mLogDetailsResponse = gson.fromJson(response, LogDetailsResponse.class);
                    } else {
                        mLogDetailsResponse = new LogDetailsResponse();
                        mLogDetailsResponse.setMessage(ProjectResponse.getMessage(response));
                        mLogDetailsResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setResponseData(mLogDetailsResponse);
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

    @Override
    public void getLogRefuseData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getLogRefuse(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private LogRefuseResponse mLogRefuseResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response+"---mapParameters---:"+mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mLogRefuseResponse = gson.fromJson(response, LogRefuseResponse.class);
                    } else {
                        mLogRefuseResponse = new LogRefuseResponse();
                        mLogRefuseResponse.setMessage(ProjectResponse.getMessage(response));
                        mLogRefuseResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setLogRefuseData(mLogRefuseResponse);
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


    @Override
    public void getLogExamineData(TreeMap<String, String> headers, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getLogExamine(headers, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private LogExamineResponse mLogExamineResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response+"---mapParameters---:"+mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mLogExamineResponse = gson.fromJson(response, LogExamineResponse.class);
                    } else {
                        mLogExamineResponse = new LogExamineResponse();
                        mLogExamineResponse.setMessage(ProjectResponse.getMessage(response));
                        mLogExamineResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setLogExamineData(mLogExamineResponse);
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
