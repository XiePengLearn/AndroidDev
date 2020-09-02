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
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
import com.xiaoanjujia.home.composition.me.deposit.DepositActivity;
import com.xiaoanjujia.home.entities.FeedBackResponse;
import com.xiaoanjujia.home.entities.UploadImageResponse;
import com.xiaoanjujia.home.entities.UserBalanceResponse;
import com.xiaoanjujia.home.tool.Api;
import com.xiaoanjujia.home.tool.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xiaoanjujia.common.util.Tool.getTime;

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
    @BindView(R2.id.company_certificate_im)
    ImageView companyCertificateIm;
    @BindView(R2.id.company_certificate_recycler)
    RecyclerView companyCertificateRecycler;
    @BindView(R2.id.edit_post_message_title)
    EditText editPostMessageTitle;
    @BindView(R2.id.edit_post_message_des)
    EditText editPostMessageDes;
    @BindView(R2.id.edit_post_message_single_price)
    EditText editPostMessageSinglePrice;
    @BindView(R2.id.edit_post_message_all_price)
    EditText editPostMessageAllPrice;
    @BindView(R2.id.item_shop_name_tv)
    TextView itemShopNameTv;
    @BindView(R2.id.tv_current_money)
    TextView tvCurrentMoney;
    @BindView(R2.id.tv_current_money_des)
    TextView tvCurrentMoneyDes;
    @BindView(R2.id.item_shop_name_publish_number_tv)
    TextView itemShopNamePublishNumberTv;
    @BindView(R2.id.ll_recharge)
    LinearLayout llRecharge;
    @BindView(R2.id.edit_post_message_contact_name)
    EditText editPostMessageContactName;
    @BindView(R2.id.edit_post_message_phone_number)
    EditText editPostMessagePhoneNumber;
    @BindView(R2.id.edit_post_message_area)
    EditText editPostMessageArea;
    @BindView(R2.id.edit_post_message_area_detailed_address)
    EditText editPostMessageAreaDetailedAddress;
    @BindView(R2.id.uploading_special_certificate_iv)
    ImageView uploadingSpecialCertificateIv;
    @BindView(R2.id.uploading_special_certificate_rv)
    RecyclerView uploadingSpecialCertificateRv;
    @BindView(R2.id.post_message_visiting_time)
    TextView postMessageVisitingTime;
    @BindView(R2.id.post_message_visiting_time_ll)
    LinearLayout postMessageVisitingTimeLl;
    @BindView(R2.id.post_message_leave_time)
    TextView postMessageLeaveTime;
    @BindView(R2.id.post_message_leave_time_ll)
    LinearLayout postMessageLeaveTimeLl;
    @BindView(R2.id.post_message_btn)
    AlphaButton postMessageBtn;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;
    @BindView(R2.id.edit_post_message_id_pic_address)
    EditText editPostMessageIdPicAddress;


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
    private int cate_id;
    private int mId;
    private TimePickerView mPvTime;
    private TimePickerView mPvTimeLeave;
    private String mLeaveTime;
    private String mVisitorTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        cate_id = intent.getIntExtra("cate_id", -1);
        mId = intent.getIntExtra("id", -1);
        initTitle();
        initView();
        initUserBalance();
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
        initTimePicker();
        initTimePickerLeave();

        PostMessageFullyGridLayoutManager manager = new PostMessageFullyGridLayoutManager(PostMessageActivity.this, 3, GridLayoutManager.VERTICAL, false);
        companyCertificateRecycler.setLayoutManager(manager);
        mAdapter = new PostMessageGridImageAdapter(PostMessageActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);
        //        mAdapter.setSelectMax(1);
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
        //        mAdapter2.setSelectMax(1);
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
     * 'id':商户id
     * 'cate_id'类别id----之前是category_id
     * 'top_imgs',顶部九张图片链接
     * 'title':标题
     * 'title_text', 描述
     * 'single_money', 单个金额
     * 'count_money', 预算
     * 'contacts_name', 联系人名字
     * 'contacts_phone',手机号
     * 'region',区域
     * 'detailed_address',详细地址
     * 'advertisement_img',广告图片
     * 'advertisement_url',广告图片链接地址
     * 'release_time',开始时间
     * 'end_time结束时间
     */
    private void initData() {
        String editPostMessageTitleText = editPostMessageTitle.getText().toString().trim();
        String editPostMessageDesText = editPostMessageDes.getText().toString().trim();
        String editPostMessageSinglePriceText = editPostMessageSinglePrice.getText().toString().trim();
        String editPostMessageAllPriceText = editPostMessageAllPrice.getText().toString().trim();
        String editPostMessageContactNameText = editPostMessageContactName.getText().toString().trim();
        String editPostMessagePhoneNumberText = editPostMessagePhoneNumber.getText().toString().trim();
        String editPostMessageAreaText = editPostMessageArea.getText().toString().trim();
        String editPostMessageAreaDetailedAddressText = editPostMessageAreaDetailedAddress.getText().toString().trim();
        String editPostMessageIdPicAddressText = editPostMessageIdPicAddress.getText().toString().trim();
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("id", mId);
        mapParameters.put("cate_id", cate_id);
        mapParameters.put("top_imgs", mCompanyPath);
        mapParameters.put("title", editPostMessageTitleText);
        mapParameters.put("title_text", editPostMessageDesText);
        mapParameters.put("single_money", editPostMessageSinglePriceText);
        mapParameters.put("count_money", editPostMessageAllPriceText);
        mapParameters.put("contacts_name", editPostMessageContactNameText);
        mapParameters.put("contacts_phone", editPostMessagePhoneNumberText);
        mapParameters.put("region", editPostMessageAreaText);
        mapParameters.put("detailed_address", editPostMessageAreaDetailedAddressText);
        mapParameters.put("advertisement_img", mMSpecialImgsPath);
        mapParameters.put("advertisement_url", editPostMessageIdPicAddressText);
        mapParameters.put("release_time", mVisitorTime);
        mapParameters.put("end_time", mLeaveTime);
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

                ToastUtil.showToast(this.getApplicationContext(), "发布信息成功");
                ARouter.getInstance().build("/PublishSuccessActivity/PublishSuccessActivity").greenChannel().navigation(mContext);
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

    private void initUserBalance() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getUserBalance(headersTreeMap, mapParameters);
    }

    @Override
    public void setUserBalance(UserBalanceResponse userBalanceResponse) {
        try {
            int code = userBalanceResponse.getStatus();
            String msg = userBalanceResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                UserBalanceResponse.DataBean data = userBalanceResponse.getData();
                if (data != null) {
                    String user_money = data.getUser_money();
                    if (!Utils.isNull(user_money)) {
                        tvCurrentMoney.setText(String.format("%s元", user_money));
                    }
                }

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

    @OnClick({R2.id.main_title_back, R2.id.company_certificate_im,
            R2.id.uploading_special_certificate_iv, R2.id.post_message_visiting_time_ll,
            R2.id.post_message_leave_time_ll, R2.id.post_message_btn, R2.id.ll_recharge})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
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
        } else if (id == R.id.post_message_visiting_time_ll) {
            mPvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        } else if (id == R.id.post_message_leave_time_ll) {
            mPvTimeLeave.show(view);
        } else if (id == R.id.ll_recharge) {
            Intent intent = new Intent(PostMessageActivity.this, DepositActivity.class);
            startActivity(intent);


        } else if (id == R.id.post_message_btn) {
            String editPostMessageTitleText = editPostMessageTitle.getText().toString().trim();
            String editPostMessageDesText = editPostMessageDes.getText().toString().trim();
            String editPostMessageSinglePriceText = editPostMessageSinglePrice.getText().toString().trim();
            String editPostMessageAllPriceText = editPostMessageAllPrice.getText().toString().trim();
            String editPostMessageContactNameText = editPostMessageContactName.getText().toString().trim();
            String editPostMessagePhoneNumberText = editPostMessagePhoneNumber.getText().toString().trim();
            String editPostMessageAreaText = editPostMessageArea.getText().toString().trim();
            String editPostMessageAreaDetailedAddressText = editPostMessageAreaDetailedAddress.getText().toString().trim();
            String editPostMessageIdPicAddressText = editPostMessageIdPicAddress.getText().toString().trim();

            //先上传公司证件  ,公司证件上传成功后 在上传特殊材料选择图片
            if (selectList.size() == 0) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请选择发布图片");
                return;
            }
            if (Util.isNull(editPostMessageTitleText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入发布标题");
                return;
            }
            if (Util.isNull(editPostMessageDesText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入描述");
                return;
            }
            if (Util.isNull(editPostMessageSinglePriceText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入单个价格");
                return;
            }
            if (Util.isNull(editPostMessageAllPriceText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入发布金额");
                return;
            }
            if (Util.isNull(editPostMessageContactNameText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入联系人");
                return;
            }
            if (Util.isNull(editPostMessagePhoneNumberText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入手机号");
                return;
            }
            if (Util.isNull(editPostMessageAreaText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入您所在区域");
                return;
            }
            if (Util.isNull(editPostMessageAreaDetailedAddressText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入您的详细地址");
                return;
            }
            if (selectList2.size() == 0) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请选择插入广告图片");
            }
            if (Util.isNull(editPostMessageIdPicAddressText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入点击广告图片跳转链接地址");
                return;
            }

            String postMessageVisitingTimeText = postMessageVisitingTime.getText().toString().trim();
            String postMessageLeaveTimeText = postMessageLeaveTime.getText().toString().trim();
            if (Util.isNull(postMessageVisitingTimeText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入发布时间");
                return;
            }
            if (Util.isNull(postMessageLeaveTimeText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请输入结束时间");
                return;
            }
            uploadPictureToServer(selectList);
        }
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        //时间选择器

        mPvTime = new TimePickerBuilder(PostMessageActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                Toast.makeText(PostMessageActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mVisitorTime = Utils.getTime(date);
                postMessageVisitingTime.setText(mVisitorTime);
            }
        })
                .setItemVisibleCount(5)
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }

    private void initTimePickerLeave() {//Dialog 模式下，在底部弹出
        //时间选择器
        mPvTimeLeave = new TimePickerBuilder(PostMessageActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                Toast.makeText(PostMessageActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mLeaveTime = Utils.getTime(date);
                postMessageLeaveTime.setText(mLeaveTime);
            }
        })
                .setItemVisibleCount(5)
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
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
            mMaxSelectNum = 9;
            //            mAdapter.setSelectMax(9);
            // 单独拍照和相册
            chooseMode = PictureMimeType.ofImage();
            selectPictureSetting(isCameraButton, selectList, 9);
            mAdapter.notifyDataSetChanged();
        } else {
            //*上传特殊材料：
            isCameraButton = true;
            mMaxSelectNum = 1;
            //            mAdapter2.setSelectMax(1);
            // 单独拍照和相册
            chooseMode = PictureMimeType.ofImage();
            selectPictureSetting(isCameraButton, selectList2, 1);
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
            mMaxSelectNum = 9;
            selectPictureSetting(isCameraButton, selectList, 9);

        }

    };
    private PostMessageGridImageAdapter2.onAddPicClickListener onAddPicClickListener2 = new PostMessageGridImageAdapter2.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            mMaxSelectNum = 1;
            isfirst = false;
            selectPictureSetting(isCameraButton, selectList2, 1);

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
