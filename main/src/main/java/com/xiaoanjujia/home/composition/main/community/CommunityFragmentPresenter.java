package com.xiaoanjujia.home.composition.main.community;

import com.google.gson.Gson;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.ComcateListsResponse;
import com.xiaoanjujia.home.entities.CommunitySearchResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.StoreHot2Response;
import com.xiaoanjujia.home.entities.StoreHotMoreResponse;
import com.xiaoanjujia.home.entities.StoreHotResponse;

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
public class CommunityFragmentPresenter extends BasePresenter implements CommunityFragmentContract.Presenter {

    private MainDataManager mDataManager;

    private CommunityFragmentContract.View mContractView;
    private static final String TAG = "BeforePagePresenter";

    @Inject
    public CommunityFragmentPresenter(MainDataManager mDataManager, CommunityFragmentContract.View view) {
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
        Disposable disposable = mDataManager.getOrderLists(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private CommunitySearchResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, CommunitySearchResponse.class);
                    } else {
                        mDataResponse = new CommunitySearchResponse();
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
        Disposable disposable = mDataManager.getComcateLists(headers, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private ComcateListsResponse mComcateListsResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mComcateListsResponse = gson.fromJson(response, ComcateListsResponse.class);
                    } else {
                        mComcateListsResponse = new ComcateListsResponse();
                        mComcateListsResponse.setMessage(ProjectResponse.getMessage(response));
                        mComcateListsResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setTypesOfRoleData(mComcateListsResponse);
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
        Disposable disposable = mDataManager.getOrderLists(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private CommunitySearchResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, CommunitySearchResponse.class);
                    } else {
                        mDataResponse = new CommunitySearchResponse();
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

    @Override
    public void getStoreHotData(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getCommuhotSpot(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private StoreHotResponse mDateResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDateResponse = gson.fromJson(response, StoreHotResponse.class);
                    } else {
                        mDateResponse = new StoreHotResponse();
                        mDateResponse.setMessage(ProjectResponse.getMessage(response));
                        mDateResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setStoreHotData(mDateResponse);
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
    public void getStoreHot2Data(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {

        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getCommuhotSpot(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private StoreHot2Response mDateResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDateResponse = gson.fromJson(response, StoreHot2Response.class);
                    } else {
                        mDateResponse = new StoreHot2Response();
                        mDateResponse.setMessage(ProjectResponse.getMessage(response));
                        mDateResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setStoreHot2Data(mDateResponse);
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
    public void getStoreHotDataMore(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getComhotSpotList(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private StoreHotMoreResponse mDateResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDateResponse = gson.fromJson(response, StoreHotMoreResponse.class);
                    } else {
                        mDateResponse = new StoreHotMoreResponse();
                        mDateResponse.setMessage(ProjectResponse.getMessage(response));
                        mDateResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setStoreHotDataMore(mDateResponse);
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
    public void getCommunitySearch(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getCommunitySearch(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private CommunitySearchResponse mDateResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDateResponse = gson.fromJson(response, CommunitySearchResponse.class);
                    } else {
                        mDateResponse = new CommunitySearchResponse();
                        mDateResponse.setMessage(ProjectResponse.getMessage(response));
                        mDateResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setCommunitySearch(mDateResponse);
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
    public void getCommunityList(TreeMap<String, String> mapHeaders, final Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getOrderLists(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private CommunitySearchResponse mDateResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDateResponse = gson.fromJson(response, CommunitySearchResponse.class);
                    } else {
                        mDateResponse = new CommunitySearchResponse();
                        mDateResponse.setMessage(ProjectResponse.getMessage(response));
                        mDateResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setCommunityList(mDateResponse);
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
