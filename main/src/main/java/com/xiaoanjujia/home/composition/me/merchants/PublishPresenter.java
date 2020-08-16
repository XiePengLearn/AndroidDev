package com.xiaoanjujia.home.composition.me.merchants;

import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.FeedBackResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
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
    private PublishContract.View mContractView;
    private static final String TAG = "PublishPresenter";

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
        //        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getFeedBackData(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            private FeedBackResponse mfeedBackResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonArrayData = ProjectResponse.isJsonArrayData(response);
                    if (jsonArrayData) {
                        mfeedBackResponse = gson.fromJson(response, FeedBackResponse.class);
                    } else {
                        mfeedBackResponse = new FeedBackResponse();
                        mfeedBackResponse.setMessage(ProjectResponse.getMessage(response));
                        mfeedBackResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setResponseData(mfeedBackResponse);
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
                LogUtil.e(TAG, "=======onError:======= ");
                mfeedBackResponse = new FeedBackResponse();
                mfeedBackResponse.setMessage("商户认证数据提交失败......");
                mfeedBackResponse.setStatus(0);
                mContractView.setResponseData(mfeedBackResponse);
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
    public void getUploadImage(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList) {
        /**
         * 公共上传图片
         * header参数每次都要携带
         * 1.接口地址 /api/image
         * image[]
         * img_path( 返回字符串,多张图片是用逗号隔开 ):路径
         * {
         *     "status": 1,
         *     "message": "OK",
         *     "data": {
         *         "path": "https://a.xiaoanjujia.com/uploads/images/2020/07-21/b104c02321833ac7dcc323539f5a2ebe.jpg,https://a.xiaoanjujia.com/uploads/images/2020/07-21/ea5b207875b7f9d49b3c5094d07bb8b0.jpg"
         *     }
         * }
         */
        //        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        List<File> imageFile = new ArrayList<>();
        for (int i = 0; i < LocalMediaList.size(); i++) {
            imageFile.add(new File(LocalMediaList.get(i).getCompressPath()));
        }
        List<MultipartBody.Part> partList = filesToMultipartBodyParts(imageFile);
        Disposable disposable = mDataManager.executePostImageHeader(headers, null, partList, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    UploadImageResponse mUploadImageResponse = gson.fromJson(response, UploadImageResponse.class);

                    mContractView.setUploadImage(mUploadImageResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //                mContractView.hiddenProgressDialogView();
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //                mContractView.hiddenProgressDialogView();
                LogUtil.e(TAG, "=======onError:======= ");
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                //                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

    @Override
    public void getUploadPicture(TreeMap<String, String> headers, List<LocalMedia> LocalMediaList) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        List<File> imageFile = new ArrayList<>();
        for (int i = 0; i < LocalMediaList.size(); i++) {
            imageFile.add(new File(LocalMediaList.get(i).getCompressPath()));
        }
        List<MultipartBody.Part> partList = filesToMultipartBodyParts(imageFile);
        Disposable disposable = mDataManager.executePostImageHeader(headers, null, partList, new ErrorDisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    UploadImageResponse mUploadImageResponse = gson.fromJson(response, UploadImageResponse.class);

                    mContractView.setUploadPicture(mUploadImageResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //                mContractView.hiddenProgressDialogView();
            }

            //如果需要发生Error时操作UI可以重写onError，统一错误操作可以在ErrorDisposableObserver中统一执行
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                //                mContractView.hiddenProgressDialogView();
                LogUtil.e(TAG, "=======onError:======= ");
            }

            @Override
            public void onComplete() {
                long completeRequestTime = System.currentTimeMillis();
                long useTime = completeRequestTime - beforeRequestTime;
                LogUtil.e(TAG, "=======onCompleteUseMillisecondTime:======= " + useTime + "  ms");
                //                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

    private RequestBody convertToRequestBody(String param) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), param);
        return requestBody;
    }

    private List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image[]", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
}
