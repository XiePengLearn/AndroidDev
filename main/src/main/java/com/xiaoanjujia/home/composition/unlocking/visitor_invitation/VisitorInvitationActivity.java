package com.xiaoanjujia.home.composition.unlocking.visitor_invitation;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.unlocking.dialog.ChoiceGenderDialog;
import com.xiaoanjujia.home.composition.unlocking.dialog.ChoiceIdTypeDialog;
import com.xiaoanjujia.home.composition.unlocking.dialog.ChoiceRriginIncidentDialog;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xiaoanjujia.common.util.Tool.getTime;

/**
 * @author xiepeng
 */
@Route(path = "/visitorInvitationActivity/visitorInvitationActivity")
public class VisitorInvitationActivity extends BaseActivity implements VisitorInvitationContract.View {
    @Inject
    VisitorInvitationPresenter mPresenter;
    private static final String TAG = "VisitorActivity";
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
    EditText editInvitationVisitingName;
    @BindView(R2.id.invitation_visiting_gender)
    TextView invitationVisitingGender;
    @BindView(R2.id.invitation_visiting_gender_ll)
    LinearLayout invitationVisitingGenderLl;
    @BindView(R2.id.edit_invitation_visiting_phone)
    EditText editInvitationVisitingPhone;
    @BindView(R2.id.edit_invitation_visiting_license_number)
    EditText editInvitationVisitingLicenseNumber;
    @BindView(R2.id.invitation_visiting_id_type)
    TextView invitationVisitingIdType;
    @BindView(R2.id.invitation_visiting_id_type_ll)
    LinearLayout invitationVisitingIdTypeLl;
    @BindView(R2.id.edit_invitation_visiting_id_number)
    EditText editInvitationVisitingIdNumber;
    @BindView(R2.id.edit_invitation_visiting_units)
    EditText editInvitationVisitingUnits;
    @BindView(R2.id.register_success_entry)
    AlphaButton registerSuccessEntry;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;
    private ChoiceGenderDialog mChoiceGenderDialog;
    private ChoiceRriginIncidentDialog mChoiceRriginIncidentDialog;
    private ChoiceIdTypeDialog mChoiceIdTypeDialog;
    private TimePickerView mPvTime;
    private TimePickerView mPvTimeLeave;
    private String mLeaveTime;
    private String mVisitorTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_invitation);
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
        mainTitleText.setText("访客邀约");
    }

    private void initView() {
        DaggerVisitorInvitationActivityComponent.builder()
                .appComponent(getAppComponent())
                .visitorInvitationPresenterModule(new VisitorInvitationPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        initTimePicker();
        initTimePickerLeave();

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

    @OnClick({R2.id.main_title_back, R2.id.invitation_particulars_of_matter_ll, R2.id.invitation_visiting_time_ll, R2.id.invitation_leave_time_ll, R2.id.invitation_visiting_gender_ll, R2.id.invitation_visiting_id_type_ll, R2.id.register_success_entry})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {

        } else if (id == R.id.invitation_particulars_of_matter_ll) {
            String particularsType = invitationParticularsOfMatter.getText().toString();
            if (!Utils.isNull(particularsType) && particularsType.equals("其他")) {
                showChoiceRriginIncidentDialog(0);
            } else if (!Utils.isNull(particularsType) && particularsType.equals("工作")) {
                showChoiceRriginIncidentDialog(1);
            } else if (!Utils.isNull(particularsType) && particularsType.equals("拜访")) {
                showChoiceRriginIncidentDialog(2);
            } else if (!Utils.isNull(particularsType) && particularsType.equals("外卖")) {
                showChoiceRriginIncidentDialog(3);
            } else if (!Utils.isNull(particularsType) && particularsType.equals("快递")) {
                showChoiceRriginIncidentDialog(4);
            } else {
                showChoiceRriginIncidentDialog(-1);
            }

        } else if (id == R.id.invitation_visiting_time_ll) {
            mPvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        } else if (id == R.id.invitation_leave_time_ll) {
            mPvTimeLeave.show(view);
        } else if (id == R.id.invitation_visiting_gender_ll) {
            String genderType = invitationVisitingGender.getText().toString();
            if (!Utils.isNull(genderType)) {
                showChoiceGenderDialog(genderType.equals("男") ? 0 : 1);
            } else {
                showChoiceGenderDialog(-1);
            }

        } else if (id == R.id.invitation_visiting_id_type_ll) {
            String visitingIdType = invitationVisitingIdType.getText().toString();
            if (!Utils.isNull(visitingIdType) && visitingIdType.equals("居民身份证")) {
                showChoiceIdTypeDialog(0);
            } else if (!Utils.isNull(visitingIdType) && visitingIdType.equals("警官证")) {
                showChoiceIdTypeDialog(1);
            } else if (!Utils.isNull(visitingIdType) && visitingIdType.equals("军官证")) {
                showChoiceIdTypeDialog(2);
            } else if (!Utils.isNull(visitingIdType) && visitingIdType.equals("护照")) {
                showChoiceIdTypeDialog(3);
            } else {
                showChoiceIdTypeDialog(-1);
            }

        } else if (id == R.id.register_success_entry) {

        }
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        //时间选择器

        mPvTime = new TimePickerBuilder(VisitorInvitationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mVisitorTime = Utils.getTime(date);
                invitationVisitingTime.setText(mVisitorTime);
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
        mPvTimeLeave = new TimePickerBuilder(VisitorInvitationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mLeaveTime = Utils.getTime(date);
                invitationLeaveTime.setText(mLeaveTime);
            }
        })
                .setItemVisibleCount(5)
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }

    private void showChoiceGenderDialog(int genderType) {
        if (mChoiceGenderDialog == null) {
            mChoiceGenderDialog = new ChoiceGenderDialog(this, choiceGenderDialogOnClickListener, new ChoiceGenderDialog.GenderCallback() {
                @Override
                public void genderSelect(int gender) {
                    invitationVisitingGender.setText(gender == 0 ? "男" : "女");
                    cancelChoiceGenderDialog();
                }

                @Override
                public void saveGenderSuccess(int gender) {

                }
            });
        }
        mChoiceGenderDialog.selectGender(genderType);
        mChoiceGenderDialog.show();
    }

    public void cancelChoiceGenderDialog() {
        if (mChoiceGenderDialog != null) {
            mChoiceGenderDialog.cancel();
        }
    }

    View.OnClickListener choiceGenderDialogOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private void showChoiceRriginIncidentDialog(int genderType) {
        if (mChoiceRriginIncidentDialog == null) {
            mChoiceRriginIncidentDialog = new ChoiceRriginIncidentDialog(this, new ChoiceRriginIncidentDialog.IncidentCallback() {
                @Override
                public void genderSelect(int numIncident) {
                    if (numIncident == 0) {
                        invitationParticularsOfMatter.setText("其他");
                    } else if ((numIncident == 1)) {
                        invitationParticularsOfMatter.setText("工作");
                    } else if ((numIncident == 2)) {
                        invitationParticularsOfMatter.setText("拜访");
                    } else if ((numIncident == 3)) {
                        invitationParticularsOfMatter.setText("外卖");
                    } else {
                        invitationParticularsOfMatter.setText("快递");
                    }

                    cancelChoiceRriginIncidentDialog();
                }

                @Override
                public void saveGenderSuccess(int gender) {

                }
            });
        }
        mChoiceRriginIncidentDialog.selectGender(genderType);
        mChoiceRriginIncidentDialog.show();
    }

    public void cancelChoiceRriginIncidentDialog() {
        if (mChoiceRriginIncidentDialog != null) {
            mChoiceRriginIncidentDialog.cancel();
        }
    }

    private void showChoiceIdTypeDialog(int genderType) {
        if (mChoiceIdTypeDialog == null) {
            mChoiceIdTypeDialog = new ChoiceIdTypeDialog(this, new ChoiceIdTypeDialog.IncidentCallback() {
                @Override
                public void genderSelect(int numIncident) {
                    if (numIncident == 0) {
                        invitationVisitingIdType.setText("居民身份证");
                    } else if ((numIncident == 1)) {
                        invitationVisitingIdType.setText("警官证");
                    } else if ((numIncident == 2)) {
                        invitationVisitingIdType.setText("军官证");
                    } else {
                        invitationVisitingIdType.setText("护照");
                    }

                    cancelChoiceIdTypeDialog();
                }

                @Override
                public void saveGenderSuccess(int gender) {

                }
            });
        }
        mChoiceIdTypeDialog.selectGender(genderType);
        mChoiceIdTypeDialog.show();
    }

    public void cancelChoiceIdTypeDialog() {
        if (mChoiceIdTypeDialog != null) {
            mChoiceIdTypeDialog.cancel();
        }
    }
}
