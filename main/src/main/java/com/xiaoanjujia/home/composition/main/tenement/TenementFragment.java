package com.xiaoanjujia.home.composition.main.tenement;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseFragment;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description: 物业管理
 */
public class TenementFragment extends BaseFragment implements TenementFragmentContract.View, PtrHandler {
    @Inject
    TenementFragmentPresenter mPresenter;


    private static final String TAG = "NationExamActivity";
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
    @BindView(R2.id.tenement_department_head_btn)
    AlphaButton tenementDepartmentHeadBtn;
    @BindView(R2.id.tenement_department_head_rl)
    RelativeLayout tenementDepartmentHeadRl;
    @BindView(R2.id.tenement_personnel_btn)
    AlphaButton tenementPersonnelBtn;
    @BindView(R2.id.tenement_personnel_rl)
    RelativeLayout tenementPersonnelRl;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tenement, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initEvent() {
        initView();
        //        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.INVISIBLE);
        mainTitleText.setText("物业管理");
    }

    @Override
    public void onLazyLoad() {

    }

    public static TenementFragment newInstance() {
        TenementFragment newInstanceFragment = new TenementFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        newInstanceFragment.setArguments(bundle);
        return newInstanceFragment;
    }

    public void initView() {
        DaggerTenementFragmentComponent.builder()
                .appComponent(getAppComponent())
                .tenementFragmentModule(new TenementFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        findPullRefreshHeader.setPtrHandler(this);
    }

    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
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


    @OnClick({R2.id.tenement_department_head_btn, R2.id.tenement_personnel_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.tenement_department_head_btn) {
            ARouter.getInstance().build("/supervisorActivity/supervisorActivity").greenChannel().navigation(mContext);
        } else if (id == R.id.tenement_personnel_btn) {
            ARouter.getInstance().build("/staffActivity/staffActivity").greenChannel().navigation(mContext);
        }
    }
}
