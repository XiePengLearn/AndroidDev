package com.xiaoanjujia.home.composition.unlocking.add_info_go_on;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.success.submit.SubmitSuccessActivity;
import com.xiaoanjujia.home.composition.tenement.detail.RecordDetailGridLayoutManager;
import com.xiaoanjujia.home.composition.unlocking.dialog.GoOnAddInfoDialog;
import com.xiaoanjujia.home.composition.unlocking.reservation_record_details.ReservationRecordDetailGridImageAdapter;
import com.xiaoanjujia.home.entities.ChooseYourAreaInfo;
import com.xiaoanjujia.home.entities.GoOnSingleAddDataResponse;
import com.xiaoanjujia.home.entities.RootAreaResponse;
import com.xiaoanjujia.home.entities.RootNextRegionResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;
import com.xiaoanjujia.home.tool.Api;

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
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.ISelectAble;

/**
 * @author xiepeng
 */
@Route(path = "/addPersonalInformationActivity/addPersonalInformationActivity")
public class AddInfoGoOnActivity extends BaseActivity implements AddInfoGoOnContract.View, GoOnAddInfoDialog.OnDialogListener {
    @Inject
    AddInfoGoOnPresenter mPresenter;
    private static final String TAG = "SelectHousingActivity";
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
    @BindView(R2.id.add_personal_info_room_number_tv)
    TextView addPersonalInfoRoomNumberTv;
    @BindView(R2.id.add_personal_info_room_number_ll)
    LinearLayout addPersonalInfoRoomNumberLl;
    @BindView(R2.id.add_personal_info_type_tv)
    TextView addPersonalInfoTypeTv;
    @BindView(R2.id.add_personal_info_type_ll)
    LinearLayout addPersonalInfoTypeLl;
    @BindView(R2.id.go_on_add_personal_info_submit)
    AlphaButton goOnAddPersonalInfoSubmit;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;
    @BindView(R2.id.invitation_visiting_time)
    TextView invitationVisitingTime;
    @BindView(R2.id.invitation_visiting_time_ll)
    LinearLayout invitationVisitingTimeLl;
    @BindView(R2.id.invitation_leave_time)
    TextView invitationLeaveTime;
    @BindView(R2.id.invitation_leave_time_ll)
    LinearLayout invitationLeaveTimeLl;
    @BindView(R2.id.line_start)
    View lineStart;
    @BindView(R2.id.line_end)
    View lineEnd;


    private GoOnAddInfoDialog mAddSelectBankDialog;
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_CODE = 2;
    private static final String MPOSITION = "mPosition";
    private String mBankName;
    private String mBankCode;
    private int mPosition = -1;
    private String bankCode = "-1";
    //选择银行卡的list
    private ArrayList arrayBankList = new ArrayList();
    private ArrayList<ISelectAble> mDataNextRegionList;
    private List<RootNextRegionResponse.DataBean> mRootNextRegionDateList;
    private boolean mIsFirstPop = true;
    private String mIndexCodeLastRoom;
    private BottomDialog mDialog;
    private int gender = 1;
    private String mPersonId;
    private String mOrgPathName;
    private String mFrontPersonId;
    private String mFrontRoomCode;
    private String mFrontroomPath;
    private String mVisitorTime;
    private String mLeaveTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info_go_on);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mDataNextRegionList = new ArrayList<>();
        Intent intent = getIntent();
        mFrontPersonId = intent.getStringExtra("personId");
        mFrontRoomCode = intent.getStringExtra("roomCode");
        mFrontroomPath = intent.getStringExtra("roomPath");
        if (!Utils.isNull(mFrontroomPath)) {
            addPersonalInfoRoomNumberTv.setText(mFrontroomPath);
        }
        initView();
        //        initInfoData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("继续添加信息");
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private ReservationRecordDetailGridImageAdapter mAdapter;

    private void initView() {
        DaggerAddInfoGoOnComponent.builder()
                .appComponent(getAppComponent())
                .addInfoGoOnPresenterModule(new AddInfoGoOnPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        RecordDetailGridLayoutManager manager = new RecordDetailGridLayoutManager(AddInfoGoOnActivity.this, 1, GridLayoutManager.VERTICAL, false);
        initTimePicker();
        initTimePickerLeave();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initInfoData() {
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
                        mPersonId = dataBean.getPersonId();
                        mOrgPathName = dataBean.getOrgPathName();
                        if (!Utils.isNull(mOrgPathName)) {
                            addPersonalInfoRoomNumberTv.setText(mOrgPathName);

                        }
                    }
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(AddInfoGoOnActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(AddInfoGoOnActivity.this, msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(AddInfoGoOnActivity.this, "解析数据失败");
        }
    }

    private void initData() {
        Map<String, Object> mapParameters = new HashMap<>(2);
        //        mapParameters.put("paramName", "phoneNo");
        //        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getRootAreaData(headersTreeMap, mapParameters);
    }


    @Override
    public void setRootAreaData(RootAreaResponse mRootAreaResponse) {
        try {
            String code = mRootAreaResponse.getStatus();
            String msg = mRootAreaResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                RootAreaResponse.DataBean data = mRootAreaResponse.getData();
                if (data != null) {
                    arrayBankList.clear();
                    String name = data.getName();
                    String indexCode = data.getIndexCode();
                    String parentIndexCode = data.getParentIndexCode();
                    String treeCode = data.getTreeCode();
                    ChooseYourAreaInfo info = new ChooseYourAreaInfo();
                    info.setName(name);
                    info.setIndexCode(indexCode);
                    info.setParentIndexCode(parentIndexCode);
                    info.setTreeCode(treeCode);
                    arrayBankList.add(info);

                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(AddInfoGoOnActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(AddInfoGoOnActivity.this, msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(AddInfoGoOnActivity.this, "解析数据失败");
        }
    }

    private void initRootNextRegionData() {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", "1");
        mapParameters.put("pageSize", "1000");
        mapParameters.put("resourceType", "region");
        mapParameters.put("parentIndexCode", mFrontRoomCode);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getRootNextRegionData(headersTreeMap, mapParameters);
    }

    @Override
    public void setRootNextRegionData(RootNextRegionResponse mRootNextRegionResponse) {
        try {
            String code = mRootNextRegionResponse.getStatus();
            String msg = mRootNextRegionResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                mRootNextRegionDateList = mRootNextRegionResponse.getData();
                if (mRootNextRegionDateList != null && mRootNextRegionDateList.size() > 0) {
                    /**
                     * "regionPath": "@root000000@92067959-1cc4-4429-9093-05a1ed827a5a@4d863400-b023-44ad-b1eb-991cdbb8a862@",
                     * 		"cascadeType": 0,
                     * 		"catalogType": 12,
                     * 		"cascadeCode": "0",
                     * 		"available": true,
                     * 		"indexCode": "4d863400-b023-44ad-b1eb-991cdbb8a862",
                     * 		"updateTime": "2020-08-06T15:27:24.376+08:00",
                     * 		"sort": 2,
                     * 		"nodeType": 2,
                     * 		"leaf": false,
                     * 		"regionCode": "01201005010000000000",
                     * 		"createTime": "2020-08-06T15:27:24.376+08:00",
                     * 		"name": "1管理分区",
                     * 		"parentIndexCode": "92067959-1cc4-4429-9093-05a1ed827a5a"
                     */

                    mDataNextRegionList.clear();

                    for (int i = 0; i < mRootNextRegionDateList.size(); i++) {
                        final int finalJ = i;
                        final String indexCode = mRootNextRegionDateList.get(i).getIndexCode();
                        final String name = mRootNextRegionDateList.get(i).getName();
                        mDataNextRegionList.add(new ISelectAble() {
                            @Override
                            public String getName() {
                                return name;
                            }

                            @Override
                            public String getIndexCode() {
                                return indexCode;
                            }

                            @Override
                            public int getId() {
                                return finalJ;
                            }

                            @Override
                            public Object getArg() {
                                return this;
                            }
                        });

                    }
                } else {
                    ToastUtil.showToast(AddInfoGoOnActivity.this, "暂无数据");
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(AddInfoGoOnActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(AddInfoGoOnActivity.this, msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(AddInfoGoOnActivity.this, "解析数据失败");
        }
    }

    //http://localhost:8080/artemis/persionHouse/singleAdd?
    // personName=景郝11&
    // gender=1&
    // orgIndexCode=e3769762-45a5-4128-af84-f444bb0474c6&
    // certificateType=111&
    // certificateNo=141125189401290056

    //  "checkInTime": "2020-08-31"
    //-     "expireTime": "2021-08-31"
    //-     "personId": "aebcf3a7b59b4fb889daaea2b45c2bf5"
    //-     "relatedType": "1"
    //-     "roomCode": "ff7011c4-a28e-4149-8d58-0b93a69ca236"
    private void initSingleAddData() {
        String invitationVisitingTimeText = invitationVisitingTime.getText().toString();
        String invitationLeaveTimeText = invitationLeaveTime.getText().toString();

        Map<String, Object> mapParameters = new HashMap<>(4);
        if (!Utils.isNull(bankCode) && bankCode.equals("1")) {
            mapParameters.put("checkInTime", invitationVisitingTimeText);
            mapParameters.put("expireTime", invitationLeaveTimeText);
        }
        mapParameters.put("personId", mFrontPersonId);
        mapParameters.put("relatedType", bankCode);
        mapParameters.put("roomCode", mFrontRoomCode);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getGoOnSingleAddData(headersTreeMap, mapParameters);
    }

    @Override
    public void setGoOnSingleAddData(GoOnSingleAddDataResponse mGoOnSingleAddDataResponse) {
        try {
            String code = mGoOnSingleAddDataResponse.getStatus();
            String msg = mGoOnSingleAddDataResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                //                String data = mGoOnSingleAddDataResponse.getData();
                //                ToastUtil.showToast(AddInfoGoOnActivity.this, "成功");
                ToastUtil.showToast(this.getApplicationContext(), "提交成功");
                //                ARouter.getInstance().build("/SubmitSuccessActivity/SubmitSuccessActivity").greenChannel().navigation(mContext);
                Intent intent = new Intent(mContext, SubmitSuccessActivity.class);
                intent.putExtra("hindText", "hindText");
                startActivity(intent);
                finish();
            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(AddInfoGoOnActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(AddInfoGoOnActivity.this, "继续添加信息失败,请重试");
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(AddInfoGoOnActivity.this, "解析数据失败");
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

    @OnClick({R2.id.main_title_back, R2.id.add_personal_info_type_ll, R2.id.go_on_add_personal_info_submit,
            R2.id.invitation_visiting_time_ll, R2.id.invitation_leave_time_ll})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.add_personal_info_type_ll) {
            mAddSelectBankDialog = GoOnAddInfoDialog.newInstance(mPosition);
            if (getFragmentManager() != null) {
                mAddSelectBankDialog.setOnDialogListener(this);
                mAddSelectBankDialog.show(getSupportFragmentManager(), "addSelectBankDialog");
            }
        } else if (id == R.id.go_on_add_personal_info_submit) {
            String invitationVisitingTimeText = invitationVisitingTime.getText().toString();
            String invitationLeaveTimeText = invitationLeaveTime.getText().toString();
            if (!Utils.isNull(bankCode) && bankCode.equals("1")) {
                if (Utils.isNull(invitationVisitingTimeText)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), "请选择租赁开始时间");
                    return;
                }
                if (Utils.isNull(invitationLeaveTimeText)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), "请选择租赁结束时间");
                    return;
                }
            }


            initSingleAddData();
        } else if (id == R.id.invitation_visiting_time_ll) {
            //            hideKeyboard(view);
            mPvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        } else if (id == R.id.invitation_leave_time_ll) {
            //            hideKeyboard(view);
            mPvTimeLeave.show(view);

        }
    }

    private TimePickerView mPvTime;
    private TimePickerView mPvTimeLeave;

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        //时间选择器

        mPvTime = new TimePickerBuilder(AddInfoGoOnActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                //                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mVisitorTime = Utils.getTimeMonth(date);
                invitationVisitingTime.setText(mVisitorTime);
            }
        })
                .setItemVisibleCount(2)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }

    private void initTimePickerLeave() {//Dialog 模式下，在底部弹出
        //时间选择器
        mPvTimeLeave = new TimePickerBuilder(AddInfoGoOnActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                //                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mLeaveTime = Utils.getTimeMonth(date);
                invitationLeaveTime.setText(mLeaveTime);
            }
        })
                .setItemVisibleCount(2)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }

    @Override
    public void onItemClick(String bankName, String bankCode, int position) {
        mPosition = position;
        this.bankCode = bankCode;
        addPersonalInfoTypeTv.setText(bankName);
        if (!Utils.isNull(bankCode) && bankCode.equals("1")) {
            lineStart.setVisibility(View.VISIBLE);
            lineEnd.setVisibility(View.VISIBLE);
            invitationVisitingTimeLl.setVisibility(View.VISIBLE);
            invitationLeaveTimeLl.setVisibility(View.VISIBLE);
        } else {
            lineStart.setVisibility(View.INVISIBLE);
            lineEnd.setVisibility(View.INVISIBLE);
            invitationVisitingTimeLl.setVisibility(View.INVISIBLE);
            invitationLeaveTimeLl.setVisibility(View.INVISIBLE);
        }
    }

}
