package com.xiaoanjujia.home.composition.unlocking.reservation_record;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.AppointmentRecordsResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.VisitingRecordsResponse;

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
public class ReservationRecordPresenter extends BasePresenter implements ReservationRecordContract.Presenter {
    private MainDataManager mDataManager;
    private ReservationRecordContract.View mContractView;
    private static final String TAG = "ChangeAuthenticationPresenter";

    @Inject
    public ReservationRecordPresenter(MainDataManager mDataManager, ReservationRecordContract.View view) {
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
        Disposable disposable = mDataManager.getAppointmentRecords(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private AppointmentRecordsResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, AppointmentRecordsResponse.class);
                    } else {
                        mDataResponse = new AppointmentRecordsResponse();
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
        Disposable disposable = mDataManager.getAppointmentRecords(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private AppointmentRecordsResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, AppointmentRecordsResponse.class);
                    } else {
                        mDataResponse = new AppointmentRecordsResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatusString(response));
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

    @Override
    public void getLaiFangData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getVisitingRecords(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private VisitingRecordsResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, VisitingRecordsResponse.class);
                    } else {
                        mDataResponse = new VisitingRecordsResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.setLaiFangData(mDataResponse);
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
    public void getLaiFangMoreData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getVisitingRecords(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private VisitingRecordsResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, VisitingRecordsResponse.class);
                    } else {
                        mDataResponse = new VisitingRecordsResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.setLaiFangMoreData(mDataResponse);
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
