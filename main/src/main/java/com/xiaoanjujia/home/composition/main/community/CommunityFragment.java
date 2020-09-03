package com.xiaoanjujia.home.composition.main.community;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseFragment;
import com.xiaoanjujia.common.base.baseadapter.BaseQuickAdapter;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.widget.SearchLayout;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.common.widget.view_switcher.UpDownViewSwitcher;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.community.category.CategoryDetailsActivity;
import com.xiaoanjujia.home.composition.community.details.CompositionDetailActivity;
import com.xiaoanjujia.home.entities.ComcateListsResponse;
import com.xiaoanjujia.home.entities.CommunitySearchResponse;
import com.xiaoanjujia.home.entities.StoreHot2Response;
import com.xiaoanjujia.home.entities.StoreHotMoreResponse;
import com.xiaoanjujia.home.entities.StoreHotResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description: 快速开发Fragment
 */
public class CommunityFragment extends BaseFragment implements CommunityFragmentContract.View, PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {
    @Inject
    CommunityFragmentPresenter mPresenter;
    private static final String TAG = "CommunityFragment";

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
    private CommunityFragmentAdapter adapter;
    private int page = 1;
    private RecyclerView aflCotent;
    private CommunityHomePageClassificationAdapter mAdapterResult;
    private UpDownViewSwitcher mHomeViewSwitcher;
    private LinearLayout mStoreHotMoreLl;
    private SearchLayout mSlSearchLayout;
    private String mInputTheKeyWord = "";
    private TextView mTvSearchButton;


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {

        initView();

        initTypeRoieData();
        initTitle();
        initStoreHot2Data();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.INVISIBLE);
        mainTitleText.setText("社区");
    }

    @Override
    public void onLazyLoad() {

    }

    public static CommunityFragment newInstance() {
        CommunityFragment newInstanceFragment = new CommunityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        newInstanceFragment.setArguments(bundle);
        return newInstanceFragment;
    }


    private void initView() {

        //        DaggerCommunityFragmentComponent.builder()
        //                .appComponent(getAppComponent())
        //                .communityFragmentModule(new CommunityFragmentModule(this, MainDataManager.getInstance(mDataManager)))
        //                .build()
        //                .inject(this);
        DaggerCommunityFragmentComponent.builder()
                .appComponent(getAppComponent())
                .communityFragmentModule(new CommunityFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        mLayoutInflater = LayoutInflater.from(getActivity());


        findPullRefreshHeader.setPtrHandler(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommunityFragmentAdapter(R.layout.item_community_fragment);
        adapter.setOnLoadMoreListener(this);

        View itemHeader = mLayoutInflater.inflate(R.layout.item_community_fragment_header, null);
        aflCotent = itemHeader.findViewById(R.id.rl_community_home_page_classification);
        mHomeViewSwitcher = itemHeader.findViewById(R.id.home_view_switcher);
        mStoreHotMoreLl = itemHeader.findViewById(R.id.store_hot_more_ll);
        mSlSearchLayout = itemHeader.findViewById(R.id.sl_searchLayout);
        mTvSearchButton = itemHeader.findViewById(R.id.tv_search_button);
        mTvSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                if (adapter != null) {
                    adapter.setEnableLoadMore(true);
                }
                //隐藏软键盘
                mSlSearchLayout.hideSoftInput();
                initData(page, mInputTheKeyWord);
            }
        });
        mSlSearchLayout.setListener(new SearchLayout.OnSearchListener() {


            @Override
            public void onCancel() {
                //                Toast.makeText(mContext, "取消", Toast.LENGTH_LONG).show();
                mInputTheKeyWord = "";
            }

            @Override
            public void onSearchKeyLister(String input) {
                //                Toast.makeText(mContext, input, Toast.LENGTH_LONG).show();


                //                if (TextUtils.isEmpty(input)) {
                //
                //                    //                    ToastUtil.showToast(HistorySearchActivity.this.getApplicationContext(), "请输入要搜索专家名称");
                //                    Toast.makeText(mContext, "请输入要搜索专家名称", Toast.LENGTH_LONG).show();
                //                    return;
                //                }

                LogUtil.e(TAG, "-----------执行次数----------:");
                mInputTheKeyWord = input;
                //隐藏软键盘
                mSlSearchLayout.hideSoftInput();
                page = 1;
                if (adapter != null) {
                    adapter.setEnableLoadMore(true);
                }
                initData(page, mInputTheKeyWord);
            }

            @Override
            public void onCurrentText(String text) {
                mInputTheKeyWord = text;
            }
        });
        mStoreHotMoreLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(BaseApplication.getInstance(), "热点更多 开发中");
            }
        });

        //搜索结果的展示
        CommunityGridLayoutManager manager = new CommunityGridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
        aflCotent.setLayoutManager(manager);
        mAdapterResult = new CommunityHomePageClassificationAdapter(R.layout.item_community_fragment_header_grald);
        mAdapterResult.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List data = adapter.getData();
                ComcateListsResponse.DataBean servicehistoryBean = (ComcateListsResponse.DataBean) data.get(position);
                String cateName = servicehistoryBean.getCate_name();
                int cate_id = servicehistoryBean.getCate_id();
                Intent intent = new Intent(mContext, CategoryDetailsActivity.class);
                intent.putExtra("cateName", cateName);
                intent.putExtra("cate_id", cate_id);
                startActivity(intent);
                //                ToastUtil.showToast(BaseApplication.getInstance(), "position:" + position);
            }
        });
        aflCotent.setAdapter(mAdapterResult);

        adapter.addHeaderView(itemHeader);
        adapter.setEnableLoadMore(true);
        adapter.loadMoreComplete();
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List data = adapter.getData();
                CommunitySearchResponse.DataBean dateBean = (CommunitySearchResponse.DataBean) data.get(position);
                int id = dateBean.getId();
                int community_id = dateBean.getCommunity_id();
                Intent intent = new Intent(getActivity(), CompositionDetailActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("community_id", community_id);
                startActivity(intent);
            }
        });

    }

    /**
     * 1.接口地址 /api/v1/communitysearch
     * page
     * shop_name:订单标题和详情
     *
     * @param page
     * @param shopName
     */
    private void initData(int page, String shopName) {

        Map<String, Object> mapParameters = new HashMap<>(3);
        mapParameters.put("page", String.valueOf(page));
        mapParameters.put("shop_name", shopName);


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }

    private void initTypeRoieData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getTypesOfRoleData(headersTreeMap, mapParameters);
    }


    @Override
    public void setResponseData(CommunitySearchResponse mCommunitySearchResponse) {
        try {
            int code = mCommunitySearchResponse.getStatus();
            String msg = mCommunitySearchResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<CommunitySearchResponse.DataBean> messageDate = mCommunitySearchResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            adapter.setEnableLoadMore(false);
                        } else {
                            adapter.setEnableLoadMore(true);
                        }
                        List<CommunitySearchResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setEnableLoadMore(false);
                        List<CommunitySearchResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.notifyDataSetChanged();
                    }

                } else {

                    rlNoData.setVisibility(View.VISIBLE);
                    adapter.setEnableLoadMore(false);
                    List<CommunitySearchResponse.DataBean> data = adapter.getData();
                    data.clear();
                    adapter.notifyDataSetChanged();
                }

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(getActivity());
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(getActivity().getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(getActivity().getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void setMoreData(CommunitySearchResponse moreDate) {
        try {
            int code = moreDate.getStatus();
            String msg = moreDate.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LogUtil.e(TAG, "SESSION_ID: " + moreDate.getData());
                List<CommunitySearchResponse.DataBean> data = moreDate.getData();
                if (data != null) {

                    for (int i = 0; i < data.size(); i++) {
                        adapter.getData().add(data.get(i));
                    }
                    if (data.size() < 10) {
                        adapter.setEnableLoadMore(false);
                    } else {
                        adapter.setEnableLoadMore(true);
                    }
                } else {
                    adapter.setEnableLoadMore(false);
                }


            } else if (code == ResponseCode.SEESION_ERROR) {
                adapter.loadMoreComplete();
                //SESSION_ID过期或者报错  要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mContext);

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
    public void setTypesOfRoleData(ComcateListsResponse mComcateListsResponse) {
        try {
            int code = mComcateListsResponse.getStatus();
            String msg = mComcateListsResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<ComcateListsResponse.DataBean> dataList = mComcateListsResponse.getData();
                if (dataList != null) {
                    if (dataList.size() > 0) {
                        List<ComcateListsResponse.DataBean> dataHistory = mAdapterResult.getData();
                        dataHistory.clear();
                        mAdapterResult.addData(dataList);
                    }
                }
                if (adapter != null) {
                    adapter.setEnableLoadMore(true);
                }
                initData(page, mInputTheKeyWord);
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(getActivity());
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(getActivity().getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(getActivity().getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void setStoreHotData(StoreHotResponse mStoreHotResponse) {
        try {
            int code = mStoreHotResponse.getStatus();
            String msg = mStoreHotResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                StoreHotResponse.DataBean data = mStoreHotResponse.getData();

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(getActivity());
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(getActivity().getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(getActivity().getApplicationContext(), "解析数据失败");
        }
    }

    private void initStoreHot2Data() {
        // roletype :1是物业主管.2普通物业
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("page", "1");
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getStoreHot2Data(headersTreeMap, mapParameters);
    }

    @Override
    public void setStoreHot2Data(StoreHot2Response mStoreHot2Response) {
        try {
            int code = mStoreHot2Response.getStatus();
            String msg = mStoreHot2Response.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                final List<StoreHot2Response.DataBean> data = mStoreHot2Response.getData();
                if (data != null) {
                    if (data.size() > 0) {
                        mHomeViewSwitcher.setSwitcheNextViewListener(new UpDownViewSwitcher.SwitchNextViewListener() {
                            @Override
                            public void switchTONextView(View nextView, int index) {
                                if (nextView == null)
                                    return;
                                final String tag = data.get(index % data.size()).getHotspot_text();
                                final String tag1 = data.get(index % data.size()).getCreateTime();
                                ((TextView) nextView.findViewById(R.id.textview)).setText(tag1);
                                ((TextView) nextView.findViewById(R.id.switch_title_text)).setText(tag);
                                nextView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(v.getContext().getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                        mHomeViewSwitcher.setContentLayout(R.layout.switch_view);
                    }
                }
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(getActivity());
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(getActivity().getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(getActivity().getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void setStoreHotDataMore(StoreHotMoreResponse mStoreHotMoreResponse) {
        try {
            int code = mStoreHotMoreResponse.getStatus();
            String msg = mStoreHotMoreResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<StoreHotMoreResponse.DataBean> data = mStoreHotMoreResponse.getData();
                if (data != null) {
                    if (data.size() > 0) {
                        //                        List<ComcateListsResponse.DataBean> dataHistory = mAdapterResult.getData();
                        //                        dataHistory.clear();
                        //                        mAdapterResult.addData(data);
                    }
                }
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(getActivity());
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(getActivity().getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(getActivity().getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void setCommunitySearch(CommunitySearchResponse mCommunitySearchResponse) {
        try {
            int code = mCommunitySearchResponse.getStatus();
            String msg = mCommunitySearchResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<CommunitySearchResponse.DataBean> messageDate = mCommunitySearchResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            adapter.setEnableLoadMore(false);
                        } else {
                            adapter.setEnableLoadMore(true);
                        }
                        List<CommunitySearchResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setEnableLoadMore(false);
                        List<CommunitySearchResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.notifyDataSetChanged();
                    }

                } else {

                    rlNoData.setVisibility(View.VISIBLE);
                    adapter.setEnableLoadMore(false);
                    List<CommunitySearchResponse.DataBean> data = adapter.getData();
                    data.clear();
                    adapter.notifyDataSetChanged();
                }

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(getActivity());
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(getActivity().getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(getActivity().getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void setCommunityList(CommunitySearchResponse mCommunityListResponse) {
        try {
            int code = mCommunityListResponse.getStatus();
            String msg = mCommunityListResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                List<CommunitySearchResponse.DataBean> messageDate = mCommunityListResponse.getData();
                if (messageDate != null) {
                    if (messageDate.size() > 0) {
                        if (messageDate.size() < 10) {
                            adapter.setEnableLoadMore(false);
                        } else {
                            adapter.setEnableLoadMore(true);
                        }
                        List<CommunitySearchResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.addData(messageDate);
                        rlNoData.setVisibility(View.GONE);
                    } else {
                        rlNoData.setVisibility(View.VISIBLE);
                        adapter.setEnableLoadMore(false);
                        List<CommunitySearchResponse.DataBean> data = adapter.getData();
                        data.clear();
                        adapter.notifyDataSetChanged();
                    }

                } else {

                    rlNoData.setVisibility(View.VISIBLE);
                    adapter.setEnableLoadMore(false);
                    List<CommunitySearchResponse.DataBean> data = adapter.getData();
                    data.clear();
                    adapter.notifyDataSetChanged();
                }

            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(getActivity());
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(getActivity().getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(getActivity().getApplicationContext(), "解析数据失败");
        }
    }

    @Override
    public void showProgressDialogView() {
        showJDLoadingDialog();
    }

    @Override
    public void hiddenProgressDialogView() {
        hideJDLoadingDialog();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onLoadMoreRequested() {

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                initMoreData(page, mInputTheKeyWord);
            }
        }, 500);
    }

    //page
    //datetype :时间类型默认----是1(当天)2(本周)3(本月)4(上月)5(近三月)
    //id:角色id  默认0  全部
    private void initMoreData(int page, String mInputTheKeyWord) {

        Map<String, Object> mapParameters = new HashMap<>(3);

        mapParameters.put("page", String.valueOf(page));
        mapParameters.put("shop_name", mInputTheKeyWord);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getMoreData(headersTreeMap, mapParameters);
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                if (adapter != null) {
                    adapter.setEnableLoadMore(true);
                }
                initData(page, mInputTheKeyWord);
                frame.refreshComplete();
            }
        }, 500);
    }

}
