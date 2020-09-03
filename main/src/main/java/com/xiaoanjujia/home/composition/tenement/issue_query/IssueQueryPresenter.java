package com.xiaoanjujia.home.composition.tenement.issue_query;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.PropertyManagementListLogResponse;
import com.xiaoanjujia.home.entities.TypeOfRoleResponse;

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
public class IssueQueryPresenter extends BasePresenter implements IssueQueryContract.Presenter {
    private MainDataManager mDataManager;
    private              IssueQueryContract.View mContractView;
    private static final String               TAG = "ChangeAuthenticationPresenter";

    @Inject
    public IssueQueryPresenter(MainDataManager mDataManager, IssueQueryContract.View view) {
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
        Disposable disposable = mDataManager.getOrdinaryLogLists(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private PropertyManagementListLogResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response+"---mapParameters---:"+mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, PropertyManagementListLogResponse.class);
                    } else {
                        mDataResponse = new PropertyManagementListLogResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatus(response));
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
    public void getTypesOfRoleData(TreeMap<String, String> headers, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getTypeOfRole(headers, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private TypeOfRoleResponse mTypeOfRoleResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response+"---mapParameters---:"+mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mTypeOfRoleResponse = gson.fromJson(response, TypeOfRoleResponse.class);
                    } else {
                        mTypeOfRoleResponse = new TypeOfRoleResponse();
                        mTypeOfRoleResponse.setMessage(ProjectResponse.getMessage(response));
                        mTypeOfRoleResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setTypesOfRoleData(mTypeOfRoleResponse);
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
    public void getMoreData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getOrdinaryLogLists(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private PropertyManagementListLogResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response+"---mapParameters---:"+mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, PropertyManagementListLogResponse.class);
                    } else {
                        mDataResponse = new PropertyManagementListLogResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setMoreData(mDataResponse);
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
