package com.xiaoanjujia.home.composition.unlocking.visitor_audit;

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
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/VisitorAuditActivity/VisitorAuditActivity")
public class VisitorAuditActivity extends BaseActivity implements VisitorAuditContract.View {
    @Inject
    VisitorAuditPresenter mPresenter;
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
    @BindView(R2.id.invitation_particulars_of_matter)
    TextView invitationParticularsOfMatter;
    @BindView(R2.id.invitation_particulars_of_matter_ll)
    LinearLayout invitationParticularsOfMatterLl;
    @BindView(R2.id.invitation_visiting_time)
    TextView invitationVisitingTime;
    @BindView(R2.id.invitation_visiting_time_ll)
    LinearLayout invitationVisitingTimeLl;
    @BindView(R2.id.invitation_leave_time)
    TextView invitationLeaveTime;
    @BindView(R2.id.invitation_leave_time_ll)
    LinearLayout invitationLeaveTimeLl;
    @BindView(R2.id.edit_invitation_visiting_name)
    TextView editInvitationVisitingName;
    @BindView(R2.id.invitation_visiting_gender)
    TextView invitationVisitingGender;
    @BindView(R2.id.invitation_visiting_gender_ll)
    LinearLayout invitationVisitingGenderLl;
    @BindView(R2.id.edit_invitation_visiting_phone)
    TextView editInvitationVisitingPhone;
    @BindView(R2.id.edit_invitation_visiting_license_number)
    TextView editInvitationVisitingLicenseNumber;
    @BindView(R2.id.invitation_visiting_id_type)
    TextView invitationVisitingIdType;
    @BindView(R2.id.invitation_visiting_id_type_ll)
    LinearLayout invitationVisitingIdTypeLl;
    @BindView(R2.id.edit_invitation_visiting_id_number)
    TextView editInvitationVisitingIdNumber;
    @BindView(R2.id.edit_invitation_visiting_units)
    TextView editInvitationVisitingUnits;
    @BindView(R2.id.invitation_visiting_cancel)
    AlphaButton invitationVisitingCancel;
    @BindView(R2.id.invitation_visiting_confirm)
    AlphaButton invitationVisitingConfirm;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_audit);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

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
    }

    private void initView() {
        DaggerVisitorAuditActivityComponent.builder()
                .appComponent(getAppComponent())
                .visitorAuditPresenterModule(new VisitorAuditPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    private void initData() {

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(LoginResponse loginResponse) {
        try {
            int code = loginResponse.getStatus();
            String msg = loginResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LoginResponse.DataBean data = loginResponse.getData();


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

    @OnClick({R2.id.main_title_back, R2.id.invitation_visiting_cancel, R2.id.invitation_visiting_confirm})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.register_success_entry) {

        } else if (id == R.id.invitation_visiting_confirm) {

        }
    }


}
