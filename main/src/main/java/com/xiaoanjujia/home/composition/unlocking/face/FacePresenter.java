package com.xiaoanjujia.home.composition.unlocking.face;

import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.AddFaceResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.QueryFaceResponse;
import com.xiaoanjujia.home.entities.UpdateFaceResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;
import com.xiaoanjujia.home.entities.VisitorFaceScoreResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;

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
 * @Date: 2019/10
 * @Description: ChangeAuthenticationPresenter
 */
public class FacePresenter extends BasePresenter implements FaceContract.Presenter {
    private MainDataManager mDataManager;
    private FaceContract.View mContractView;
    private static final String TAG = "ChangeAuthenticationPresenter";

    @Inject
    public FacePresenter(MainDataManager mDataManager, FaceContract.View view) {
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
    public void getPersonalInformationData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getVisitorPersonInfo(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private VisitorPersonInfoResponse mLoginResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mLoginResponse = gson.fromJson(response, VisitorPersonInfoResponse.class);
                    } else {
                        mLoginResponse = new VisitorPersonInfoResponse();
                        mLoginResponse.setMessage(ProjectResponse.getMessage(response));
                        mLoginResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.setPersonalInformationData(mLoginResponse);
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
    public void getFaceScoreData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getFaceCheckFace(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private VisitorFaceScoreResponse mLoginResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mLoginResponse = gson.fromJson(response, VisitorFaceScoreResponse.class);
                    } else {
                        mLoginResponse = new VisitorFaceScoreResponse();
                        mLoginResponse.setMessage(ProjectResponse.getMessage(response));
                        mLoginResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.setFaceScoreData(mLoginResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.showToast(BaseApplication.getInstance(),"人脸检测失败,请更换人脸照片");
                }

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
                                mContractView.hiddenProgressDialogView();
                                ToastUtil.showToast(BaseApplication.getInstance(),"上传图片失败");
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
    public void getAddFace(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getFaceAddFace(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            private AddFaceResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, AddFaceResponse.class);
                    } else {
                        mDataResponse = new AddFaceResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.setAddFace(mDataResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                mContractView.hiddenProgressDialogView();
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
//                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

    @Override
    public void getUpdateFace(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getFaceUpdateFace(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            private UpdateFaceResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, UpdateFaceResponse.class);
                    } else {
                        mDataResponse = new UpdateFaceResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.setUpdateFace(mDataResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                mContractView.hiddenProgressDialogView();
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
//                mContractView.hiddenProgressDialogView();
            }
        });
        addDisposabe(disposable);
    }

    @Override
    public void getQueryFace(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        Disposable disposable = mDataManager.getFaceQueryFace(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {
            private QueryFaceResponse mDataResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response);
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonArrayData(response);
                    if (jsonObjectData) {
                        mDataResponse = gson.fromJson(response, QueryFaceResponse.class);
                    } else {
                        mDataResponse = new QueryFaceResponse();
                        mDataResponse.setMessage(ProjectResponse.getMessage(response));
                        mDataResponse.setStatus(ProjectResponse.getStatusString(response));
                    }
                    mContractView.seQueryFace(mDataResponse);
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
