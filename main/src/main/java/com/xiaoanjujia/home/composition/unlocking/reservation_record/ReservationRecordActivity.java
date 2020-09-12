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
import com.xiaoanjujia.home.composition.tenement.quary_detail.QuaryDetailActivity;
import com.xiaoanjujia.home.entities.AppointmentRecordsResponse;
import com.xiaoanjujia.home.entities.PropertyManagementListLogResponse;
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


    private LayoutInflater mLayoutInflater;

    private List<String> listDate = new ArrayList<>();
    private List<Integer> listDateType = new ArrayList<>();
    private List<String> listWork = new ArrayList<>();
    private List<Integer> listWorkId = new ArrayList<>();
    private ReservationRecordPreviewsAdapter adapter;
    private int page = 1, datetype = 1, id = 0;
    private String personId;
    private String mPersonName;

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
        initData(page);
        initTitle();
        initTimePicker();
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

                        List data = adapter.getData();
                        PropertyManagementListLogResponse.DataBean dateBean = (PropertyManagementListLogResponse.DataBean) data.get(position);
                        int id = dateBean.getId();
                        Intent intent = new Intent(ReservationRecordActivity.this, QuaryDetailActivity.class);
                        intent.putExtra("id", id);
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
                    PropertyManagementListLogResponse.DataBean dateBean = (PropertyManagementListLogResponse.DataBean) data.get(position);
                    int id = dateBean.getId();
                    Intent intent = new Intent(ReservationRecordActivity.this, QuaryDetailActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            }
        });

    }


    //pageNo=1&
    // pageSize=10&
    // receptionistId=aebcf3a7b59b4fb889daaea2b45c2bf5&
    // visitorName=井号
    private void initData(int page) {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));
        mapParameters.put("receptionistId", personId);
//        mapParameters.put("visitorName", mPersonName);


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
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

    }

    @Override
    public void setLaiFangMoreData(VisitingRecordsResponse mVisitingRecordsResponse) {

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
                initMoreData(page, datetype, id);
            }
        }, 500);
    }

    //page
    //datetype :时间类型默认----是1(当天)2(本周)3(本月)4(上月)5(近三月)
    //id:角色id  默认0  全部
    private void initMoreData(int page, int datetype, int id) {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));
        mapParameters.put("receptionistId", personId);
        mapParameters.put("visitorName", mPersonName);


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getMoreData(headersTreeMap, mapParameters);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData(page);
                frame.refreshComplete();
            }
        }, 500);
    }

    @OnClick({R2.id.main_title_back, R2.id.fang_ke_record, R2.id.lai_fang_record, R2.id.select_date_ll})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.main_title_back) {
            finish();
        } else if (viewId == R.id.fang_ke_record) {
            noDataTv.setText("暂无预约记录");
            fangKeRecord.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            //设置不为加粗
            laiFangRecord.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else if (viewId == R.id.lai_fang_record) {
            noDataTv.setText("暂无来访记录");
            fangKeRecord.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            //设置不为加粗
            laiFangRecord.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else if (viewId == R.id.select_date_ll) {
            mPvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
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
                selectDateTv.setText(mVisitorTime);
            }
        })
                .setItemVisibleCount(2)
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setSubmitColor(getResources().getColor(R.color.color_2AAD67))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.color_2AAD67))//取消按钮文字颜色
                .build();
    }
}
