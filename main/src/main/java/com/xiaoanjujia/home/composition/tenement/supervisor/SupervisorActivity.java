package com.xiaoanjujia.home.composition.tenement.supervisor;

import android.content.Intent;
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
import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.tenement.detail.RecordDetailActivity;
import com.xiaoanjujia.home.entities.PropertyManagementListLogResponse;
import com.xiaoanjujia.home.entities.TypeOfRoleResponse;
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
@Route(path = "/supervisorActivity/supervisorActivity")
public class SupervisorActivity extends BaseActivity implements SupervisorContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    SupervisorPresenter mPresenter;
    private static final String TAG = "SupervisorActivity";

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
    private SupervisorPreviewsAdapter adapter;
    private int page = 1, datetype = 1, id = 0;
    private AutoFlowLayout aflCotent;
    private AutoFlowLayout aflJobsToChoose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();

        initTypeRoieData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("商城");
    }

    private void initView() {
        DaggerSupervisorActivityComponent.builder()
                .appComponent(getAppComponent())
                .supervisorPresenterModule(new SupervisorPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mLayoutInflater = LayoutInflater.from(this);


        findPullRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SupervisorPreviewsAdapter(R.layout.item_supervisor_recyclerview);
        adapter.setOnLoadMoreListener(this);

        View itemHeader = mLayoutInflater.inflate(R.layout.item_supervisor_recyclerview_header, null);
        aflCotent = itemHeader.findViewById(R.id.afl_cotent);
        aflJobsToChoose = itemHeader.findViewById(R.id.afl_jobs_to_choose);
        adapter.addHeaderView(itemHeader);
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        mRecyclerView.setAdapter(adapter);

        aflCotent.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //时间
                if (view.isSelected()) {
                    datetype = listDateType.get(position);

                } else {
                    datetype = 1;//默认
                }
                page = 1;
                initData(page, datetype, id);
            }
        });

        aflJobsToChoose.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //职位
                //时间
                if (view.isSelected()) {
                    id = listWorkId.get(position);

                } else {
                    id = 0;//默认
                }
                page = 1;
                initData(page, datetype, id);

            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public boolean onItemChildClick(BaseQuickAdapter baseAdapter, View view, int position) {
                int i = view.getId();
                if (i == R.id.item_supervisor_btn_status) {

                    List data = adapter.getData();
                    PropertyManagementListLogResponse.DataBean dateBean = (PropertyManagementListLogResponse.DataBean) data.get(position);
                    int id = dateBean.getId();
                    Intent intent = new Intent(SupervisorActivity.this, RecordDetailActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }

                return true;
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List data = adapter.getData();
                PropertyManagementListLogResponse.DataBean dateBean = (PropertyManagementListLogResponse.DataBean) data.get(position);
                int id = dateBean.getId();
                Intent intent = new Intent(SupervisorActivity.this, RecordDetailActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

    }

    //page
    //datetype :时间类型默认----是1(当天)2(本周)3(本月)4(上月)5(近三月)
    //id:角色id  默认0  全部
    private void initData(int page, int datetype, int id) {

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("page", String.valueOf(page));
        mapParameters.put("datetype", String.valueOf(datetype));
        mapParameters.put("id", String.valueOf(id));


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }

    private void initTypeRoieData() {
        // roletype :1是物业主管.2普通物业
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("roletype", "1");
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getTypesOfRoleData(headersTreeMap, mapParameters);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void setResponseData(PropertyManagementListLogResponse mPropertyManagementListLogResponse) {
        try {
            int code = mPropertyManagementListLogResponse.getStatus();
            String msg = mPropertyManagementListLogResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<PropertyManagementListLogResponse.DataBean> messageDate = mPropertyManagementListLogResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            adapter.setEnableLoadMore(false);
                        } else {
                            adapter.setEnableLoadMore(true);
                        }
                        List<PropertyManagementListLogResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setEnableLoadMore(false);
                        List<PropertyManagementListLogResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.notifyDataSetChanged();
                    }

                } else {

                    rlNoData.setVisibility(View.VISIBLE);
                    adapter.setEnableLoadMore(false);
                    List<PropertyManagementListLogResponse.DataBean> data = adapter.getData();
                    data.clear();
                    adapter.notifyDataSetChanged();
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
    public void setMoreData(PropertyManagementListLogResponse moreDate) {
        try {
            int code = moreDate.getStatus();
            String msg = moreDate.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<PropertyManagementListLogResponse.DataBean> data = moreDate.getData();
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


            } else if (code == ResponseCode.SEESION_ERROR) {
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
    public void setTypesOfRoleData(TypeOfRoleResponse mTypeOfRoleResponse) {
        try {
            int code = mTypeOfRoleResponse.getStatus();
            String msg = mTypeOfRoleResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {

                TypeOfRoleResponse.DataBean data = mTypeOfRoleResponse.getData();
                listDate.clear();
                listWork.clear();
                List<TypeOfRoleResponse.DataBean.OrdinarydateBean> ordinarydate = data.getOrdinarydate();
                List<TypeOfRoleResponse.DataBean.OrdinaryroleBean> ordinaryrole = data.getOrdinaryrole();
                for (int i = 0; i < ordinarydate.size(); i++) {
                    listDate.add(ordinarydate.get(i).getName());
                    listDateType.add(ordinarydate.get(i).getDatetype());
                }
                for (int i = 0; i < ordinaryrole.size(); i++) {
                    listWork.add(ordinaryrole.get(i).getName());
                    listWorkId.add(ordinaryrole.get(i).getId());
                }

                aflCotent.setAdapter(new FlowAdapter(listDate) {
                    @Override
                    public View getView(int position) {
                        View item = mLayoutInflater.inflate(R.layout.item_supervisor, null);
                        TextView tvAttrTag = (TextView) item.findViewById(R.id.tv_attr_tag);
                        tvAttrTag.setText(listDate.get(position));
                        return item;
                    }
                });

                aflJobsToChoose.setAdapter(new FlowAdapter(listWork) {
                    @Override
                    public View getView(int position) {
                        View item = mLayoutInflater.inflate(R.layout.item_supervisor, null);
                        TextView tvAttrTag = (TextView) item.findViewById(R.id.tv_attr_tag);
                        tvAttrTag.setText(listWork.get(position));
                        return item;
                    }
                });
                initData(page, datetype, id);
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

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("page", String.valueOf(page));
        mapParameters.put("datetype", String.valueOf(datetype));
        mapParameters.put("id", String.valueOf(id));


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getMoreData(headersTreeMap, mapParameters);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData(page, datetype, id);
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
