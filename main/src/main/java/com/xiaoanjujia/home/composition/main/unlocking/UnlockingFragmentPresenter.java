package com.xiaoanjujia.home.composition.main.unlocking;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.AppUpdateResponse;
import com.xiaoanjujia.home.entities.ChangeAccountResponse;
import com.xiaoanjujia.home.entities.PhoneResponse;
import com.xiaoanjujia.home.entities.ProDisplayDataResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

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
public class UnlockingFragmentPresenter extends BasePresenter implements UnlockingFragmentContract.Presenter {

    private MainDataManager mDataManager;

    private UnlockingFragmentContract.View mContractView;
    private static final String TAG = "BeforePagePresenter";

    @Inject
    public UnlockingFragmentPresenter(MainDataManager mDataManager, UnlockingFragmentContract.View view) {
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
        Disposable disposable = mDataManager.getVisitorPersonInfo(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            private VisitorPersonInfoResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, VisitorPersonInfoResponse.class);
                    } else {
                        mDataResponse = new VisitorPersonInfoResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.setResponseData(mDataResponse);
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

    @Override
    public void getGetPhoneData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getProPhone(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private PhoneResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, PhoneResponse.class);
                    } else {
                        mDataResponse = new PhoneResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setGetPhoneData(mDataResponse);
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
    public void getGetProDisplayData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getProDisplay(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private ProDisplayDataResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isStringData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, ProDisplayDataResponse.class);
                    } else {
                        mDataResponse = new ProDisplayDataResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setGetProDisplayData(mDataResponse);
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
    public void getRequestUpdateData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getAppUpdate(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private AppUpdateResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, AppUpdateResponse.class);
                    } else {
                        mDataResponse = new AppUpdateResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setResponseUpdateData(mDataResponse);
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
    public void getResponseChangeAccount(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getAppChange(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private ChangeAccountResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isStringData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, ChangeAccountResponse.class);
                    } else {
                        mDataResponse = new ChangeAccountResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setResponseChangeAccount(mDataResponse);
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
