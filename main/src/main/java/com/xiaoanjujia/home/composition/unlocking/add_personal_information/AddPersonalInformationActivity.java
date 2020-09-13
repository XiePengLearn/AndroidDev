package com.xiaoanjujia.home.composition.unlocking.add_personal_information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.luck.picture.lib.entity.LocalMedia;
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
import com.xiaoanjujia.home.composition.tenement.detail.RecordDetailGridLayoutManager;
import com.xiaoanjujia.home.composition.unlocking.add_info_go_on.AddInfoGoOnActivity;
import com.xiaoanjujia.home.composition.unlocking.dialog.SelectAreaDialog;
import com.xiaoanjujia.home.composition.unlocking.reservation_record_details.ReservationRecordDetailGridImageAdapter;
import com.xiaoanjujia.home.composition.unlocking.select_housing.SelectHousingActivity;
import com.xiaoanjujia.home.entities.ChooseYourAreaInfo;
import com.xiaoanjujia.home.entities.RootAreaResponse;
import com.xiaoanjujia.home.entities.RootNextRegionResponse;
import com.xiaoanjujia.home.entities.SingleAddDataResponse;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.DataProvider;
import chihane.jdaddressselector.ISelectAble;
import chihane.jdaddressselector.SelectedListener;
import chihane.jdaddressselector.Selector;

/**
 * @author xiepeng
 */
@Route(path = "/addPersonalInformationActivity/addPersonalInformationActivity")
public class AddPersonalInformationActivity extends BaseActivity implements AddPersonalInformationContract.View, SelectAreaDialog.OnDialogListener {
    @Inject
    AddPersonalInformationPresenter mPresenter;
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
    AlphaButton sexMan;
    @BindView(R2.id.add_personal_info_sex_woman)
    AlphaButton sexWoman;
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

    private SelectAreaDialog mAddSelectBankDialog;
    private static final int REQUEST_CODE = 1;
    private static final int RESULT_CODE = 2;
    private static final String MPOSITION = "mPosition";
    private String mBankName;
    private String mBankCode;
    private int mPosition = -1;
    //选择银行卡的list
    private ArrayList arrayBankList = new ArrayList();
    private String mParentIndexCodes;
    private final int SUBACTIVITY1 = 1, SUBACTIVITY2 = 2; //requestCode请求码
    private String mIndexCode;
    private String mIndexCodeNext;
    private ArrayList<ISelectAble> mDataNextRegionList;
    private List<RootNextRegionResponse.DataBean> mRootNextRegionDateList;
    private boolean mIsFirstPop = true;
    private String mIndexCodeLastRoom;
    private BottomDialog mDialog;
    private int gender = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_information);
        themeId = R.style.picture_default_style;
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        mDataNextRegionList = new ArrayList<>();

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


    //    public void initData() {
    //        Map<String, Object> mapParameters = new HashMap<>(2);
    //        mapParameters.put("paramName", "phoneNo");
    //        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
    //        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
    //        mPresenter.getRequestData(headersTreeMap, mapParameters);
    //    }


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

    public void initData() {
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

    public void initRootNextRegionData() {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", "1");
        mapParameters.put("pageSize", "1000");
        mapParameters.put("resourceType", "region");
        mapParameters.put("parentIndexCode", mIndexCodeNext);
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
                    Myreceiver.send(mDataNextRegionList);
                } else {
                    ToastUtil.showToast(AddPersonalInformationActivity.this, "暂无数据");
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

    //http://localhost:8080/artemis/persionHouse/singleAdd?
    // personName=景郝11&
    // gender=1&
    // orgIndexCode=e3769762-45a5-4128-af84-f444bb0474c6&
    // certificateType=111&
    // certificateNo=141125189401290056


    public void initSingleAddData() {
        String addPersonalInfoAreaTvText = addPersonalInfoAreaTv.getText().toString();
        String addPersonalInfoHousingTvText = addPersonalInfoHousingTv.getText().toString();
        String addPersonalInfoRoomNumberTvText = addPersonalInfoRoomNumberTv.getText().toString();

        String editAddPersonalInfoVisitingNameText = editAddPersonalInfoVisitingName.getText().toString();

        String editAddPersonalInfoIdNumberText = editAddPersonalInfoIdNumber.getText().toString();
        String editAddPersonalInfoPhoneText = editAddPersonalInfoPhone.getText().toString();

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("personName", editAddPersonalInfoVisitingNameText);
        mapParameters.put("gender", String.valueOf(gender));
        mapParameters.put("orgIndexCode", mIndexCodeLastRoom);
        mapParameters.put("certificateType", "111");
        mapParameters.put("certificateNo", editAddPersonalInfoIdNumberText);
        mapParameters.put("phoneNo", editAddPersonalInfoPhoneText);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getSingleAddData(headersTreeMap, mapParameters);
    }

    @Override
    public void setSingleAddData(SingleAddDataResponse mSingleAddDataResponse) {
        try {
            String code = mSingleAddDataResponse.getStatus();
            String msg = mSingleAddDataResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                String personId = mSingleAddDataResponse.getData();
                ToastUtil.showToast(AddPersonalInformationActivity.this, "添加成功");
                String roomPath1 = addPersonalInfoAreaTv.getText().toString();
                String roomPath2 = addPersonalInfoHousingTv.getText().toString();
                String roomPath3 = addPersonalInfoRoomNumberTv.getText().toString();
                Intent intent = new Intent(mContext, AddInfoGoOnActivity.class);
                intent.putExtra("personId", personId);
                intent.putExtra("roomCode", mIndexCodeLastRoom);
                intent.putExtra("roomPath", roomPath1 + "\n" + roomPath2 + "\n" + roomPath3);
                startActivity(intent);
                PrefUtils.writePersonInfo("true", BaseApplication.getInstance());
                finish();
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
            R2.id.add_personal_info_sex_man, R2.id.add_personal_info_sex_woman,
            R2.id.generate_add_personal_info_submit})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.add_personal_info_area_ll) {
            //            if (!NoDoubleClickUtils.isDoubleClick()) {
            //                Intent intent = new Intent(SelectHousingActivity.this, PermitActivity.class);
            //                intent.putExtra("orderId", mOrderId);
            //                startActivity(intent);
            //            }

            mAddSelectBankDialog = SelectAreaDialog.newInstance(JSON.toJSONString(arrayBankList), mPosition);
            if (getFragmentManager() != null) {
                mAddSelectBankDialog.setOnDialogListener(this);
                mAddSelectBankDialog.show(getSupportFragmentManager(), "addSelectBankDialog");
            }
        } else if (id == R.id.add_personal_info_housing_ll) {
            if (Utils.isNull(mParentIndexCodes)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请先选择区域");
                return;
            }
            if (!NoDoubleClickUtils.isDoubleClick()) {
                Intent intent = new Intent(AddPersonalInformationActivity.this, SelectHousingActivity.class);
                intent.putExtra("parentIndexCodes", mParentIndexCodes);
                startActivityForResult(intent, SUBACTIVITY1);
            }

        } else if (id == R.id.add_personal_info_room_number_ll) {
            if (Utils.isNull(mIndexCode)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请先选择小区");
                return;
            }
            mIsFirstPop = true;
            showDialog();

        } else if (id == R.id.generate_add_personal_info_submit) {
            String addPersonalInfoAreaTvText = addPersonalInfoAreaTv.getText().toString();
            String addPersonalInfoHousingTvText = addPersonalInfoHousingTv.getText().toString();
            String addPersonalInfoRoomNumberTvText = addPersonalInfoRoomNumberTv.getText().toString();

            String editAddPersonalInfoVisitingNameText = editAddPersonalInfoVisitingName.getText().toString();

            String editAddPersonalInfoIdNumberText = editAddPersonalInfoIdNumber.getText().toString();
            String editAddPersonalInfoPhoneText = editAddPersonalInfoPhone.getText().toString();
            if (Utils.isNull(addPersonalInfoAreaTvText)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请选择区域");
                return;
            }
            if (Utils.isNull(addPersonalInfoHousingTvText)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请选择小区");
                return;
            }
            if (Utils.isNull(addPersonalInfoRoomNumberTvText)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请选择楼栋房号");
                return;
            }
            if (Utils.isNull(editAddPersonalInfoVisitingNameText)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请输入2-10位真实姓名");
                return;
            }
            if (Utils.isNull(editAddPersonalInfoIdNumberText)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请输入身份证号");
                return;
            }
            if (Utils.isNull(editAddPersonalInfoPhoneText)) {
                ToastUtil.showToast(BaseApplication.getInstance(), "请输入手机号码");
                return;
            }

            initSingleAddData();
        } else if (id == R.id.add_personal_info_sex_man) {
            hideKeyboard(view);
            gender = 1;
            sexMan.setBackground(getResources().getDrawable(R.drawable.sex_select));
            sexMan.setTextColor(getResources().getColor(R.color.color_2AAD67));

            sexWoman.setBackground(getResources().getDrawable(R.drawable.sex_normal));
            sexWoman.setTextColor(getResources().getColor(R.color.color_494949));
        } else if (id == R.id.add_personal_info_sex_woman) {
            hideKeyboard(view);
            gender = 2;

            sexMan.setBackground(getResources().getDrawable(R.drawable.sex_normal));
            sexMan.setTextColor(getResources().getColor(R.color.color_494949));

            sexWoman.setBackground(getResources().getDrawable(R.drawable.sex_select));
            sexWoman.setTextColor(getResources().getColor(R.color.color_2AAD67));
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClick(String bankName, String bankCode, int position) {
        mPosition = position;
        mParentIndexCodes = bankCode;
        addPersonalInfoAreaTv.setText(bankName);
    }

    //重载方法，接受返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SUBACTIVITY1:
                if (resultCode == Activity.RESULT_OK) {
                    String name = data.getStringExtra("name");
                    mIndexCode = data.getStringExtra("indexCode");
                    addPersonalInfoHousingTv.setText(name);
                    mIndexCodeNext = mIndexCode;
                    //                    initRootNextRegionData();
                }
                break;


            default:
                break;
        }
    }

    private ArrayList<ISelectAble> getDatas() {
        int count = new Random().nextInt(99) + 1;
        ArrayList<ISelectAble> data = new ArrayList<>(count);
        for (int j = 0; j < count; j++) {
            final int finalJ = j;
            data.add(new ISelectAble() {
                @Override
                public String getName() {
                    return "随机" + finalJ;
                }

                @Override
                public String getIndexCode() {
                    return null;
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
        return data;
    }

    private DataProvider.DataReceiver Myreceiver;

    private void showDialog() {
        Selector selector = new Selector(this, 5);

        selector.setDataProvider(new DataProvider() {
            @Override
            public void provideData(int currentDeep, int preId, DataReceiver receiver) {
                Myreceiver = receiver;
                if (mIsFirstPop) {
                    mIndexCodeNext = mIndexCode;
                    mIsFirstPop = false;
                } else {
                    if (mRootNextRegionDateList.size() > preId) {
                        mIndexCodeNext = mRootNextRegionDateList.get(preId).getIndexCode();
                    }

                }
                initRootNextRegionData();

                //根据tab的深度和前一项选择的id，获取下一级菜单项
                //                Log.i(TAG, "provideData: currentDeep >>> " + currentDeep + " preId >>> " + preId);
                //                mIndexCodeNext = mRootNextRegionDateList.get(preId).getIndexCode();
                ////                receiver.send(mDataNextRegionList);
                //                initRootNextRegionData();
            }
        });
        selector.setSelectedListener(new SelectedListener() {
            @Override
            public void onAddressSelected(ArrayList<ISelectAble> selectAbles) {
                String result = "";
                for (ISelectAble selectAble : selectAbles) {
                    result += selectAble.getName() + " ";

                }
                if (selectAbles.size() > 0) {
                    mIndexCodeLastRoom = selectAbles.get(selectAbles.size() - 1).getIndexCode();
                    LogUtil.e(TAG, "=======indexCode:=======" + mIndexCodeLastRoom);
                }

                LogUtil.e(TAG, "=======result:=======" + result);
                //                Toast.makeText(AddInfoGoOnActivity.this, result, Toast.LENGTH_SHORT).show();
                addPersonalInfoRoomNumberTv.setText(result);
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });

        mDialog = new BottomDialog(this);
        mDialog.init(this, selector);
        mDialog.show();
    }
}
