package com.xiaoanjujia.home.composition.me.merchants;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.SelectPicPopupWindow;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.FeedBackResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 * 知见-发布
 */
@Route(path = "/publishActivity/publishActivity")
public class PublishActivity extends BaseActivity implements PublishContract.View {
    @Inject
    PublishPresenter mPresenter;


    private static final String TAG = "PublishActivity";
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


    private Button mLoginEntry;
    private FeedBackResponse feedBackResponse;

    public static final int START_RECORD = 24;    //录音
    private GridImageAdapter mAdapter;


    private int themeId;
    private int chooseMode;

    private List<LocalMedia> selectList = new ArrayList<>();
    private int mMaxSelectNum;
    private boolean isCameraButton;
    private int mChoseType = 0; //1为图片  2为视频

    private String currentOutputVideoPath = "";//压缩后的视频地址

    private String videoTime = "";//获取视频时长
    private int videoWidth = 0;//获取视频的宽度
    private int videoHeight = 0;//获取视频的高度
    private int videoGotation = 0;//获取视频的角度
    private Bitmap mBitMap;
    private Double videoLength = 0.00;//视频时长 s


    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jkx/";
    private List<PublishImageResponse> imageUriList = new ArrayList<>();
    private List<LocalMedia> selectImageCommitTemp;    //防止提交未成功 报错而临时存在的,只有提交时 才会赋值
    private UploadImageResponse uploadImageResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledge_publish);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        selectImageCommitTemp = new ArrayList<>();
        initTitle();
        initView();

        initFile();
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        mainTitleBack.setVisibility(View.INVISIBLE);
        mainTitleText.setText(R.string.ren_zheng_shang_hu);
    }


    private void initView() {
        DaggerPublishActivityComponent.builder()
                .appComponent(getAppComponent())
                .publishPresenterModule(new PublishPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);


        FullyGridLayoutManager manager = new FullyGridLayoutManager(PublishActivity.this, 3, GridLayoutManager.VERTICAL, false);
        companyCertificateRecycler.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(PublishActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);

        companyCertificateRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LogUtil.e(TAG, "长度---->" + selectList.size());
                if (selectList.size() > 0) {

                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(PublishActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case PictureConfig.TYPE_AUDIO:
                            // 预览音频
                            PictureSelector.create(PublishActivity.this).externalPictureAudio(media.getPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            //                        PictureWindowAnimationStyle animationStyle = new PictureWindowAnimationStyle();
                            //                        animationStyle.activityPreviewEnterAnimation = R.anim.picture_anim_up_in;
                            //                        animationStyle.activityPreviewExitAnimation = R.anim.picture_anim_down_out;
                            PictureSelector.create(PublishActivity.this)
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

    }

    private void initData(String content) {

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("CONTETNT", content);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "S012");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void setResponseData(FeedBackResponse feedBackResponse) {
        this.feedBackResponse = feedBackResponse;
        try {
            String code = feedBackResponse.getCode();
            String msg = feedBackResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                ToastUtil.showToast(this.getApplicationContext(), "提交成功");
                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
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
        this.uploadImageResponse = uploadImageResponse;
        try {
            String code = uploadImageResponse.getCode();
            String msg = uploadImageResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {
                String image_uri = uploadImageResponse.getData().getIMAGE_URI();
                PublishImageResponse publishImageResponse = new PublishImageResponse();
                publishImageResponse.setTYPE("1");
                publishImageResponse.setURI(image_uri);
                imageUriList.add(publishImageResponse);

                selectImageCommitTemp.remove(0);
                uploadImage(selectImageCommitTemp);
                //                if (selectImageCommitTemp.size() < 1)
                //                    deleteCache();
            } else if (code.equals(ResponseCode.SEESION_ERROR)) {
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

    public static final int TAKE_VIDEO = 16; // 视频
    public static final int TAKE_PICTURE = 3; // 拍照和相册

    @OnClick({R2.id.main_title_back, R2.id.register_success_entry, R2.id.company_certificate_im})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.register_success_entry) {
            String publishTitle = edPublishTitle.getText().toString().trim();
            String publishContent = edPublishContent.getText().toString().trim();
            if (TextUtils.isEmpty(publishTitle)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入标题");
                return;
            } else if (publishTitle.length() > 50) {
                ToastUtil.showToast(mContext.getApplicationContext(), "标题限制50字数");
                return;
            }

            if (TextUtils.isEmpty(publishContent)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入内容");
                return;
            } else if (publishContent.length() > 1500) {
                ToastUtil.showToast(mContext.getApplicationContext(), "内容限制1500字数");
                return;
            }
        } else if (id == R.id.company_certificate_im) {
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData("照片", "", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("照片".equals(str)) {
                        mChoseType = 1;
                        photoSelection(true, 2, TAKE_PICTURE, selectList);
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


    private void uploadImage(List<LocalMedia> localMediaList) {
        if (localMediaList.size() > 0) {
            String compressPath = localMediaList.get(0).getCompressPath();
            File file = new File(compressPath);

            LogUtil.e(TAG, "=====compressPath======" + compressPath);
            LogUtil.e("compress image result:", new File(compressPath).length() / 1024 + "k");
            uploadImageToServer(file);
        }
    }

    //上传图片(方法)
    private void uploadImageToServer(File file_name) {
        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "CM003");
        mapHeaders.put("SESSION_ID", mSession_id);
        mPresenter.getUploadImage(mapHeaders, file_name);
    }


    public void refershAddPictureButton() {
        companyCertificateIm.setVisibility(View.VISIBLE);
        companyCertificateRecycler.setVisibility(View.GONE);
    }


    public void photoSelection(boolean isCamera, int maxSelect, int resquestCode, List<LocalMedia> selectList) {
        if (isCamera) {
            isCameraButton = true;
            mMaxSelectNum = 9;
            mAdapter.setSelectMax(mMaxSelectNum);
            // 单独拍照和相册
            chooseMode = PictureMimeType.ofImage();
            selectPictureSetting(isCameraButton);
        } else {
            isCameraButton = false;
            mMaxSelectNum = 1;
            mAdapter.setSelectMax(mMaxSelectNum);
            // 视频
            chooseMode = PictureMimeType.ofVideo();
            selectPictureSetting(isCameraButton);
        }
        //        选择拍照熟悉设置


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);

                    //选择后有结果隐藏图片添加的按钮,无结果则隐藏
                    //选择后有结果显示companyCertificateRecycler,无结果则隐藏
                    if (selectList.size() > 0) {
                        ivSelectAddImage.setVisibility(View.GONE);
                        companyCertificateRecycler.setVisibility(View.VISIBLE);
                    } else {
                        ivSelectAddImage.setVisibility(View.VISIBLE);
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
                        LogUtil.e(TAG, "原图---->" + media.getPath());
                        LogUtil.e(TAG, "裁剪---->" + media.getCutPath());
                        LogUtil.e(TAG, "Android Q 特有Path---->" + media.getAndroidQToPath());
                    }
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            selectPictureSetting(isCameraButton);

        }

    };

    private void selectPictureSetting(Boolean isCameraButton) {
        // 进入相册 以下是例子：不需要的api可以不写
        PictureSelector.create(PublishActivity.this)
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

    private void initFile() {
        makeRootDirectory(PATH);
        currentOutputVideoPath = PATH + GetPathFromUri.getVideoFileName();
    }


    /**
     * 没有文件夹。创建文件夹
     *
     * @param filePath
     */
    public void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                Boolean isTrue = file.mkdir();
            }
        } catch (Exception e) {

        }
    }

}
