package com.xiaoanjujia.home.composition.me.merchants;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
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
import com.xiaoanjujia.home.MainDataManager;

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
    View           fakeStatusBar;
    @BindView(R2.id.jkx_title_left)
    TextView       jkxTitleLeft;
    @BindView(R2.id.jkx_close_page)
    TextView       jkxClosePage;
    @BindView(R2.id.jkx_title_left_btn)
    Button         jkxTitleLeftBtn;
    @BindView(R2.id.jkx_title_center)
    TextView       jkxTitleCenter;
    @BindView(R2.id.jkx_title_right_btn)
    TextView       jkxTitleRightBtn;
    @BindView(R2.id.new_message)
    TextView       newMessage;
    @BindView(R2.id.rl_new_message)
    RelativeLayout rlNewMessage;
    @BindView(R2.id.jkx_title_right)
    TextView       jkxTitleRight;
    @BindView(R2.id.tv_knowledge_publish)
    TextView       tvKnowledgePublish;
    @BindView(R2.id.jkx_text_right_select)
    TextView       jkxTextRightSelect;
    @BindView(R2.id.ed_publish_title)
    EditText       edPublishTitle;
    @BindView(R2.id.ed_publish_content)
    EditText       edPublishContent;
    @BindView(R2.id.recycler)
    RecyclerView recyclerView;
    @BindView(R2.id.iv_select_add_image)
    ImageView      ivSelectAddImage;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout   llKnowledgePublishRoot;


    private Button           mLoginEntry;
    private FeedBackResponse feedBackResponse;

    public static final int              START_RECORD = 24;    //录音
    private             GridImageAdapter mAdapter;


    private int themeId;
    private int chooseMode;

    private List<LocalMedia> selectList = new ArrayList<>();
    private int              mMaxSelectNum;
    private boolean          isCameraButton;
    private int              mChoseType = 0; //1为图片  2为视频

    private String     currentOutputVideoPath = "";//压缩后的视频地址

    private String videoTime     = "";//获取视频时长
    private int    videoWidth    = 0;//获取视频的宽度
    private int    videoHeight   = 0;//获取视频的高度
    private int    videoGotation = 0;//获取视频的角度
    private Bitmap mBitMap;
    private Double videoLength   = 0.00;//视频时长 s

    private CustomProgressDialog mProcessingDialog;


    public static final String                     PATH         = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jkx/";
    private             UploadVideoResponse        uploadVideoResponse;
    private             List<PublishVideoResponse> videoUriList = new ArrayList<>();
    private             List<PublishImageResponse> imageUriList = new ArrayList<>();
    private             List<LocalMedia>           selectImageCommitTemp;    //防止提交未成功 报错而临时存在的,只有提交时 才会赋值
    private             UploadImageResponse        uploadImageResponse;

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
        edPublishContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(s.toString())) {
                    //                    tvCommit.setBackgroundResource(R.drawable.bg_shap_button_31);
                } else {
                    //                    tvCommit.setBackgroundResource(R.drawable.bg_shap_button_31_gray);
                }
            }
        });
        initFile();
        initVideo();
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        //返回按钮
        jkxClosePage.setVisibility(View.VISIBLE);
        tvKnowledgePublish.setVisibility(View.VISIBLE);

        //标题
        jkxTitleCenter.setText("");


    }


    private void initView() {
        DaggerPublishActivityComponent.builder()
                .appComponent(getAppComponent())
                .publishPresenterModule(new PublishPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);


        FullyGridLayoutManager manager = new FullyGridLayoutManager(PublishActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        mAdapter = new GridImageAdapter(PublishActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);

        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, v) -> {
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
    public void setUploadVideo(UploadVideoResponse uploadVideoResponse) {
        this.uploadVideoResponse = uploadVideoResponse;
        try {
            String code = uploadVideoResponse.getCode();
            String msg = uploadVideoResponse.getMsg();
            if (code.equals(ResponseCode.SUCCESS_OK)) {

                String video_uri = uploadVideoResponse.getData().getIMAGE_URI();
                PublishVideoResponse videoResponse = new PublishVideoResponse();
                videoResponse.setTYPE("2");
                videoResponse.setURI(video_uri);
                videoUriList.add(videoResponse);
                String jsonVideo = new Gson().toJson(videoUriList);
                LogUtil.e(TAG, "============jsonVideo===============" + jsonVideo);
                initUploadVideoData(jsonVideo, "1");
                videoUriList.clear();
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

    public static final int TAKE_VIDEO   = 16; // 视频
    public static final int TAKE_PICTURE = 3; // 拍照和相册

    @OnClick({R2.id.jkx_close_page, R2.id.tv_knowledge_publish, R2.id.iv_select_add_image})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.jkx_close_page) {
            finish();
        } else if (i == R.id.tv_knowledge_publish) {
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

            if (selectList.size() > 0) {
                if (mChoseType == 2) {
                    // 选择的视频需要进行压缩
                    LocalMedia localMedia = selectList.get(0);
                    String mVideoPath = localMedia.getPath();
                    try {
                        MediaMetadataRetriever retr = new MediaMetadataRetriever();
                        retr.setDataSource(mVideoPath);
                        videoTime = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);//获取视频时长
                        videoWidth = Integer.valueOf(retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));//获取视频的宽度
                        videoHeight = Integer.valueOf(retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));//获取视频的高度
                        videoGotation = Integer.valueOf(retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));//获取视频的角度

                        LogUtil.e(TAG, "videoWidth=" + videoWidth);
                        LogUtil.e(TAG, "videoHeight=" + videoHeight);
                        videoLength = Double.parseDouble(videoTime) / 1000.00;
                        LogUtil.e(TAG, "videoLength=" + videoLength);
                        mProcessingDialog.show();
                        mProcessingDialog.setProgress(0);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {


                                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                                    retriever.setDataSource(mVideoPath);
                                    int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                                    int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                                    int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
                                    int outWidth = 0;
                                    int outHeight = 0;
                                    int outBitrate = 0;
                                    if(originWidth>originHeight){
                                        if (originHeight > 3500) {
                                            outWidth = originWidth / 7;
                                            outHeight = originHeight / 7;
                                        } else if (originHeight > 3000) {
                                            outWidth = originWidth / 6;
                                            outHeight = originHeight / 6;
                                        } else if (originHeight > 2500) {
                                            outWidth = originWidth / 5;
                                            outHeight = originHeight / 5;
                                        } else if (originHeight > 2000) {
                                            outWidth = originWidth / 4;
                                            outHeight = originHeight / 4;
                                        } else if (originHeight > 1500) {
                                            outWidth = originWidth / 3;
                                            outHeight = originHeight / 3;
                                        } else if (originHeight > 1000) {
                                            outWidth = originWidth / 2;
                                            outHeight = originHeight / 2;
                                        } else if (originHeight > 700) {
                                            outWidth = (int) (originWidth / 1.5);
                                            outHeight = (int) (originHeight / 1.5);
                                        } else if (originHeight > 500) {
                                            outWidth = originWidth;
                                            outHeight = originHeight;
                                        }
                                    }else {
                                        if (originWidth > 3500) {
                                            outWidth = originWidth / 7;
                                            outHeight = originHeight / 7;
                                        } else if (originWidth > 3000) {
                                            outWidth = originWidth / 6;
                                            outHeight = originHeight / 6;
                                        } else if (originWidth > 2500) {
                                            outWidth = originWidth / 5;
                                            outHeight = originHeight / 5;
                                        } else if (originWidth > 2000) {
                                            outWidth = originWidth / 4;
                                            outHeight = originHeight / 4;
                                        } else if (originWidth > 1500) {
                                            outWidth = originWidth / 3;
                                            outHeight = originHeight / 3;
                                        } else if (originWidth > 1000) {
                                            outWidth = originWidth / 2;
                                            outHeight = originHeight / 2;
                                        } else if (originWidth > 700) {
                                            outWidth = (int) (originWidth / 1.5);
                                            outHeight = (int) (originHeight / 1.5);
                                        } else if (originWidth > 500) {
                                            outWidth = originWidth;
                                            outHeight = originHeight;
                                        }
                                    }

                                    // 分辨率320x240  码率200-384kbps;
                                    // 分辨率640x480  码率768-1024kbps;
                                    //分辨率1280x720(720p)  码率2048-3072kbps;
                                    //分辨率1920x1080(1080p)  码率5120-8192kbps.
                                    LogUtil.e(TAG,"outWidth:"+outWidth+"--"+"outHeight:"+outHeight+"--"+"bitrate:"+bitrate/5);
                                try {
                                    VideoProcessor.processor(getApplicationContext())
                                            .input(mVideoPath)
                                            .output(currentOutputVideoPath)
                                            .outWidth(outWidth)
                                            .outHeight(outHeight)
                                            //                                            .startTimeMs(startMs)
                                            //                                            .endTimeMs(endMs)
                                            .progressListener(listener)
                                            .bitrate(2048 * 1000)
                                            .process();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProcessingDialog.dismiss();

                                        //压缩成功
                                        File file = new File(currentOutputVideoPath);

                                        LogUtil.e(TAG, "=====mVideoPath压缩成功======" + currentOutputVideoPath);
                                        LogUtil.e(TAG, new File(currentOutputVideoPath).length() / 1024 + "k");
                                        uploadVideoToServer(file);
                                    }
                                });

                            }
                        }).start();


                        //                        startCompress(mVideoPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.e(TAG, "e=" + e.getMessage());
                        //不压缩上传

                        File file = new File(mVideoPath);

                        LogUtil.e(TAG, "=====mVideoPath不压缩上传======" + mVideoPath);
                        LogUtil.e("compress image result:", new File(mVideoPath).length() / 1024 + "k");
                        uploadVideoToServer(file);
                    }
                } else {
                    //选择图片
                    selectImageCommitTemp.clear();
                    selectImageCommitTemp.addAll(selectList);
                    uploadImage(selectImageCommitTemp);
                }
            }


        } else if (i == R.id.iv_select_add_image) {
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData("照片", "视频", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("照片".equals(str)) {
                        mChoseType = 1;
                        if (PermissionUtils.isGranted(PermissionConstants.CAMERA, PermissionConstants.STORAGE)) {
                            photoSelection(true, 2, TAKE_PICTURE, selectList);
                        } else {
                            requestPermission(TAKE_PICTURE, selectList, PermissionConstants.CAMERA, PermissionConstants.STORAGE);
                        }
                    } else if ("视频".equals(str)) {
                        mChoseType = 2;
                        if (PermissionUtils.isGranted(PermissionConstants.CAMERA, PermissionConstants.STORAGE)) {
                            photoSelection(false, 1, TAKE_VIDEO, selectList);
                        } else {
                            requestPermission(TAKE_VIDEO, selectList, PermissionConstants.CAMERA, PermissionConstants.STORAGE);
                        }
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

    VideoProgressListener listener = new VideoProgressListener() {
        @Override
        public void onProgress(float progress) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    ToastUtil.showToast(mContext, "压缩进度:" + progress);
                    mProcessingDialog.setProgress((int) (progress*100));
                }
            });

        }
    };

    private void uploadImage(List<LocalMedia> localMediaList) {
        if (localMediaList.size() > 0) {
            String compressPath = localMediaList.get(0).getCompressPath();
            File file = new File(compressPath);

            LogUtil.e(TAG, "=====compressPath======" + compressPath);
            LogUtil.e("compress image result:", new File(compressPath).length() / 1024 + "k");
            uploadImageToServer(file);
        } else {
            String jsonImage = new Gson().toJson(imageUriList);
            LogUtil.e(TAG, "============jsonImage===============" + jsonImage);
            initUploadVideoData(jsonImage, "0");
            imageUriList.clear();
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

    //上传视频(方法)
    private void uploadVideoToServer(File file_name) {

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "CM003");
        mapHeaders.put("SESSION_ID", mSession_id);
        mPresenter.getUploadVideo(mapHeaders, file_name);


    }

    private void initUploadVideoData(String jsonVideo, String TYPE) {
        /**
         * 属性名称中文名称数据类型数据长度允许为空备注说明
         * TITLE 标题字符型
         * CONTENT 内容字符型
         * IMAGE_URIS 图片地址集合类型
         * USER_ID	用户ID	字符型
         * TYPE	上传媒体的类型	字符型 0:图片1:视频
         */

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());
        Map<String, Object> mapParameters = new HashMap<>(5);
        mapParameters.put("IMAGE_URIS", jsonVideo);
        mapParameters.put("TITLE", edPublishTitle.getText().toString());
        mapParameters.put("CONTENT", edPublishContent.getText().toString());
        mapParameters.put("USER_ID", "");
        mapParameters.put("TYPE", TYPE);

        Map<String, String> mapHeaders = new HashMap<>(2);
        mapHeaders.put("ACTION", "I013");
        mapHeaders.put("SESSION_ID", mSession_id);

        mPresenter.getRequestData(mapHeaders, mapParameters);
    }

    public void refershAddPictureButton() {
        ivSelectAddImage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    /**
     * 获取一组权限方法
     *
     * @param type        功能
     * @param permissions 申请的权限
     */
    public void requestPermission(final int type, final List<LocalMedia> listLocalMedia, final @PermissionConstants.Permission String... permissions) {
        PermissionHelper.request(new PermissionHelper.OnPermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                switch (type) {
                    case TAKE_VIDEO:
                        photoSelection(false, mMaxSelectNum, type, listLocalMedia);
                        break;
                    case TAKE_PICTURE:
                        photoSelection(true, mMaxSelectNum, type, listLocalMedia);
                        break;
                }
            }
        }, permissions);

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
                    //选择后有结果显示recyclerView,无结果则隐藏
                    if (selectList.size() > 0) {
                        ivSelectAddImage.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        ivSelectAddImage.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
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


    private void initVideo() {

        mProcessingDialog = new CustomProgressDialog(this);
        mProcessingDialog.getWindow().setBackgroundDrawableResource(R.color.menu_color);

        mProcessingDialog.setCancelable(false);
        mProcessingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });


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
