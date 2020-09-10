package com.xiaoanjujia.home.composition.unlocking.permit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.PermitResponse;
import com.xiaoanjujia.home.tool.Api;
import com.yzq.zxinglibrary.encode.CodeCreator;

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
@Route(path = "/visitorActivity/visitorActivity")
public class PermitActivity extends BaseActivity implements PermitContract.View {
    @Inject
    PermitPresenter mPresenter;
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
    @BindView(R2.id.invitation_name)
    TextView invitationName;
    @BindView(R2.id.invitation_leave_time_ll)
    LinearLayout invitationLeaveTimeLl;
    @BindView(R2.id.invitation_card_number)
    TextView invitationCardNumber;
    @BindView(R2.id.invitation_vis_time)
    TextView invitationVisTime;
    @BindView(R2.id.invitation_leave_time)
    TextView invitationLeaveTime;
    @BindView(R2.id.qr_code_iv)
    ImageView qrCodeIv;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;

    private String orderId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        String qrCode = intent.getStringExtra("qrCode");
        String receptionistName = intent.getStringExtra("receptionistName");
        orderId = intent.getStringExtra("orderId");
        initView();
        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("访客通行证");
//        mainTitleRight.setImageDrawable(getResources().getDrawable(R.drawable.close_white));
    }

    private void initView() {
        DaggerPermitActivityComponent.builder()
                .appComponent(getAppComponent())
                .permitPresenterModule(new PermitPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    private void initData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("orderId", orderId);

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(PermitResponse mPermitResponse) {
        try {
            int code = Integer.parseInt(mPermitResponse.getStatus());
            String msg = mPermitResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<PermitResponse.DataBean> dataList = mPermitResponse.getData();
                if (dataList != null && dataList.size() > 0) {
                    PermitResponse.DataBean dataBean = dataList.get(0);
                    /**
                     * "gender": 1,
                     * 		"nation": 1,
                     * 		"orderId": "538a8726-f371-11ea-9cf0-cf99ee937498",
                     * 		"plateNo": "京5666",
                     * 		"visitStartTime": "2020-09-13T22:22:35+08:00",
                     * 		"visitPurpose": "啦啦啦",
                     * 		"customa": "",
                     * 		"certificateNo": "411425199103199030",
                     * 		"phoneNo": "15601276550",
                     * 		"visitEndTime": "2020-09-14T15:22:35+08:00",
                     * 		"verificationCode": "5570",
                     * 		"privilegeGroupNames": ["访客权限门禁点"],
                     * 		"visitorName": "谢鹏",
                     * 		"QRCode": "VjAwMSupfzP4UohLB//laW9u1Sst/DpaOUILQTrQPTwlHsXwq2yT2bjGHl/A38Yefx8XTfQ=",
                     * 		"receptionistName": "老谢",
                     * 		"appointRecordId": "bb838787f6b540bdbd33cb20254801a4",
                     * 		"receptionistCode": "01101002010010100102",
                     * 		"visitorStatus": 1,
                     * 		"receptionistId": "77edf8446c7c448e914caf14eeaf9f20",
                     * 		"certificateType": 111,
                     * 		"visitorId": "538a8726-f371-11ea-9cf0-cf99ee937498"
                     */
                    String visitorName = dataBean.getVisitorName();//姓名
                    String phoneNo = dataBean.getPhoneNo();//车牌
                    String plateNo = dataBean.getPlateNo();//车牌
                    String visitStartTime = dataBean.getVisitStartTime();
                    String visitEndTime = dataBean.getVisitEndTime();

                    if (!Utils.isNull(visitorName)) {
                        invitationName.setText(visitorName);
                    }
                    if (!Utils.isNull(plateNo)) {
                        invitationCardNumber.setText(plateNo);
                    }
                    if (!Utils.isNull(visitStartTime)) {
                        if (visitStartTime.contains("+")) {
                            String[] split = visitStartTime.split("\\+");
                            String timeDate = split[0];
                            if (timeDate.contains("T")) {
                                String[] ts = timeDate.split("T");
                                invitationVisTime.setText(ts[0] + "  " + ts[1]);
                            } else {
                                invitationVisTime.setText(timeDate);
                            }
                        } else {
                            invitationVisTime.setText(visitStartTime);
                        }

                    }
                    if (!Utils.isNull(visitEndTime)) {
                        if (visitEndTime.contains("+")) {
                            String[] split = visitEndTime.split("\\+");
                            String timeDate = split[0];
                            if (timeDate.contains("T")) {
                                String[] ts = timeDate.split("T");
                                invitationLeaveTime.setText(ts[0] + "  " + ts[1]);
                            } else {
                                invitationLeaveTime.setText(timeDate);
                            }
                        } else {
                            invitationLeaveTime.setText(visitEndTime);
                        }
                    }
                    String qrCode = dataBean.getQRCode();
                    Bitmap bitmap = CodeCreator.createQRCode(qrCode, 400, 400, null);
                    qrCodeIv.setImageBitmap(bitmap);

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
            finish();
        }
    }


}
