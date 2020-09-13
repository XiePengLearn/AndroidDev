package com.xiaoanjujia.home.composition.unlocking.reservation_record_details;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.rmondjone.camera.CameraActivity;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.NoDoubleClickUtils;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.me.merchants.GlideEngine;
import com.xiaoanjujia.home.composition.tenement.detail.RecordDetailGridLayoutManager;
import com.xiaoanjujia.home.composition.unlocking.permit.PermitActivity;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;
import com.xiaoanjujia.home.tool.Api;

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
@Route(path = "/reservationRecordDetailsActivity/reservationRecordDetailsActivity")
public class ReservationRecordDetailsActivity extends BaseActivity implements ReservationRecordDetailsContract.View {
    @Inject
    ReservationRecordDetailsPresenter mPresenter;
    private static final String TAG = "SelectHousingActivity";
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
    @BindView(R2.id.huzhu_tv)
    TextView huzhuTv;
    @BindView(R2.id.item_lai_fang_btn_status)
    AlphaButton mAlphaButton;
    @BindView(R2.id.edit_details_suo_zai_xiao_qu)
    TextView editDetailsSuoZaiXiaoQu;
    @BindView(R2.id.edit_details_vis_name)
    TextView editDetailsVisName;
    @BindView(R2.id.edit_details_vis_dui_xiang)
    TextView editDetailsVisDuiXiang;
    @BindView(R2.id.edit_details_vis_phone)
    TextView editDetailsVisPhone;
    @BindView(R2.id.edit_details_visiting_time_tv)
    TextView editDetailsVisitingTimeTv;
    @BindView(R2.id.invitation_visiting_time_ll)
    LinearLayout invitationVisitingTimeLl;
    @BindView(R2.id.edit_details_leave_time_tv)
    TextView editDetailsLeaveTimeTv;
    @BindView(R2.id.invitation_leave_time_ll)
    LinearLayout invitationLeaveTimeLl;
    @BindView(R2.id.edit_invitation_thing)
    TextView editInvitationThing;
    @BindView(R2.id.uploading_special_certificate_iv)
    ImageView uploadingSpecialCertificateIv;
    @BindView(R2.id.uploading_special_certificate_rv)
    RecyclerView recordDetailRv;
    @BindView(R2.id.edit_details_sex_tv)
    TextView editDetailsSexTv;
    @BindView(R2.id.invitation_visiting_gender_ll)
    LinearLayout invitationVisitingGenderLl;
    @BindView(R2.id.edit_details_id_number_tv)
    TextView editDetailsIdNumberTv;
    @BindView(R2.id.edit_details_card_number_tv)
    TextView editDetailsCardNumberTv;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;
    private String mOrderId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_record_details);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        int visitorStatus = intent.getIntExtra("visitorStatus", -1);
        mOrderId = intent.getStringExtra("orderId");

        if (visitorStatus == 0) {
            mAlphaButton.setText("待审核");
        } else if (visitorStatus == 1) {
            mAlphaButton.setText("正常");
        } else if (visitorStatus == 2) {
            mAlphaButton.setText("迟到");
        } else if (visitorStatus == 3) {
            mAlphaButton.setText("失效");
        } else if (visitorStatus == 4) {
            mAlphaButton.setText("审核退回");
        } else if (visitorStatus == 5) {
            mAlphaButton.setText("超期自动签离");
        } else if (visitorStatus == 6) {
            mAlphaButton.setText("已签离");
        } else if (visitorStatus == 7) {
            mAlphaButton.setText("超期未签离");
        } else if (visitorStatus == 8) {
            mAlphaButton.setText("已到达");
        } else if (visitorStatus == 9) {
            mAlphaButton.setText("审核失效");
        } else if (visitorStatus == 10) {
            mAlphaButton.setText("邀约中");
        } else if (visitorStatus == 11) {
            mAlphaButton.setText("邀约失效");
        }

        String visitorName = intent.getStringExtra("visitorName");
        String receptionistName = intent.getStringExtra("receptionistName");


        if (!Utils.isNull(visitorName)) {
            editDetailsVisName.setText(visitorName);
        } else {
            editDetailsVisName.setText("  -  ");
        }
        if (!Utils.isNull(receptionistName)) {
            editDetailsVisDuiXiang.setText(receptionistName);
        } else {
            editDetailsVisDuiXiang.setText("  -  ");
        }

        String phoneNo = intent.getStringExtra("phoneNo");

        if (!Utils.isNull(phoneNo)) {
            editDetailsVisPhone.setText(phoneNo);
        } else {
            editDetailsVisPhone.setText("  -  ");
        }
        String visitStartTime = intent.getStringExtra("visitStartTime");
        String visitEndTime = intent.getStringExtra("visitEndTime");

        if (!Utils.isNull(visitStartTime)) {
            if (visitStartTime.contains("+")) {
                String[] split = visitStartTime.split("\\+");
                String timeDate = split[0];
                if (timeDate.contains("T")) {
                    String[] ts = timeDate.split("T");
                    editDetailsVisitingTimeTv.setText(ts[0] + "  " + ts[1]);
                } else {
                    editDetailsVisitingTimeTv.setText(timeDate);
                }
            } else {
                editDetailsVisitingTimeTv.setText(visitStartTime);
            }
        } else {
            editDetailsVisitingTimeTv.setText("  -  ");
        }

        if (!Utils.isNull(visitEndTime)) {
            if (visitEndTime.contains("+")) {
                String[] split = visitEndTime.split("\\+");
                String timeDate = split[0];
                if (timeDate.contains("T")) {
                    String[] ts = timeDate.split("T");
                    editDetailsLeaveTimeTv.setText(ts[0] + "  " + ts[1]);
                } else {
                    editDetailsLeaveTimeTv.setText(timeDate);
                }
            } else {
                editDetailsLeaveTimeTv.setText(visitEndTime);
            }
        } else {
            editDetailsLeaveTimeTv.setText("  -  ");
        }
        String visitPurpose = intent.getStringExtra("visitPurpose");

        if (!Utils.isNull(visitPurpose)) {
            editInvitationThing.setText(visitPurpose);
        } else {
            editInvitationThing.setText("  -  ");
        }
        String picUri = intent.getStringExtra("picUri");

        RequestOptions options = RequestOptions
                .bitmapTransform(new CircleCrop());

        if (!Utils.isNull(picUri)) {
            if (!picUri.startsWith("http")) {
                picUri = MainDataManager.HK_ROOT_URL_PIC + picUri;
            }
            //头像
//            Glide.with(mContext)
//                    .load(picUri)
//                    .apply(options)
//                    .into(uploadingSpecialCertificateIv);
            LocalMedia localMedia = new LocalMedia();
            localMedia.setPath(picUri);
            selectList.add(localMedia);
        }

        int gender = intent.getIntExtra("gender", 0);
        if (gender == 1) {
            //1：男
            editDetailsSexTv.setText("男");
        } else if (gender == 2) {
            //2：女
            editDetailsSexTv.setText("女");
        } else {
            //0：未知
            editDetailsSexTv.setText("未知");
        }
        String certificateNo = intent.getStringExtra("certificateNo");
        String plateNo = intent.getStringExtra("plateNo");

        if (!Utils.isNull(certificateNo)) {
            editDetailsIdNumberTv.setText(certificateNo);
        } else {
            editDetailsIdNumberTv.setText("  -  ");
        }
        if (!Utils.isNull(plateNo)) {
            editDetailsCardNumberTv.setText(plateNo);
        } else {
            editDetailsCardNumberTv.setText("  -  ");
        }

        initView();
        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("访客审核");
        mainTitleRight.setText("访客通行证");
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private ReservationRecordDetailGridImageAdapter mAdapter;
    private int themeId;

    private void initView() {
        DaggerReservationRecordDetailsComponent.builder()
                .appComponent(getAppComponent())
                .reservationRecordDetailsPresenterModule(new ReservationRecordDetailsPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        RecordDetailGridLayoutManager manager = new RecordDetailGridLayoutManager(ReservationRecordDetailsActivity.this, 1, GridLayoutManager.VERTICAL, false);
        recordDetailRv.setLayoutManager(manager);
        mAdapter = new ReservationRecordDetailGridImageAdapter(ReservationRecordDetailsActivity.this, onAddPicClickListener);
        mAdapter.setList(selectList);
        //        mAdapter.setSelectMax(5);
        recordDetailRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ReservationRecordDetailGridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                LogUtil.e(TAG, "长度---->" + selectList.size());
                if (selectList.size() > 0) {
                    PictureSelector.create(ReservationRecordDetailsActivity.this)
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

    private ReservationRecordDetailGridImageAdapter.onAddPicClickListener1 onAddPicClickListener = new ReservationRecordDetailGridImageAdapter.onAddPicClickListener1() {
        @Override
        public void onAddPicClick1() {
            CameraActivity.startMe(ReservationRecordDetailsActivity.this, 2005, CameraActivity.MongolianLayerType.IDCARD_POSITIVE);

        }

    };

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("paramName", "phoneNo");
        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    public void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse) {
        try {
            String code = mVisitorPersonInfoResponse.getStatus();
            String msg = mVisitorPersonInfoResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<VisitorPersonInfoResponse.DataBean> data = mVisitorPersonInfoResponse.getData();
                if (data != null && data.size() > 0) {
                    VisitorPersonInfoResponse.DataBean dataBean = data.get(0);
                    if (dataBean != null) {
                        String orgName = dataBean.getOrgName();
                        if (!Utils.isNull(orgName)) {
                            editDetailsSuoZaiXiaoQu.setText(orgName);
                        } else {
                            editDetailsSuoZaiXiaoQu.setText("  -  ");
                        }
                    }
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(ReservationRecordDetailsActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(ReservationRecordDetailsActivity.this, msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(ReservationRecordDetailsActivity.this, "解析数据失败");
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


    @OnClick({R2.id.main_title_back, R2.id.main_title_right})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.main_title_right) {
            if (!NoDoubleClickUtils.isDoubleClick()) {
                Intent intent = new Intent(ReservationRecordDetailsActivity.this, PermitActivity.class);
                intent.putExtra("orderId", mOrderId);
                startActivity(intent);
            }
        }
    }
}
