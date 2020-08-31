package com.xiaoanjujia.home.composition.me.post_message;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.xiaoanjujia.home.entities.FeedBackResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;
import com.xiaoanjujia.home.tool.Api;
import com.xiaoanjujia.home.tool.Util;

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
 * 商户认证
 */
@Route(path = "/postMessageActivity/postMessageActivity")
public class PostMessageActivity extends BaseActivity implements PostMessageContract.View {
    @Inject
    PostMessagePresenter mPresenter;


    private static final String TAG = "PostMessageActivity";
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
    @BindView(R2.id.edit_merchant_name)
    EditText editMerchantName;
    @BindView(R2.id.edit_company_name)
    EditText editCompanyName;
    @BindView(R2.id.edit_merchant_phone)
    EditText editMerchantPhone;
    @BindView(R2.id.edit_merchant_code)
    EditText editMerchantCode;


    @BindView(R2.id.company_certificate_im)
    ImageView companyCertificateIm;
    @BindView(R2.id.company_certificate_recycler)
    RecyclerView companyCertificateRecycler;
    @BindView(R2.id.uploading_special_certificate_iv)
    ImageView uploadingSpecialCertificateIv;
    @BindView(R2.id.uploading_special_certificate_rv)
    RecyclerView uploadingSpecialCertificateRv;


    @BindView(R2.id.register_success_entry)
    AlphaButton registerSuccessEntry;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;


    private PostMessageGridImageAdapter mAdapter;
    private PostMessageGridImageAdapter2 mAdapter2;
    private int themeId;
    private int chooseMode;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> selectList2 = new ArrayList<>();
    private int mMaxSelectNum;
    private boolean isCameraButton;
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jkx/";
    private boolean isfirst = false;
    private String mCompanyPath;
    private String mMSpecialImgsPath;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        initTitle();
        initView();

    }

    /**
     * 初始化title
     */
    public void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("发布信息");
    }


    private void initView() {
        DaggerPostMessageActivityComponent.builder()
                .appComponent(getAppComponent())
                .postMessagePresenterModule(new PostMessagePresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);


        PostMessageFullyGridLayoutManager manager = new PostMessageFullyGridLayoutManager(PostMessageActivity.this, 3, GridLayoutManager.VERTICAL, false);
        companyCertificateRecycler.setLayoutManager(manager);
        mAdapter = new PostMessageGridImageAdapter(PostMessageActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);
        //        mAdapter.setSelectMax(5);
        companyCertificateRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PostMessageGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                isfirst = true;
                LogUtil.e(TAG, "长度---->" + selectList.size());
                if (selectList.size() > 0) {

                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(PostMessageActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(PostMessageActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            //                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
                            //                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
                            //                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                            PictureSelector.create(PostMessageActivity.this)
                                    .themeStyle(themeId) // xml设置主题
                                    //                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                    //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList);
                            break;
                    }
                }
            }
        });


        LinearLayoutManager manager2 = new LinearLayoutManager(PostMessageActivity.this, GridLayoutManager.HORIZONTAL, false);
        uploadingSpecialCertificateRv.setLayoutManager(manager2);
        mAdapter2 = new PostMessageGridImageAdapter2(PostMessageActivity.this, onAddPicClickListener2);
        mAdapter2.setList(selectList2);
        //        mAdapter2.setSelectMax(5);
        uploadingSpecialCertificateRv.setAdapter(mAdapter2);
        mAdapter2.setOnItemClickListener(new PostMessageGridImageAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                isfirst = false;
                LogUtil.e(TAG, "长度---->" + selectList2.size());
                if (selectList2.size() > 0) {

                    LocalMedia media = selectList2.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(PostMessageActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(PostMessageActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            //                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
                            //                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
                            //                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                            PictureSelector.create(PostMessageActivity.this)
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

    /**
     * user_id:用户id
     * name_acronym:商户简称
     * shop_name:商户全称
     * shop_phone:手机号
     * code:身份证或者组织代码
     * status:0个人,1企业( 可不传 ) *
     * imgs_url:公司证件图片链接
     * special_imgs_url:特殊证件图片链接
     */
    private void initData() {
        String editMerchantNameText = editMerchantName.getText().toString().trim();
        String editCompanyNameText = editCompanyName.getText().toString().trim();
        String editMerchantPhoneText = editMerchantPhone.getText().toString().trim();
        String editMerchantCodeText = editMerchantCode.getText().toString().trim();
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        mapParameters.put("name_acronym", editMerchantNameText);
        mapParameters.put("shop_name", editCompanyNameText);
        mapParameters.put("shop_phone", editMerchantPhoneText);
        mapParameters.put("code", editMerchantCodeText);
        mapParameters.put("imgs_url", mCompanyPath);
        if (!Utils.isNull(mMSpecialImgsPath)) {
            mapParameters.put("special_imgs_url", mMSpecialImgsPath);
        }
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void setResponseData(FeedBackResponse feedBackResponse) {
        try {
            int code = feedBackResponse.getStatus();
            String msg = feedBackResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {

                ToastUtil.showToast(this.getApplicationContext(), "提交成功");
                ARouter.getInstance().build("/SubmitSuccessActivity/SubmitSuccessActivity").greenChannel().navigation(mContext);
                finish();
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void setUploadImage(UploadImageResponse uploadImageResponse) {
        try {
            int code = uploadImageResponse.getStatus();
            String msg = uploadImageResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                mMSpecialImgsPath = uploadImageResponse.getData().getPath();
                //用户未选择 特殊材料
                initData();

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);
                finish();
            } else {
                ToastUtil.showToast(this.getApplicationContext(), msg);
                hiddenProgressDialogView();
            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void setUploadPicture(UploadImageResponse uploadImageResponse) {
        try {
            int code = uploadImageResponse.getStatus();
            String msg = uploadImageResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                mCompanyPath = uploadImageResponse.getData().getPath();
                //上传特殊图片
                if (selectList2.size() > 0) {
                    uploadImageToServer(selectList2);
                } else {
                    //用户未选择 特殊材料
                    initData();

                }

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);
                finish();
            } else {
                ToastUtil.showToast(this.getApplicationContext(), msg);
                hiddenProgressDialogView();
            }
        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
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


    @OnClick({R2.id.main_title_back, R2.id.register_success_entry, R2.id.company_certificate_im, R2.id.uploading_special_certificate_iv})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.register_success_entry) {
            String editMerchantNameText = editMerchantName.getText().toString().trim();
            String editCompanyNameText = editCompanyName.getText().toString().trim();
            String editMerchantPhoneText = editMerchantPhone.getText().toString().trim();
            String editMerchantCodeText = editMerchantCode.getText().toString().trim();
            if (Util.isNull(editMerchantNameText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入商户简称");
                return;
            }
            if (Util.isNull(editCompanyNameText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入公司名称");
                return;
            }
            if (Util.isNull(editMerchantPhoneText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入联系号码");
                return;
            }
            if (Util.isNull(editMerchantCodeText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入商户代码");
                return;
            }
            //先上传公司证件  ,公司证件上传成功后 在上传特殊材料选择图片
            if (selectList.size() == 0) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请选择上传公司证件");
                return;
            }
            uploadPictureToServer(selectList);

        } else if (id == R.id.company_certificate_im) {
            hideKeyboard(view);
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData("拍照/相册", "", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("拍照/相册".equals(str)) {
                        photoSelection(true, selectList);
                    }
                }
            });
            selectPicPopupWindow.showPopWindow();
        } else if (id == R.id.uploading_special_certificate_iv) {
            hideKeyboard(view);
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData("拍照/相册", "", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("拍照/相册".equals(str)) {
                        photoSelection(false, selectList2);
                    }
                }
            });
            selectPicPopupWindow.showPopWindow();
        }
    }


    private void postError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "process error!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //上传特殊图片(方法)
    private void uploadImageToServer(List<LocalMedia> selectList) {
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getUploadImage(headersTreeMap, selectList);
    }

    //上传公司证件(方法)
    private void uploadPictureToServer(List<LocalMedia> selectList) {
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getUploadPicture(headersTreeMap, selectList);
    }


    public void refershAddPictureButton() {
        companyCertificateIm.setVisibility(View.VISIBLE);
        companyCertificateRecycler.setVisibility(View.GONE);
    }

    public void refershAddPictureButton2() {
        uploadingSpecialCertificateIv.setVisibility(View.VISIBLE);
        uploadingSpecialCertificateRv.setVisibility(View.GONE);
    }

    public void photoSelection(boolean isFirst, List<LocalMedia> selectList) {
        this.isfirst = isFirst;
        if (isFirst) {
            //上传公司证件：
            isCameraButton = true;
            mMaxSelectNum = 3;
            //            mAdapter.setSelectMax(3);
            // 单独拍照和相册
            chooseMode = PictureMimeType.ofImage();
            selectPictureSetting(isCameraButton, selectList, 3);
            mAdapter.notifyDataSetChanged();
        } else {
            //*上传特殊材料：
            isCameraButton = true;
            mMaxSelectNum = 5;
            //            mAdapter2.setSelectMax(5);
            // 单独拍照和相册
            chooseMode = PictureMimeType.ofImage();
            selectPictureSetting(isCameraButton, selectList2, 5);
            mAdapter2.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    if (isfirst) {
                        // 图片选择结果回调
                        selectList = PictureSelector.obtainMultipleResult(data);

                        //选择后有结果隐藏图片添加的按钮,无结果则隐藏
                        //选择后有结果显示companyCertificateRecycler,无结果则隐藏
                        if (selectList.size() > 0) {
                            companyCertificateIm.setVisibility(View.GONE);
                            companyCertificateRecycler.setVisibility(View.VISIBLE);
                        } else {
                            companyCertificateIm.setVisibility(View.VISIBLE);
                            companyCertificateRecycler.setVisibility(View.GONE);
                        }
                        // 例如 LocalMedia 里面返回三种path
                        // 1.media.getPath(); 为原图path
                        // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                        // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                        // 4.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                        for (LocalMedia media : selectList) {
                            LogUtil.e(TAG, "压缩---->" + media.getCompressPath());
                            LogUtil.e(TAG, new File(media.getCompressPath()).length() / 1024 + "k");
                            LogUtil.e(TAG, "原图---->" + media.getPath());
                            LogUtil.e(TAG, "裁剪---->" + media.getCutPath());
                            LogUtil.e(TAG, "Android Q 特有Path---->" + media.getAndroidQToPath());
                        }
                        mAdapter.setList(selectList);
                        mAdapter.notifyDataSetChanged();
                    } else {

                        //---------------------------------------------------------------------------------------------------------
                        // 图片选择结果回调
                        selectList2 = PictureSelector.obtainMultipleResult(data);

                        //选择后有结果隐藏图片添加的按钮,无结果则隐藏
                        //选择后有结果显示uploadingSpecialCertificateRv,无结果则隐藏
                        if (selectList2.size() > 0) {
                            uploadingSpecialCertificateIv.setVisibility(View.GONE);
                            uploadingSpecialCertificateRv.setVisibility(View.VISIBLE);
                        } else {
                            uploadingSpecialCertificateIv.setVisibility(View.VISIBLE);
                            uploadingSpecialCertificateRv.setVisibility(View.GONE);
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
                    }


                    break;
            }
        }
    }

    private PostMessageGridImageAdapter.onAddPicClickListener1 onAddPicClickListener = new PostMessageGridImageAdapter.onAddPicClickListener1() {
        @Override
        public void onAddPicClick1() {
            isfirst = true;
            mMaxSelectNum = 3;
            selectPictureSetting(isCameraButton, selectList, 3);

        }

    };
    private PostMessageGridImageAdapter2.onAddPicClickListener onAddPicClickListener2 = new PostMessageGridImageAdapter2.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            mMaxSelectNum = 5;
            isfirst = false;
            selectPictureSetting(isCameraButton, selectList2, 5);

        }

    };

    private void selectPictureSetting(Boolean isCameraButton, List<LocalMedia> selectList, int mMaxSelectNum) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(PostMessageActivity.this)
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
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .compressQuality(80)// 图片压缩后输出质量 0~ 100
                .synOrAsy(true)//同步false或异步true 压缩 默认同步
                .queryMaxFileSize(100)// 只查多少M以内的图片、视频、音频  单位M
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效 注：已废弃
                //.glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度 注：已废弃
                //                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
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
                .cutOutQuality(90)// 裁剪输出质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径  注：已废弃
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
