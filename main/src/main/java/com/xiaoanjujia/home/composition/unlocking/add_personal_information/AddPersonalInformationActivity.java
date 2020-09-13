package com.xiaoanjujia.home.composition.unlocking.add_personal_information;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luck.picture.lib.entity.LocalMedia;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.tenement.detail.RecordDetailGridLayoutManager;
import com.xiaoanjujia.home.composition.unlocking.reservation_record_details.ReservationRecordDetailGridImageAdapter;
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
@Route(path = "/addPersonalInformationActivity/addPersonalInformationActivity")
public class AddPersonalInformationActivity extends BaseActivity implements AddPersonalInformationContract.View {
    @Inject
    AddPersonalInformationPresenter mPresenter;
    private static final String TAG = "AddPersonalInformationActivity";
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
    @BindView(R2.id.add_personal_info_area_tv)
    TextView addPersonalInfoAreaTv;
    @BindView(R2.id.add_personal_info_area_ll)
    LinearLayout addPersonalInfoAreaLl;
    @BindView(R2.id.add_personal_info_housing_tv)
    TextView addPersonalInfoHousingTv;
    @BindView(R2.id.add_personal_info_housing_ll)
    LinearLayout addPersonalInfoHousingLl;
    @BindView(R2.id.add_personal_info_room_number_tv)
    TextView addPersonalInfoRoomNumberTv;
    @BindView(R2.id.add_personal_info_room_number_ll)
    LinearLayout addPersonalInfoRoomNumberLl;
    @BindView(R2.id.edit_add_personal_info_visiting_name)
    EditText editAddPersonalInfoVisitingName;
    @BindView(R2.id.add_personal_info_sex_man)
    AlphaButton addPersonalInfoSexMan;
    @BindView(R2.id.add_personal_info_sex_woman)
    AlphaButton addPersonalInfoSexWoman;
    @BindView(R2.id.invitation_add_personal_info_gender_ll)
    LinearLayout invitationAddPersonalInfoGenderLl;
    @BindView(R2.id.edit_add_personal_info_id_number)
    EditText editAddPersonalInfoIdNumber;
    @BindView(R2.id.edit_add_personal_info_phone)
    EditText editAddPersonalInfoPhone;
    @BindView(R2.id.generate_add_personal_info_submit)
    AlphaButton generateAddPersonalInfoSubmit;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;

    private String mOrderId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_information);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        int visitorStatus = intent.getIntExtra("visitorStatus", -1);


        initView();
        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("添加个人信息");
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private ReservationRecordDetailGridImageAdapter mAdapter;
    private int themeId;

    private void initView() {
        DaggerAddPersonalInformationComponent.builder()
                .appComponent(getAppComponent())
                .addPersonalInformationPresenterModule(new AddPersonalInformationPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        RecordDetailGridLayoutManager manager = new RecordDetailGridLayoutManager(AddPersonalInformationActivity.this, 1, GridLayoutManager.VERTICAL, false);

    }


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

                    }
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(AddPersonalInformationActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(AddPersonalInformationActivity.this, msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(AddPersonalInformationActivity.this, "解析数据失败");
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



    @OnClick({R2.id.main_title_back, R2.id.add_personal_info_area_ll,
            R2.id.add_personal_info_housing_ll, R2.id.add_personal_info_room_number_ll,
            R2.id.generate_add_personal_info_submit})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.add_personal_info_area_ll) {
//            if (!NoDoubleClickUtils.isDoubleClick()) {
//                Intent intent = new Intent(AddPersonalInformationActivity.this, PermitActivity.class);
//                intent.putExtra("orderId", mOrderId);
//                startActivity(intent);
//            }
        } else if (id == R.id.add_personal_info_housing_ll) {

        } else if (id == R.id.add_personal_info_room_number_ll) {

        } else if (id == R.id.generate_add_personal_info_submit) {

        }
    }
}
