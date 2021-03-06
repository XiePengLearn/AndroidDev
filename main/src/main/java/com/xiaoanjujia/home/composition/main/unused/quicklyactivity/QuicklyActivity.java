package com.xiaoanjujia.home.composition.main.unused.quicklyactivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xiepeng
 */
@Route(path = "/quicklyActivity/quicklyActivity")
public class QuicklyActivity extends BaseActivity implements QuicklyContract.View {
    @Inject
    QuicklyPresenter mPresenter;
    private static final String TAG = "CompositionDetailActivity";

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
    EasyRecyclerView chatList;
    @BindView(R2.id.no_data_img)
    ImageView noDataImg;
    @BindView(R2.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickly);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        initData();
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
        DaggerQuicklyActivityComponent.builder()
                .appComponent(getAppComponent())
                .quicklyPresenterModule(new QuicklyPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

    }

    private void initData() {

        Map<String, Object> mapParameters = new HashMap<>(1);
        //        mapParameters.put("ACTION", "I002");


        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
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
}
