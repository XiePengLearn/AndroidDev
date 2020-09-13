package com.xiaoanjujia.home.composition.unlocking.house_manager;

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
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
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
@Route(path = "/issueQueryActivity/issueQueryActivity")
public class HouseManagerActivity extends BaseActivity implements HouseManagerContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    HouseManagerPresenter mPresenter;
    private static final String TAG = "ReservationRecordActivity";

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
    @BindView(R2.id.chat_list)
    RecyclerView mRecyclerView;
    @BindView(R2.id.no_data_img)
    ImageView noDataImg;
    @BindView(R2.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;

    private LayoutInflater mLayoutInflater;

    private List<String> listDate = new ArrayList<>();
    private List<Integer> listDateType = new ArrayList<>();
    private List<String> listWork = new ArrayList<>();
    private List<Integer> listWorkId = new ArrayList<>();
    private HouseManagerPreviewsAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_manger);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();

        //        initTypeRoieData();
        initData(page);
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("房屋管理");
    }

    private void initView() {
        DaggerHouseManagerActivityComponent.builder()
                .appComponent(getAppComponent())
                .houseManagerPresenterModule(new HouseManagerPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mLayoutInflater = LayoutInflater.from(this);


        findPullRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HouseManagerPreviewsAdapter(R.layout.item_house_manger_recyclerview);
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
//                    if (!NoDoubleClickUtils.isDoubleClick()) {
//
//                        List data = adapter.getData();
//                        PropertyManagementListLogResponse.DataBean dateBean = (PropertyManagementListLogResponse.DataBean) data.get(position);
//                        int id = dateBean.getId();
//                        Intent intent = new Intent(HouseManagerActivity.this, QuaryDetailActivity.class);
//                        intent.putExtra("id", id);
//                        startActivity(intent);
//                    }
                }

                return true;
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (!NoDoubleClickUtils.isDoubleClick()) {
//                    List data = adapter.getData();
//                    PropertyManagementListLogResponse.DataBean dateBean = (PropertyManagementListLogResponse.DataBean) data.get(position);
//                    int id = dateBean.getId();
//                    Intent intent = new Intent(HouseManagerActivity.this, QuaryDetailActivity.class);
//                    intent.putExtra("id", id);
//                    startActivity(intent);
//                }
            }
        });

    }

    private void initData(int page) {


        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("paramName", "phoneNo");
        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse) {
        try {
            String code = mVisitorPersonInfoResponse.getStatus();
            String msg = mVisitorPersonInfoResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<VisitorPersonInfoResponse.DataBean> messageDate = mVisitorPersonInfoResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            adapter.setEnableLoadMore(false);
                        } else {
                            adapter.setEnableLoadMore(true);
                        }
                        List<VisitorPersonInfoResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setEnableLoadMore(false);
                        List<VisitorPersonInfoResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.notifyDataSetChanged();
                    }

                } else {

                    rlNoData.setVisibility(View.VISIBLE);
                    adapter.setEnableLoadMore(false);
                    List<VisitorPersonInfoResponse.DataBean> data = adapter.getData();
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
    public void setMoreData(VisitorPersonInfoResponse moreDate) {
        try {
            String code = moreDate.getStatus();
            String msg = moreDate.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<VisitorPersonInfoResponse.DataBean> data = moreDate.getData();
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
                initMoreData(page);
            }
        }, 500);
    }

    private void initMoreData(int page) {

        Map<String, Object> mapParameters = new HashMap<>(4);
        mapParameters.put("paramName", "phoneNo");
        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
        mapParameters.put("pageNo", String.valueOf(page));
        mapParameters.put("pageSize", String.valueOf(10));

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

    @OnClick({R2.id.main_title_back, R2.id.no_data_img})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.main_title_back) {
            finish();
        } else if (viewId == R.id.no_data_img) {
        }
    }
}
