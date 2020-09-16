package com.xiaoanjujia.home.composition.unlocking.reservation_record;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.NoDoubleClickUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.unlocking.reservation_record_details.ReservationRecordDetailsActivity;
import com.xiaoanjujia.home.entities.AppointmentRecordsResponse;
import com.xiaoanjujia.home.entities.VisitingRecordsResponse;
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

/**
 * @author xiepeng
 */
@Route(path = "/reservationRecordActivity/reservationRecordActivity")
public class ReservationRecordActivity extends BaseActivity implements ReservationRecordContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    ReservationRecordPresenter mPresenter;
    private static final String TAG = "ReservationRecordActivity";
    @BindView(R2.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R2.id.main_title_back)
    ImageView mainTitleBack;
    @BindView(R2.id.fang_ke_record)
    TextView fangKeRecord;
    @BindView(R2.id.lai_fang_record)
    TextView laiFangRecord;
    @BindView(R2.id.main_title_container)
    LinearLayout mainTitleContainer;
    @BindView(R2.id.title_ll)
    LinearLayout titleLl;
    @BindView(R2.id.select_date_tv)
    TextView selectDateTv;
    @BindView(R2.id.select_date_ll)
    LinearLayout selectDateLl;
    @BindView(R2.id.chat_list)
    RecyclerView mRecyclerView;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    @BindView(R2.id.chat_list2)
    RecyclerView mRecyclerView2;
    @BindView(R2.id.find_pull_refresh_header2)
    JDHeaderView findPullRefreshHeader2;
    @BindView(R2.id.no_data_img)
    ImageView noDataImg;
    @BindView(R2.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R2.id.no_data_tv)
    TextView noDataTv;
    @BindView(R2.id.select_date_tv2)
    TextView selectDateTv2;
    @BindView(R2.id.select_date_ll2)
    LinearLayout selectDateLl2;


    private LayoutInflater mLayoutInflater;

    private List<String> listDate = new ArrayList<>();
    private List<Integer> listDateType = new ArrayList<>();
    private List<String> listWork = new ArrayList<>();
    private List<Integer> listWorkId = new ArrayList<>();
    private ReservationRecordPreviewsAdapter adapter;
    private ReservationRecordPreviewsAdapter2 adapter2;
    private int page = 1;
    private int page2 = 1;
    private String personId;
    private String mPersonName;
    private String mSelectTimeFangKe;
    private String mSelectTimeFangKe2;
    private int typePage = 1; //访客记录   2 来访记录

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_record);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        personId = intent.getStringExtra("personId");
        mPersonName = intent.getStringExtra("personName");
        initView();

        //        initTypeRoieData();

        initTitle();
        initTimePicker();
        initTimePicker2();
        String today = Utils.getToday();
        mSelectTimeFangKe = Utils.getTodayWithHour();
        mSelectTimeFangKe2 = Utils.getTodayWithHour();

        selectDateTv.setText(today);
        selectDateTv2.setText(today);
        initData(page, mSelectTimeFangKe);
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
    }

    private void initView() {
        DaggerReservationRecordActivityComponent.builder()
                .appComponent(getAppComponent())
                .reservationRecordPresenterModule(new ReservationRecordPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mLayoutInflater = LayoutInflater.from(this);


        findPullRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservationRecordPreviewsAdapter(R.layout.item_reservation_record_recyclerview);
        adapter.setOnLoadMoreListener(this);

        //        View itemHeader = mLayoutInflater.inflate(R.layout.item_supervisor_recyclerview_header, null);
        //        aflCotent = itemHeader.findViewById(R.id.afl_cotent);
        //        aflJobsToChoose = itemHeader.findViewById(R.id.afl_jobs_to_choose);
        //        adapter.addHeaderView(itemHeader);
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        mRecyclerView.setAdapter(adapter);


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter baseAdapter, View view, int position) {
                int i = view.getId();
                if (i == R.id.item_supervisor_btn_status) {
                    if (!NoDoubleClickUtils.isDoubleClick()) {
                        //"gender": 1,
                        //				"svrIndexCode": "abb8078f-a284-4d93-814d-7adc8f290fbf",
                        //				"nation": 1,
                        //				"orderId": "28397468-f445-11ea-9a3d-bf995f83655c",
                        //				"plateNo": "京626773",
                        //				"visitStartTime": "2020-09-16T13:39:23+08:00",
                        //				"visitPurpose": "看朋友",
                        //				"customa": "",
                        //				"certificateNo": "41142519910399030",
                        //				"phoneNo": "15601267550",
                        //				"visitEndTime": "2020-09-16T18:39:23+08:00",
                        //				"verificationCode": "7189",
                        //				"privilegeGroupNames": ["访客权限门禁点"],
                        //				"visitorName": "谢鹏",
                        //				"QRCode": "VjAwMSu1FSBN1YQz6qes4SYch8iaJUxjmweh1nl+9aU4rp/Np+y+PpB5WrjR9J3vs9ox9+E=",
                        //				"receptionistName": "老谢",
                        //				"picUri": "/pic?2ddc965bf-2do7b1l*36ed7=s-i6z24*5328989=81i5m*ep=t2p5i=d1s*i=d4b*i7dce*8428fcd6a-b1e34a-17o11cpi9c1d=92ie8=",
                        //				"appointRecordId": "027fd04d5dae47bf87766a28da75ac68",
                        //				"receptionistCode": "01101002010010100102",
                        //				"visitorStatus": 1,
                        //				"receptionistId": "77edf8446c7c448e914caf14eeaf9f20",
                        //				"certificateType": 111,
                        //				"visitorId": "28397468-f445-11ea-9a3d-bf995f83655c"
                        List data = adapter.getData();
                        AppointmentRecordsResponse.DataBean dateBean = (AppointmentRecordsResponse.DataBean) data.get(position);

                        int visitorStatus = dateBean.getVisitorStatus();
                        String visitorName = dateBean.getVisitorName();
                        String receptionistName = dateBean.getReceptionistName();
                        String phoneNo = dateBean.getPhoneNo();
                        String visitStartTime = dateBean.getVisitStartTime();
                        String visitEndTime = dateBean.getVisitEndTime();
                        String visitPurpose = dateBean.getVisitPurpose();
                        String picUri = dateBean.getPicUri();
                        int gender = dateBean.getGender();
                        String certificateNo = dateBean.getCertificateNo();
                        String plateNo = dateBean.getPlateNo();
                        String orderId = dateBean.getOrderId();
                        Intent intent = new Intent(ReservationRecordActivity.this, ReservationRecordDetailsActivity.class);
                        intent.putExtra("visitorStatus", visitorStatus);
                        intent.putExtra("visitorName", visitorName);
                        intent.putExtra("receptionistName", receptionistName);
                        intent.putExtra("phoneNo", phoneNo);
                        intent.putExtra("visitStartTime", visitStartTime);
                        intent.putExtra("visitEndTime", visitEndTime);
                        intent.putExtra("visitPurpose", visitPurpose);
                        intent.putExtra("picUri", picUri);
                        intent.putExtra("gender", gender);
                        intent.putExtra("certificateNo", certificateNo);
                        intent.putExtra("plateNo", plateNo);
                        intent.putExtra("orderId", orderId);
                        startActivity(intent);
                    }
                }

                return true;
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    List data = adapter.getData();
                    AppointmentRecordsResponse.DataBean dateBean = (AppointmentRecordsResponse.DataBean) data.get(position);

                    int visitorStatus = dateBean.getVisitorStatus();
                    String visitorName = dateBean.getVisitorName();
                    String receptionistName = dateBean.getReceptionistName();
                    String phoneNo = dateBean.getPhoneNo();
                    String visitStartTime = dateBean.getVisitStartTime();
                    String visitEndTime = dateBean.getVisitEndTime();
                    String visitPurpose = dateBean.getVisitPurpose();
                    String picUri = dateBean.getPicUri();
                    int gender = dateBean.getGender();
                    String certificateNo = dateBean.getCertificateNo();
                    String plateNo = dateBean.getPlateNo();
                    String orderId = dateBean.getOrderId();

                    Intent intent = new Intent(ReservationRecordActivity.this, ReservationRecordDetailsActivity.class);
                    intent.putExtra("visitorStatus", visitorStatus);
                    intent.putExtra("visitorName", visitorName);
                    intent.putExtra("receptionistName", receptionistName);
                    intent.putExtra("phoneNo", phoneNo);
                    intent.putExtra("visitStartTime", visitStartTime);
                    intent.putExtra("visitEndTime", visitEndTime);
                    intent.putExtra("visitPurpose", visitPurpose);
                    intent.putExtra("picUri", picUri);
                    intent.putExtra("gender", gender);
                    intent.putExtra("certificateNo", certificateNo);
                    intent.putExtra("plateNo", plateNo);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                }
            }
        });


        findPullRefreshHeader2.setPtrHandler(this);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        adapter2 = new ReservationRecordPreviewsAdapter2(R.layout.item_reservation_record_recyclerview);
        adapter2.setOnLoadMoreListener(this);

        //        View itemHeader = mLayoutInflater.inflate(R.layout.item_supervisor_recyclerview_header, null);
        //        aflCotent = itemHeader.findViewById(R.id.afl_cotent);
        //        aflJobsToChoose = itemHeader.findViewById(R.id.afl_jobs_to_choose);
        //        adapter.addHeaderView(itemHeader);
        adapter2.setEnableLoadMore(true);
        adapter2.loadMoreComplete();
        mRecyclerView2.setAdapter(adapter2);


        adapter2.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter baseAdapter, View view, int position) {
                int i = view.getId();
                if (i == R.id.item_supervisor_btn_status) {
                    if (!NoDoubleClickUtils.isDoubleClick()) {

                        List data = adapter2.getData();
                        AppointmentRecordsResponse.DataBean dateBean = (AppointmentRecordsResponse.DataBean) data.get(position);
                        //                        int id = dateBean.getId();
                        //                        Intent intent = new Intent(ReservationRecordActivity.this, QuaryDetailActivity.class);
                        //                        intent.putExtra("id", id);
                        //                        startActivity(intent);
                    }
                }

                return true;
            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    List data = adapter2.getData();
                    AppointmentRecordsResponse.DataBean dateBean = (AppointmentRecordsResponse.DataBean) data.get(position);
                    //                    int id = dateBean.getId();
                    //                    Intent intent = new Intent(ReservationRecordActivity.this, QuaryDetailActivity.class);
                    //                    intent.putExtra("id", id);
                    //                    startActivity(intent);
                }
            }
        });

    }


    //pageNo=1&
    // pageSize=10&
    // receptionistId=aebcf3a7b59b4fb889daaea2b45c2bf5&
    // visitorName=井号
    private void initData(int page, String time) {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));
        mapParameters.put("receptionistId", personId);
        mapParameters.put("visitStartTimeBegin", time);


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }

    //pageNo=1&
    // pageSize=10&
    // receptionistId=aebcf3a7b59b4fb889daaea2b45c2bf5&
    // visitorName=井号
    private void initData2(int page, String time) {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));
        mapParameters.put("receptionistId", personId);
        mapParameters.put("visitStartTimeBegin", time);


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getLaiFangData(headersTreeMap, mapParameters);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void setResponseData(AppointmentRecordsResponse mAppointmentRecordsResponse) {
        try {
            String code = mAppointmentRecordsResponse.getStatus();
            String msg = mAppointmentRecordsResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<AppointmentRecordsResponse.DataBean> messageDate = mAppointmentRecordsResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            adapter.setEnableLoadMore(false);
                        } else {
                            adapter.setEnableLoadMore(true);
                        }
                        List<AppointmentRecordsResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setEnableLoadMore(false);
                        List<AppointmentRecordsResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.notifyDataSetChanged();
                    }

                } else {

                    rlNoData.setVisibility(View.VISIBLE);
                    adapter.setEnableLoadMore(false);
                    List<AppointmentRecordsResponse.DataBean> data = adapter.getData();
                    data.clear();
                    adapter.notifyDataSetChanged();
                }

            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
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
    public void setMoreData(AppointmentRecordsResponse moreDate) {
        try {
            String code = moreDate.getStatus();
            String msg = moreDate.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<AppointmentRecordsResponse.DataBean> data = moreDate.getData();
                if (data != null) {
                    if (data.size() < 10) {
                        adapter.setEnableLoadMore(false);
                    } else {
                        adapter.setEnableLoadMore(true);
                    }
                    for (int i = 0; i < data.size(); i++) {
                        adapter.getData().add(data.get(i));
                    }

                } else {
                    adapter.setEnableLoadMore(false);
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                adapter.loadMoreComplete();
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();
            } else {
                adapter.loadMoreComplete();
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            adapter.loadMoreComplete();
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        } finally {
            adapter.loadMoreComplete();
        }
    }


    @Override
    public void setLaiFangData(VisitingRecordsResponse mVisitingRecordsResponse) {
        try {
            String code = mVisitingRecordsResponse.getStatus();
            String msg = mVisitingRecordsResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<VisitingRecordsResponse.DataBean> messageDate = mVisitingRecordsResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            adapter2.setEnableLoadMore(false);
                        } else {
                            adapter2.setEnableLoadMore(true);
                        }
                        List<VisitingRecordsResponse.DataBean> data = adapter2.getData();
                        data.clear();
                        adapter2.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter2.setEnableLoadMore(false);
                        List<VisitingRecordsResponse.DataBean> data = adapter2.getData();
                        data.clear();
                        adapter2.notifyDataSetChanged();
                    }

                } else {

                    rlNoData.setVisibility(View.VISIBLE);
                    adapter2.setEnableLoadMore(false);
                    List<VisitingRecordsResponse.DataBean> data = adapter2.getData();
                    data.clear();
                    adapter2.notifyDataSetChanged();
                }

            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
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
    public void setLaiFangMoreData(VisitingRecordsResponse moreDate) {
        try {
            String code = moreDate.getStatus();
            String msg = moreDate.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<VisitingRecordsResponse.DataBean> data = moreDate.getData();
                if (data != null) {
                    if (data.size() < 10) {
                        adapter2.setEnableLoadMore(false);
                    } else {
                        adapter2.setEnableLoadMore(true);
                    }
                    for (int i = 0; i < data.size(); i++) {
                        adapter2.getData().add(data.get(i));
                    }

                } else {
                    adapter2.setEnableLoadMore(false);
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
                adapter2.loadMoreComplete();
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

                finish();
            } else {
                adapter2.loadMoreComplete();
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mContext.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            adapter2.loadMoreComplete();
            ToastUtil.showToast(mContext.getApplicationContext(), "解析数据失败");
        } finally {
            adapter2.loadMoreComplete();
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

    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                initMoreData(page, mSelectTimeFangKe);
            }
        }, 500);

        mRecyclerView2.postDelayed(new Runnable() {
            @Override
            public void run() {
                page2++;
                initMoreData2(page2, mSelectTimeFangKe2);
            }
        }, 500);
    }

    //page
    //datetype :时间类型默认----是1(当天)2(本周)3(本月)4(上月)5(近三月)
    //id:角色id  默认0  全部
    private void initMoreData2(int page, String time) {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));
        mapParameters.put("receptionistId", personId);
        //        mapParameters.put("visitorName", mPersonName);
        mapParameters.put("visitStartTimeBegin", time);

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getLaiFangMoreData(headersTreeMap, mapParameters);
    }

    private void initMoreData(int page, String time) {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));
        mapParameters.put("receptionistId", personId);
        //        mapParameters.put("visitorName", mPersonName);
        mapParameters.put("visitStartTimeBegin", time);

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getMoreData(headersTreeMap, mapParameters);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (typePage == 1) {
                    page = 1;
                    initData(page, mSelectTimeFangKe);
                    frame.refreshComplete();
                } else {
                    page2 = 1;
                    initData2(page2, mSelectTimeFangKe2);
                    frame.refreshComplete();
                }

            }
        }, 500);
    }

    @OnClick({R2.id.main_title_back, R2.id.fang_ke_record, R2.id.lai_fang_record, R2.id.select_date_ll, R2.id.select_date_ll2})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.main_title_back) {
            finish();
        } else if (viewId == R.id.fang_ke_record) {
            typePage = 1;
            noDataTv.setText("暂无预约记录");
            fangKeRecord.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            //设置不为加粗
            laiFangRecord.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            selectDateLl.setVisibility(View.VISIBLE);
            selectDateLl2.setVisibility(View.GONE);
            rlNoData.setVisibility(View.GONE);
            findPullRefreshHeader.setVisibility(View.VISIBLE);
            findPullRefreshHeader2.setVisibility(View.GONE);


            page = 1;
            adapter.setEnableLoadMore(true);
            adapter.loadMoreComplete();
            initData(page, mSelectTimeFangKe);
        } else if (viewId == R.id.lai_fang_record) {
            typePage = 2;
            noDataTv.setText("暂无来访记录");
            fangKeRecord.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //设置不为加粗
            laiFangRecord.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            selectDateLl.setVisibility(View.GONE);
            selectDateLl2.setVisibility(View.VISIBLE);
            rlNoData.setVisibility(View.GONE);
            findPullRefreshHeader.setVisibility(View.GONE);
            findPullRefreshHeader2.setVisibility(View.VISIBLE);

            page2 = 1;
            adapter2.setEnableLoadMore(true);
            adapter2.loadMoreComplete();
            initData2(page2, mSelectTimeFangKe2);

        } else if (viewId == R.id.select_date_ll) {
            mPvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        } else if (viewId == R.id.select_date_ll2) {
            mPvTime2.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        }
    }

    private TimePickerView mPvTime;

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        //时间选择器

        mPvTime = new TimePickerBuilder(ReservationRecordActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                //                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                String mVisitorTime = Utils.getTimeMonth(date);
                mSelectTimeFangKe = Utils.getTimeMonthWithHour(date);
                selectDateTv.setText(mVisitorTime);
                page = 1;
                adapter.setEnableLoadMore(true);
                adapter.loadMoreComplete();
                initData(page, mSelectTimeFangKe);
            }
        })
                .setItemVisibleCount(2)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }

    private TimePickerView mPvTime2;

    private void initTimePicker2() {//Dialog 模式下，在底部弹出
        //时间选择器

        mPvTime2 = new TimePickerBuilder(ReservationRecordActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                //                Toast.makeText(VisitorInvitationActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                String mVisitorTime = Utils.getTimeMonth(date);
                mSelectTimeFangKe2 = Utils.getTimeMonthWithHour(date);
                selectDateTv2.setText(mVisitorTime);
                page2 = 1;
                adapter2.setEnableLoadMore(true);
                adapter2.loadMoreComplete();
                initData(page2, mSelectTimeFangKe2);
            }
        })
                .setItemVisibleCount(2)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }
}
