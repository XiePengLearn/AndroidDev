package com.xiaoanjujia.home.composition.me.deposit;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.SelectPicPopupWindow;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
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
 * @author xiepeng
 */
@Route(path = "/depositActivity/depositActivity")
public class DepositActivity extends BaseActivity implements DepositContract.View {
    @Inject
    DepositPresenter mPresenter;
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
    @BindView(R2.id.edit_post_message_title)
    EditText editPostMessageTitle;
    @BindView(R2.id.tv_current_money_des)
    TextView tvCurrentMoneyDes;
    @BindView(R2.id.ll_recharge)
    LinearLayout llRecharge;
    @BindView(R2.id.edit_post_message_des)
    EditText editPostMessageDes;
    @BindView(R2.id.post_message_visiting_time)
    TextView postMessageVisitingTime;
    @BindView(R2.id.post_message_visiting_time_ll)
    LinearLayout postMessageVisitingTimeLl;
    @BindView(R2.id.post_message_btn)
    AlphaButton postMessageBtn;
    @BindView(R2.id.ll_knowledge_publish_root)
    LinearLayout llKnowledgePublishRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);

        initView();
        //        initData();
        initTitle();
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("充值");
    }

    private void initView() {
        DaggerDepositActivityComponent.builder()
                .appComponent(getAppComponent())
                .depositPresenterModule(new DepositPresenterModule(this, MainDataManager.getInstance(mDataManager)))
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

    @OnClick({R2.id.main_title_back, R2.id.post_message_visiting_time_ll, R2.id.post_message_btn})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {
            finish();
        } else if (id == R.id.post_message_visiting_time_ll) {
            hideKeyboard(view);
            SelectPicPopupWindow selectPicPopupWindow = new SelectPicPopupWindow(mContext, llKnowledgePublishRoot);
            selectPicPopupWindow.setData2("微信", "支付宝", null);
            selectPicPopupWindow.setOnSelectItemOnclickListener(new SelectPicPopupWindow.OnSelectItemOnclickListener() {
                @Override
                public void selectItem(String str) {

                    if ("微信".equals(str)) {
                        //微信
                        postMessageVisitingTime.setText("微信");
                    } else if ("支付宝".equals(str)) {
                        //支付宝
                        postMessageVisitingTime.setText("支付宝");
                    } else {

                    }
                }
            });
            selectPicPopupWindow.showPopWindow();
        } else if (id == R.id.post_message_btn) {
            String depositPrice = editPostMessageTitle.getText().toString().trim();

            String depositDes = editPostMessageDes.getText().toString().trim();

            if (Utils.isNull(depositPrice)) {
                ToastUtil.showToast(mContext.getApplicationContext(), "其输入充值金额");
                return;
            }
            if (!Utils.isNull(depositPrice)) {
                double v = Double.parseDouble(depositPrice);
                if (v < 100) {
                    ToastUtil.showToast(mContext.getApplicationContext(), "最低充值金额：￥100");
                    return;
                }

            }
            ToastUtil.showToast(BaseApplication.getInstance(), "支付申请中");
        }
    }
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
