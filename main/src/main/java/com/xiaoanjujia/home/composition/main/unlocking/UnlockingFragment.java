package com.xiaoanjujia.home.composition.main.unlocking;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseFragment;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.main.unused.quicklyfragment.DaggerQuicklyFragmentComponent;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.HashMap;
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
public class UnlockingFragment extends BaseFragment implements UnlockingFragmentContract.View, PtrHandler {
    @Inject
    UnlockingFragmentPresenter mPresenter;
    @BindView(R2.id.no_data_img)
    ImageView noDataImg;
    @BindView(R2.id.rl_fragment_no_data)
    RelativeLayout rlFragmentNoData;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;

    private Handler mHandler;

    private static final String TAG = "NationExamActivity";

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quickly, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {

        Bundle arguments = getArguments();

        initView();
        initData();

    }

    @Override
    public void onLazyLoad() {

    }

    public static UnlockingFragment newInstance() {
        UnlockingFragment newInstanceFragment = new UnlockingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        newInstanceFragment.setArguments(bundle);
        return newInstanceFragment;
    }

    public void initView() {

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerUnlockingFragmentComponent.builder()
                .appComponent(getAppComponent())
                .unlockingFragmentModule(new UnlockingFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        //
        findPullRefreshHeader.setPtrHandler(this);
        //                findRecyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        //                adapter = new FindsAdapter(R.layout.item_finds_recyclerview);
        //                adapter.setOnLoadMoreListener(this);
        //                adapter.setEnableLoadMore(true);
        //                findRecyclerview.setAdapter(adapter);

        mHandler = new Handler();

    }

    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
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
                ARouter.getInstance().build("/login/login").greenChannel().navigation(mActivity);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(mActivity.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(mActivity.getApplicationContext(), "解析数据失败");
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
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                frame.refreshComplete();
            }
        }, 500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


}
