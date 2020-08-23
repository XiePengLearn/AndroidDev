package com.xiaoanjujia.home.composition.community;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.rmondjone.camera.CameraActivity;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.me.merchants.GlideEngine;
import com.xiaoanjujia.home.entities.CommunityDetailsResponse;
import com.xiaoanjujia.home.entities.LogExamineResponse;
import com.xiaoanjujia.home.entities.LogRefuseResponse;
import com.xiaoanjujia.home.tool.Api;
import com.xiaoanjujia.home.tool.Util;

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
@Route(path = "/compositionDetailActivity/compositionDetailActivity")
public class CompositionDetailActivity extends BaseActivity implements CompositionDetailContract.View {
    @Inject
    CompositionDetailPresenter mPresenter;
    private static final String TAG = "CompositionDetailActivity";

    public static final String KEY_IMAGE_PATH = "imagePath";
    @BindView(R2.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R2.id.main_title_back)
    ImageView mainTitleBack;
    @BindView(R2.id.main_title_text)
    TextView mainTitleText;
    @BindView(R2.id.main_title_right)
    TextView mainTitleRight;
    @BindView(R2.id.main_title_container)
    RelativeLayout mainTitleContainer;
    @BindView(R2.id.community_title_text_tv)
    TextView communityTitleTextTv;
    @BindView(R2.id.invitation_particulars_of_matter_ll)
    LinearLayout invitationParticularsOfMatterLl;
    @BindView(R2.id.record_detail_rv)
    RecyclerView recordDetailRv;
    @BindView(R2.id.staff_take_picture_layout_list_ll)
    LinearLayout staffTakePictureLayoutListLl;
    @BindView(R2.id.item_composition_serve_tv)
    TextView itemCompositionServeTv;
    @BindView(R2.id.item_composition_address_tv)
    TextView itemCompositionAddressTv;
    @BindView(R2.id.item_composition_btn_details)
    TextView itemCompositionBtnDetails;
    @BindView(R2.id.item_composition_btn_comment)
    TextView itemCompositionBtnComment;
    @BindView(R2.id.item_composition_btn_merchant)
    TextView itemCompositionBtnMerchant;
    @BindView(R2.id.item_composition_image_ad)
    ImageView itemCompositionImageAd;
    @BindView(R2.id.edit_item_composition_comment_content)
    EditText editItemCompositionCommentContent;
    @BindView(R2.id.publish_composition_comment_content_btn)
    AlphaButton publishCompositionCommentContentBtn;
    @BindView(R2.id.composition_comment_status_1)
    AlphaButton compositionCommentStatus1;
    @BindView(R2.id.composition_comment_status_2)
    AlphaButton compositionCommentStatus2;
    @BindView(R2.id.composition_comment_status_3)
    AlphaButton compositionCommentStatus3;


    private List<LocalMedia> selectList = new ArrayList<>();
    private CompositionDetailGridImageAdapter mAdapter;
    private int themeId;
    private int mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composition_detail);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        initView();
        requestData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("审核通知");
        mainTitleRight.setText("");
    }

    private void initView() {
        DaggerCompositionDetailActivityComponent.builder()
                .appComponent(getAppComponent())
                .compositionDetailPresenterModule(new CompositionDetailPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        CompositionDetailGridLayoutManager manager = new CompositionDetailGridLayoutManager(CompositionDetailActivity.this, 3, GridLayoutManager.VERTICAL, false);
        recordDetailRv.setLayoutManager(manager);
        mAdapter = new CompositionDetailGridImageAdapter(CompositionDetailActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);
        //        mAdapter.setSelectMax(5);
        recordDetailRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CompositionDetailGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                LogUtil.e(TAG, "长度---->" + selectList.size());
                if (selectList.size() > 0) {
                    PictureSelector.create(CompositionDetailActivity.this)
                            .themeStyle(themeId) // xml设置主题
                            //                                .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                            //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .loadImageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, selectList);
                }
            }
        });
    }

    private CompositionDetailGridImageAdapter.onAddPicClickListener1 onAddPicClickListener = new CompositionDetailGridImageAdapter.onAddPicClickListener1() {
        @Override
        public void onAddPicClick1() {
            CameraActivity.startMe(CompositionDetailActivity.this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);

        }

    };

    public void refershAddPictureButton() {
        staffTakePictureLayoutListLl.setVisibility(View.GONE);
    }

    private void logExamineData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("id", String.valueOf(mId));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getLogExamineData(headersTreeMap, mapParameters);
    }

    private void logRefuseData() {
        //        id:日志id
        //        refuse_text:拒绝理由
        //        String editRejectLayoutText = editRejectLayout.getText().toString().trim();
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("id", String.valueOf(mId));
        //        mapParameters.put("refuse_text", editRejectLayoutText);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getLogRefuseData(headersTreeMap, mapParameters);
    }


    private void requestData() {


        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("id", String.valueOf(mId));

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(CommunityDetailsResponse mCommunityDetailsResponse) {
        try {
            int code = mCommunityDetailsResponse.getStatus();
            String msg = mCommunityDetailsResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                CommunityDetailsResponse.DataBean data = mCommunityDetailsResponse.getData();
                if (data != null) {
                    List<String> log_imgs = data.getTop_imgs();
                    if (log_imgs != null) {
                        for (int i = 0; i < log_imgs.size(); i++) {
                            LocalMedia localMedia = new LocalMedia();
                            localMedia.setPath(log_imgs.get(i));
                            selectList.add(localMedia);
                        }

                        mAdapter.notifyDataSetChanged();
                    }

                    /**
                     * "id": 5,
                     *         "community_id": 3,
                     *         "title": "啊啊啊1111骚点的",
                     *         "top_imgs": [
                     *             "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *             "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *             "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg"
                     *         ],
                     *         "region": "北京市",
                     *         "detailed_address": "朝阳区1022",
                     *         "advertisement_img": "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *         "advertisement_url": "https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg",
                     *         "title_text": "啊啊啊是的撒多所多多",
                     *         "contacts_phone": "18635805566",
                     *         "name_acronym": "小安",
                     *         "shop_name": "小安居家",
                     *         "shop_phone": "1863805566",
                     *         "shop_order_count": 12
                     */
                    String title_text = data.getTitle_text();
                    if (!Utils.isNull(title_text)) {
                        communityTitleTextTv.setText(title_text);
                    }
                    String region = data.getRegion();
                    if (!Utils.isNull(region)) {
                        itemCompositionServeTv.setText(String.format("服务于%s", region));
                    }

                    String detailed_address = data.getDetailed_address();
                    if (!Utils.isNull(detailed_address)) {
                        itemCompositionAddressTv.setText(detailed_address);
                    }

                    String advertisement_img = data.getAdvertisement_img();
                    if (!Utils.isNull(advertisement_img)) {
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .placeholder(R.color.app_color_f6)
                                .diskCacheStrategy(DiskCacheStrategy.ALL);
                        Glide.with(CompositionDetailActivity.this)
                                .load(advertisement_img)
                                .apply(options)
                                .into(itemCompositionImageAd);
                    }
                    //                    String week = data.getWeek();
                    //                    if (!Utils.isNull(week)) {
                    //                        itemRecordDetailWeekDateTv.setText(week);
                    //                    }
                    //                    String name = data.getName();
                    //                    if (!Utils.isNull(name)) {
                    //                        itemRecordDetailPublisherTv.setText(name);
                    //                    }
                    //                    String refuse_text = data.getRefuse_text();
                    //                    if (!Utils.isNull(refuse_text)) {
                    //                        itemRecordDetailRefuseTextTv.setText(refuse_text);
                    //                    }
                    //                    int examinestatus = data.getExaminestatus();
                    //                    //examinestatus:0是未审核1是通过2被拒绝
                    //                    if (examinestatus == 1) {
                    //                        agreeLayoutBtn.setVisibility(View.INVISIBLE);
                    //                        rejectLayoutBtn.setText("审核已通过");
                    //                        rejectLayoutBtn.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_gray));
                    //                        rejectLayoutBtn.setClickable(false);
                    //                    } else if (examinestatus == 2) {
                    //                        agreeLayoutBtn.setVisibility(View.INVISIBLE);
                    //                        rejectLayoutBtn.setText("审核已拒绝");
                    //                        rejectLayoutBtn.setBackground(getResources().getDrawable(R.drawable.bg_shap_button_gray));
                    //                        rejectLayoutBtn.setClickable(false);
                    //                    } else {
                    //                        //未审核
                    //                    }


                }


            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
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
    public void setLogExamineData(LogExamineResponse mLogExamineResponse) {
        try {
            int code = mLogExamineResponse.getStatus();
            String msg = mLogExamineResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }
                ARouter.getInstance().build("/AuditSuccessActivity/AuditSuccessActivity").greenChannel().navigation(mContext);
                finish();
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
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
    public void setLogRefuseData(LogRefuseResponse mLogRefuseResponse) {
        try {
            int code = mLogRefuseResponse.getStatus();
            String msg = mLogRefuseResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }
                finish();
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(this);
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


    @OnClick({R2.id.main_title_back, R2.id.item_composition_btn_details, R2.id.item_composition_btn_comment,
            R2.id.item_composition_btn_merchant, R2.id.publish_composition_comment_content_btn, R2.id.composition_comment_status_1,
            R2.id.composition_comment_status_2, R2.id.composition_comment_status_3, R2.id.item_composition_image_ad})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.item_composition_btn_details) {

        } else if (id == R.id.item_composition_btn_comment) {

        } else if (id == R.id.item_composition_btn_merchant) {

        } else if (id == R.id.publish_composition_comment_content_btn) {
            String editItemCompositionCommentContentText = editItemCompositionCommentContent.getText().toString().trim();

            if (Util.isNull(editItemCompositionCommentContentText)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "请写出您的评价内容");
                return;
            }
            //            logRefuseData();
        } else if (id == R.id.composition_comment_status_1) {

        } else if (id == R.id.composition_comment_status_2) {

        } else if (id == R.id.composition_comment_status_3) {

        } else if (id == R.id.item_composition_image_ad) {
            //ad

        }
    }
}
