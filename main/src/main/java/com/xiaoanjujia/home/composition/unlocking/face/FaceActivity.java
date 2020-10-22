package com.xiaoanjujia.home.composition.unlocking.face;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.SelectPicPopupWindow;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.me.post_message.GlideEngine;
import com.xiaoanjujia.home.composition.unlocking.visitor_invitation.PlateNumberDialog;
import com.xiaoanjujia.home.entities.AddFaceResponse;
import com.xiaoanjujia.home.entities.QueryFaceResponse;
import com.xiaoanjujia.home.entities.UpdateFaceResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;
import com.xiaoanjujia.home.entities.VisitorFaceScoreResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;
import com.xiaoanjujia.home.tool.Api;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/faceActivity/faceActivity")
public class FaceActivity extends BaseActivity implements FaceContract.View {
    @Inject
    FacePresenter mPresenter;
    private static final String TAG = "PermitActivity";
    @BindView(R2.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R2.id.main_title_back)
    ImageView mainTitleBack;
    @BindView(R2.id.main_title_text)
    TextView mainTitleText;
    @BindView(R2.id.main_title_right)
    ImageView mainTitleRight;
    @BindView(R2.id.main_title_container)
    LinearLayout mainTitleContainer;
    @BindView(R2.id.huzhu_iv)
    ImageView huzhuIv;
    @BindView(R2.id.huzhu_tv)
    TextView huzhuTv;
    @BindView(R2.id.sex_img)
    ImageView sexImg;
    @BindView(R2.id.phone_tv)
    TextView phoneTv;
    @BindView(R2.id.id_card_tv)
    TextView idCardTv;
    @BindView(R2.id.uploading_special_certificate_iv)
    ImageView uploadingSpecialCertificateIv;
    @BindView(R2.id.uploading_special_certificate_rv)
    RecyclerView uploadingSpecialCertificateRv;
    @BindView(R2.id.face_pic_visibility)
    LinearLayout facePicVisibility;
    @BindView(R2.id.generate_visitor_card)
    AlphaButton generateVisitorCard;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;
    @BindView(R2.id.generate_visitor_uploading_face_pic)
    AlphaButton generateVisitorUploadingFacePic;


    private List<LocalMedia> selectList2 = new ArrayList<>();
    private int themeId;
    private String mImagePath;

    private boolean isCameraButton;
    private int chooseMode;
    private int gender = 1;
    private String personId;
    private PlateNumberDialog reStartOtherCountryDialog;
    private String mPersonId;
    private String mPersonName;
    private String mPicUri;
    private boolean isHaveFace = false;
    private String mPersonPhotoIndexCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        personId = intent.getStringExtra("personId");


        initView();
        initTitle();
        initData();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("用户信息");
        //        mainTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.visitor_invitation_record));
    }

    private FaceGridImageAdapter mAdapter2;

    private void initView() {
        DaggerFaceActivityComponent.builder()
                .appComponent(getAppComponent())
                .facePresenterModule(new FacePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        LinearLayoutManager manager2 = new LinearLayoutManager(FaceActivity.this, GridLayoutManager.HORIZONTAL, false);
        uploadingSpecialCertificateRv.setLayoutManager(manager2);
        mAdapter2 = new FaceGridImageAdapter(FaceActivity.this, onAddPicClickListener2);
        mAdapter2.setList(selectList2);
        //        mAdapter2.setSelectMax(1);
        uploadingSpecialCertificateRv.setAdapter(mAdapter2);
        mAdapter2.setOnItemClickListener(new FaceGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LogUtil.e(TAG, "长度---->" + selectList2.size());
                if (selectList2.size() > 0) {

                    LocalMedia media = selectList2.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(FaceActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(FaceActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            //                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
                            //                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
                            //                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                            PictureSelector.create(FaceActivity.this)
                                    .themeStyle(themeId) // xml设置主题
                                    //                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                    //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList2);
                            break;
                    }
                }
            }
        });
    }

    private FaceGridImageAdapter.onAddPicClickListener onAddPicClickListener2 = new FaceGridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            selectPictureSetting(isCameraButton, selectList2, 1);

        }

    };

    public void refershAddPictureButton2() {
        generateVisitorCard.setVisibility(View.VISIBLE);
        facePicVisibility.setVisibility(View.GONE);
    }

    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("paramName", "phoneNo");
        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getPersonalInformationData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void showProgressDialogView() {
        showProgressDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hiddenProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destory();
        }
    }

    private List<LocalMedia> selectListFace = new ArrayList<>();

    @OnClick({R2.id.main_title_back, R2.id.generate_visitor_card, R2.id.generate_visitor_uploading_face_pic, R2.id.huzhu_iv})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.huzhu_iv) {
            if (selectListFace.size() > 0) {
                PictureSelector.create(FaceActivity.this)
                        .themeStyle(themeId) // xml设置主题
                        //                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                        //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                        .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                        .loadImageEngine(com.xiaoanjujia.home.composition.me.merchants.GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                        .openExternalPreview(0, selectListFace);
            }
        } else if (id == R.id.generate_visitor_uploading_face_pic) {
            //            上传人脸
            if (selectList2.size() == 0) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请选择人脸照片");
                return;
            }
            uploadPictureToServer(selectList2);

        } else if (id == R.id.generate_visitor_card) {
            hideKeyboard(view);
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData("拍照/相册", "", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("拍照/相册".equals(str)) {
                        photoSelection(true, selectList2);
                    }
                }
            });
            selectPicPopupWindow.showPopWindow();

            //            uploadPictureToServer(selectList2);
        } else if (id == R.id.uploading_special_certificate_iv) {
            hideKeyboard(view);
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData("拍照/相册", "", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("拍照/相册".equals(str)) {
                        photoSelection(true, selectList2);
                    }
                }
            });
            selectPicPopupWindow.showPopWindow();
        }
    }

    //上传公司证件(方法)
    private void uploadPictureToServer(List<LocalMedia> selectList) {
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getUploadPicture(headersTreeMap, selectList);
    }

    @Override
    public void setPersonalInformationData(VisitorPersonInfoResponse mVisitorPersonInfoResponse) {
        try {
            String code = mVisitorPersonInfoResponse.getStatus();
            String msg = mVisitorPersonInfoResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<VisitorPersonInfoResponse.DataBean> data = mVisitorPersonInfoResponse.getData();
                if (data != null && data.size() > 0) {
                    VisitorPersonInfoResponse.DataBean dataBean = data.get(0);
                    if (dataBean != null) {
                        generateVisitorCard.setVisibility(View.VISIBLE);
                        facePicVisibility.setVisibility(View.GONE);
                        selectList2.clear();
                        /**
                         *      "marriaged": 0,
                         * 		"orgName": "2户室",
                         * 		"gender": 1,
                         * 		"orgPath": "@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@e01dae84-10fc-4f21-8863-acac68ea41ae@f2c46644-fcae-441c-831c-196362f4e36b@d1e64845-9083-4c37-8bdd-a2a7eead2a09@",
                         * 		"syncFlag": 0,
                         * 		"orgPathName": "铭嘉地产/张营小区三栋楼/1管理分区/14号楼/1单元/1楼层/2户室",
                         * 		"orgIndexCode": "d1e64845-9083-4c37-8bdd-a2a7eead2a09",
                         * 		"orgList": ["d1e64845-9083-4c37-8bdd-a2a7eead2a09"],
                         * 		"updateTime": "2020-09-01T21:27:04.290+08:00",
                         * 		"certificateNo": "1411251999912354562",
                         * 		"phoneNo": "15601267550",
                         * 		"personName": "老谢",
                         * 		"pinyin": "laoxie",
                         * 		"personPhoto": [],
                         * 		"createTime": "2020-09-01T21:24:22.641+08:00",
                         * 		"jobNo": "0124",
                         * 		"personId": "77edf8446c7c448e914caf14eeaf9f20",
                         * 		"lodge": 0,
                         * 		"age": 0,
                         * 		"certificateType": 111
                         *
                         * 	            "marriaged": 0,
                         *             "orgName": "铭嘉地产",
                         *             "gender": 2,
                         *             "orgPath": "@root000000@",
                         *             "syncFlag": 0,
                         *             "orgPathName": "铭嘉地产",
                         *             "orgIndexCode": "root000000",
                         *             "orgList": [
                         *                 "root000000"
                         *             ],
                         *             "updateTime": "2020-08-11T17:13:13.687+08:00",
                         *             "certificateNo": "13072719861044333",
                         *             "phoneNo": "13683673419",
                         *             "personName": "霍冉",
                         *             "pinyin": "huoran",
                         *             "roomNum": "101",
                         *             "personPhoto": [
                         *                 {
                         *                     "personPhotoIndexCode": "f182e3c0-0eaf-49a7-8590-f8d0f8a59be5",
                         *                     "picUri": "/pic?9ddc663bf-5do7b1l*46ed61i-z0e*3s0337781115m9ep=t=i9p*i=d1s*i=d3b*i7dce*8428fcd6a-b1=74a-40o11cpi0c1d=41ie8=",
                         *                     "serverIndexCode": "7ef23451-2765-48a7-bdb5-dbfb118f0061"
                         *                 }
                         *             ],
                         *             "createTime": "2020-08-11T10:06:28.427+08:00",
                         *             "jobNo": "101",
                         *             "personId": "3c17d5694a8c4f1aacd60ae44c1267db",
                         *             "lodge": 0,
                         *             "age": 0,
                         *             "certificateType": 111
                         */
                        mPersonName = dataBean.getPersonName();
                        if (!Utils.isNull(mPersonName)) {
                            huzhuTv.setText(mPersonName);

                        }
                        personId = dataBean.getPersonId();
                        String orgPathName = dataBean.getOrgPathName();
                        int gender = dataBean.getGender();
                        //++gender number False 性别，1：男；2：女；0：未知
                        if (gender == 1) {
                            //1：男
                            sexImg.setImageDrawable(getResources().getDrawable(R.drawable.sex_man));
                        } else if (gender == 2) {
                            //2：女
                            sexImg.setImageDrawable(getResources().getDrawable(R.drawable.sex_woman));
                        } else {
                            //0：未知
                            sexImg.setImageDrawable(getResources().getDrawable(R.drawable.sex_weizhi));
                        }
                        String phoneNo = dataBean.getPhoneNo();
                        if (!Utils.isNull(phoneNo)) {
                            phoneTv.setText(phoneNo);
                        }

                        String certificateNo = dataBean.getCertificateNo();
                        if (!Utils.isNull(certificateNo)) {
                            idCardTv.setText(certificateNo);
                        }
                        mPersonId = dataBean.getPersonId();
                        List<VisitorPersonInfoResponse.DataBean.PersonPhotoBean> personPhoto = dataBean.getPersonPhoto();
                        if (personPhoto != null && personPhoto.size() > 0) {

                            mPersonPhotoIndexCode = personPhoto.get(0).getPersonPhotoIndexCode();
                            mPicUri = personPhoto.get(0).getPicUri();
                            //                            initQueryFace(picUri, mPersonId);

                            RequestOptions options = RequestOptions
                                    .bitmapTransform(new CircleCrop());

                            if (!Utils.isNull(mPicUri) && !Utils.isNull(mPersonPhotoIndexCode)) {

                                if (!mPicUri.startsWith("http")) {
                                    mPicUri = MainDataManager.HK_ROOT_URL_PIC + mPicUri;
                                }
                                //头像
                                Glide.with(mContext)
                                        .load(mPicUri)
                                        .apply(options)
                                        .into(huzhuIv);

                                LocalMedia localMedia = new LocalMedia();
                                localMedia.setPath(mPicUri);
                                localMedia.setAndroidQToPath(mPicUri);
                                selectListFace.clear();
                                selectListFace.add(localMedia);
                                generateVisitorCard.setText("重新采集人脸");
                                isHaveFace = true;
                            } else {
                                generateVisitorCard.setText("采集人脸");
                                isHaveFace = false;
                            }

                        } else {
                            generateVisitorCard.setText("采集人脸");
                            isHaveFace = false;
                        }


                    }
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(FaceActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "获取人脸信息失败");
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "解析数据失败");
        }
    }

    //facePicUrl=http://hk.xiaoanjujia.com/image/renlian.jpg
    public void initFaceScoreData(String facePicUrl) {
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("facePicUrl", facePicUrl);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getFaceScoreData(headersTreeMap, mapParameters);
    }

    @Override
    public void setFaceScoreData(VisitorFaceScoreResponse mVisitorFaceScoreResponsee) {
        try {
            String code = mVisitorFaceScoreResponsee.getStatus();
            String msg = mVisitorFaceScoreResponsee.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                VisitorFaceScoreResponse.DataBean data = mVisitorFaceScoreResponsee.getData();
                if (data != null) {
                    int faceScore = data.getFaceScore();
                    if (faceScore > 75) {
                        //                        initData();
                        if (!isHaveFace) {
                            //添加
                            initAddFace(mImagePath);
                        } else {
                            //修改
                            initUpdateFace(mImagePath);
                        }


                    } else {
                        ToastUtil.showToast(FaceActivity.this, "人脸评分低于75分,请重新选择人脸照片!");
                        hiddenProgressDialog();
                    }
                }

            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(FaceActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(FaceActivity.this, "人脸评分失败,请重新选择人脸照片!");
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "解析数据失败");
        }
    }


    @Override
    public void setUploadPicture(UploadImageResponse uploadImageResponse) {
        try {
            int code = uploadImageResponse.getStatus();
            String msg = uploadImageResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                mImagePath = uploadImageResponse.getData().getPath();
                initFaceScoreData(mImagePath);

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);
                finish();
            } else {
                ToastUtil.showToast(this.getApplicationContext(), "图片上传失败");
                hiddenProgressDialogView();
            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }

    //http://hk.xiaoanjujia.com/artemis/face/addFace?personId=e5cab658a0024ae29837755c4933f04f&faceUrl=https://a.xiaoanjujia.com/uploads/images/2020/09-09/20200910105707.jpg
    public void initAddFace(String facePicUrl) {
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("personId", mPersonId);
        mapParameters.put("faceUrl", facePicUrl);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getAddFace(headersTreeMap, mapParameters);
    }

    @Override
    public void setAddFace(AddFaceResponse mAddFaceResponse) {
        try {
            String code = mAddFaceResponse.getStatus();
            String msg = mAddFaceResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                AddFaceResponse.DataBean data = mAddFaceResponse.getData();
                //                String faceId = data.getFaceId();
                //                String faceUrl = data.getFaceUrl();
                //                String personId = data.getPersonId();
                //                initQueryFace(faceUrl, personId);
                initData();

                //                if (data != null) {
                //                    int faceScore = data.getFaceScore();
                //                    if (faceScore > 75) {
                //                        //                        initData();
                //                    } else {
                //                        ToastUtil.showToast(FaceActivity.this, "人脸评分低于75分,请重新选择人脸照片!");
                //                    }
                //                }

            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(FaceActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "采集人脸照片失败");
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "解析数据失败");
        }
    }

    //http://hk.xiaoanjujia.com/artemis/face/updateFace?
    // faceId=ba1147ab-55c6-4dbb-9c3d-daecd88d2d34&
    // faceUrl=https://a.xiaoanjujia.com/uploads/images/2020/09-09/20200910105707.jpg **
    // 具体参数链接及其说明https://open.hikvision.com/docs/cd03aa4347c342c1a6bb9e220a867bc6#bcba56a2
    public void initUpdateFace(String facePicUrl) {
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("faceId", mPersonPhotoIndexCode);
        mapParameters.put("faceUrl", facePicUrl);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getUpdateFace(headersTreeMap, mapParameters);
    }

    @Override
    public void setUpdateFace(UpdateFaceResponse mUpdateFaceResponse) {
        try {
            String code = mUpdateFaceResponse.getStatus();
            String msg = mUpdateFaceResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                UpdateFaceResponse.DataBean data = mUpdateFaceResponse.getData();
                //                String faceId = data.getFaceId();
                //                String faceUrl = data.getFaceUrl();
                //                String personId = data.getPersonId();
                //                initQueryFace(faceUrl, personId);
                initData();

                //                if (data != null) {
                //                    int faceScore = data.getFaceScore();
                //                    if (faceScore > 75) {
                //                        //                        initData();
                //                    } else {
                //                        ToastUtil.showToast(FaceActivity.this, "人脸评分低于75分,请重新选择人脸照片!");
                //                    }
                //                }

            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(FaceActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "更新人脸照片失败,请重试");
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "解析数据失败");
        }
    }


    public void initQueryFace(String faceUrl, String personId) {
        Map<String, Object> mapParameters = new HashMap<>(2);
        //        mapParameters.put("faceUrl", faceUrl);
        mapParameters.put("personId", personId);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getQueryFace(headersTreeMap, mapParameters);
    }

    @Override
    public void seQueryFace(QueryFaceResponse mQueryFaceResponse) {
        try {
            String code = mQueryFaceResponse.getStatus();
            String msg = mQueryFaceResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<QueryFaceResponse.DataBean> data = mQueryFaceResponse.getData();
                if (data != null && data.size() > 0) {
                    QueryFaceResponse.DataBean dataBean = data.get(0);
                    if (dataBean != null) {

                        //                        String personName = dataBean.getPersonName();
                        //                        if (!Utils.isNull(personName)) {
                        //                            huzhuTv.setText(personName);
                        //                        }
                        //                        personId = dataBean.getPersonId();
                        //                        String orgPathName = dataBean.getOrgPathName();
                        //                        int gender = dataBean.getGender();
                        //                        //++gender number False 性别，1：男；2：女；0：未知
                        //                        if (gender == 1) {
                        //                            //1：男
                        //                            sexImg.setImageDrawable(getResources().getDrawable(R.drawable.sex_man));
                        //                        } else if (gender == 2) {
                        //                            //2：女
                        //                            sexImg.setImageDrawable(getResources().getDrawable(R.drawable.sex_woman));
                        //                        } else {
                        //                            //0：未知
                        //                            sexImg.setImageDrawable(getResources().getDrawable(R.drawable.sex_weizhi));
                        //                        }
                        //                        String phoneNo = dataBean.getPhoneNo();
                        //                        if (!Utils.isNull(phoneNo)) {
                        //                            phoneTv.setText(phoneNo);
                        //                        }
                        //
                        //                        String certificateNo = dataBean.getCertificateNo();
                        //                        if (!Utils.isNull(certificateNo)) {
                        //                            idCardTv.setText(certificateNo);
                        //                        }
                    }
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(FaceActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "查询人脸评分失败,请重试");
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(FaceActivity.this.getApplicationContext(), "解析数据失败");
        }
    }


    public void photoSelection(boolean isFirst, List<LocalMedia> selectList) {
        //*上传特殊材料：
        isCameraButton = true;
        //            mAdapter2.setSelectMax(1);
        // 单独拍照和相册
        chooseMode = PictureMimeType.ofImage();
        selectPictureSetting(isCameraButton, selectList2, 1);
        mAdapter2.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:


                    //---------------------------------------------------------------------------------------------------------
                    // 图片选择结果回调
                    selectList2 = PictureSelector.obtainMultipleResult(data);

                    //选择后有结果隐藏图片添加的按钮,无结果则隐藏
                    //选择后有结果显示uploadingSpecialCertificateRv,无结果则隐藏
                    if (selectList2.size() > 0) {
                        generateVisitorCard.setVisibility(View.GONE);
                        facePicVisibility.setVisibility(View.VISIBLE);
                    } else {
                        generateVisitorCard.setVisibility(View.VISIBLE);
                        facePicVisibility.setVisibility(View.GONE);
                    }
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    for (LocalMedia media : selectList2) {
                        LogUtil.e(TAG, "压缩---->" + media.getCompressPath());
                        LogUtil.e(TAG, new File(media.getCompressPath()).length() / 1024 + "k");
                        LogUtil.e(TAG, "原图---->" + media.getPath());
                        LogUtil.e(TAG, "裁剪---->" + media.getCutPath());
                        LogUtil.e(TAG, "Android Q 特有Path---->" + media.getAndroidQToPath());
                    }
                    mAdapter2.setList(selectList2);
                    mAdapter2.notifyDataSetChanged();


                    break;
            }
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void selectPictureSetting(Boolean isCameraButton, List<LocalMedia> selectList, int mMaxSelectNum) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(FaceActivity.this)
                .openGallery(chooseMode)// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style v2.3.3后 建议使用setPictureStyle()动态方式
                //                        .setLanguage(language)// 设置语言，默认中文
                //                        .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                //                        .setPictureCropStyle(mCropParameterStyle)// 动态自定义裁剪主题
                //.setPictureWindowAnimationStyle(new PictureWindowAnimationStyle())// 自定义相册启动退出动画
                .maxSelectNum(mMaxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                //                        .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .isSingleDirectReturn(false)// 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .previewImage(true)// 是否可预览图片
                .previewVideo(true)// 是否可预览视频
                //.querySpecifiedFormatSuffix(PictureMimeType.ofPNG())// 查询指定后缀格式资源
                .enablePreviewAudio(true) // 是否可播放音频
                .isCamera(isCameraButton)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .enableCrop(true)// 是否裁剪
                .compress(true)// 是否压缩
                .compressQuality(40)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步false或异步true 压缩 默认同步
                .queryMaxFileSize(100)// 只查多少M以内的图片、视频、音频  单位M
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
                //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)
//                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                .isGif(true)// 是否显示gif图片
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(false)// 是否圆形裁剪
                .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                .openClickSound(false)// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
                //                        .videoMaxSecond(15)
                //                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.cropCompressQuality(90)// 注：已废弃 改用cutOutQuality()
                .cutOutQuality(40)// 裁剪输出质量 默认100
                .minimumCompressSize(150)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径  注：已废弃
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


}
