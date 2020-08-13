package com.xiaoanjujia.home.composition.me.merchants;

import com.google.gson.Gson;
import com.xiaoanjujia.common.apiservice.RetrofitService;
import com.xiaoanjujia.common.apiservice.RetrofitServiceUtil;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.FeedBackResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Auther: xp
 * @Date: 2019/9/13 22:09
 * @Description: ChangeAuthenticationPresenter
 */
public class PublishPresenter extends BasePresenter implements PublishContract.Presenter {
    private MainDataManager mDataManager;
    private              PublishContract.View mContractView;
    private static final String               TAG = "ChangeAuthenticationPresenter";

    @Inject
    public PublishPresenter(MainDataManager mDataManager, PublishContract.View view) {
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
        Disposable disposable = mDataManager.getFeedBackData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    FeedBackResponse feedBackResponse = gson.fromJson(response, FeedBackResponse.class);

                    mContractView.setResponseData(feedBackResponse);
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
    public void getUploadImage(TreeMap<String, String> headers, File file_name) {

        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        RetrofitService retrofitService = RetrofitServiceUtil.getRetrofitService();
        RequestBody params = RequestBody.create(MediaType.parse("text/plain"), "png");
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/png/jpg; charset=utf-8"), file_name);
        MultipartBody.Part part = MultipartBody.Part.createFormData("IMAGE", file_name.getName(), requestBody);
        retrofitService.upload_avatar(headers, part, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {

                            String response = responseBody.string();
                            LogUtil.e(TAG, "=======response:=======" + response);
                            Gson gson = new Gson();
                            UploadImageResponse uploadImageResponse = gson.fromJson(response, UploadImageResponse.class);

                            mContractView.setUploadImage(uploadImageResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mContractView.hiddenProgressDialogView();
                    }

                    @Override
                    public void onComplete() {
                        long completeRequestTime = System.currentTimeMillis();
                        long useTime = completeRequestTime - beforeRequestTime;
                        LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");

                    }
                });
    }


}
