package com.xiaoanjujia.home.composition.tenement.staff;

import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.rxjava.ErrorDisposableObserver;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.BasePresenter;
import com.xiaoanjujia.home.entities.AddPropertyLogResponse;
import com.xiaoanjujia.home.entities.ProjectResponse;
import com.xiaoanjujia.home.entities.TypeOfRoleResponse;
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
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description: ChangeAuthenticationPresenter
 */
public class StaffPresenter extends BasePresenter implements StaffContract.Presenter {
    private MainDataManager mDataManager;
    private StaffContract.View mContractView;
    private static final String TAG = "ChangeAuthenticationPresenter";

    @Inject
    public StaffPresenter(MainDataManager mDataManager, StaffContract.View view) {
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
        Disposable disposable = mDataManager.getAddPropertyLog(mapHeaders, mapParameters, new ErrorDisposableObserver<ResponseBody>() {

            private AddPropertyLogResponse mAddPropertyLogResponse;

            @Override
            public void onNext(ResponseBody responseBody) {
                try {

                    String response = responseBody.string();
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
                    Gson gson = new Gson();
                    boolean jsonObjectData = ProjectResponse.isJsonObjectData(response);
                    if (jsonObjectData) {
                        mAddPropertyLogResponse = gson.fromJson(response, AddPropertyLogResponse.class);
                    } else {
                        mAddPropertyLogResponse = new AddPropertyLogResponse();
                        mAddPropertyLogResponse.setMessage(ProjectResponse.getMessage(response));
                        mAddPropertyLogResponse.setStatus(ProjectResponse.getStatus(response));
                    }
                    mContractView.setResponseData(mAddPropertyLogResponse);
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
    public void getUploadImage(final TreeMap<String, String> headers, final List<LocalMedia> LocalMediaList) {
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
        mContractView.showProgressDialogView();
        final long beforeRequestTime = System.currentTimeMillis();
        final List<File> imageFile = new ArrayList<>();
        final List<File> lubanImageFile = new ArrayList<>();
        for (int i = 0; i < LocalMediaList.size(); i++) {
            imageFile.add(new File(LocalMediaList.get(i).getPath()));
        }
        Luban.with(BaseApplication.getInstance())
                .load(imageFile)
                .ignoreBy(100)
                .setTargetDir(getPath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.e(TAG, file.length() / 1024 + "k");
                        lubanImageFile.add(file);
                        if (LocalMediaList.size() == lubanImageFile.size()) {
                            // 执行文件上传
                            List<MultipartBody.Part> partList = filesToMultipartBodyParts(lubanImageFile);
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
                                    mContractView.hiddenProgressDialogView();
                                    LogUtil.e(TAG, "=======onError:======= " + e.toString());
                                    ToastUtil.showToast(BaseApplication.getInstance(), "图片上传报错");
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.showToast(BaseApplication.getInstance(), "图片在压缩过程中报错");
                        mContractView.hiddenProgressDialogView();
                    }
                }).launch();


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
                    LogUtil.e(TAG, "=======response:=======" + response + "---mapParameters---:" + mapParameters.toString());
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

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}
