package com.xiaoanjujia.home.composition.success.submit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PhoneValidator;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
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
 * 提交成功
 */
@Route(path = "/SubmitSuccessActivity/SubmitSuccessActivity")
public class SubmitSuccessActivity extends BaseActivity implements SubmitSuccessContract.View {
    @Inject
    SubmitSuccessPresenter presenter;


    private static final String TAG = "PublishSuccessActivity";
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
    @BindView(R2.id.register_success_entry)
    AlphaButton registerSuccessEntry;
    @BindView(R2.id.text1_submit)
    TextView text1Submit;
    @BindView(R2.id.text2_submit)
    TextView text2Submit;

    private LoginResponse loginResponse;
    private boolean isClickForgetPassword = false;
    private String mUserName;
    private String mPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_success_activity);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            String hindText = intent.getStringExtra("hindText");
            if (!Utils.isNull(hindText) && hindText.equals("hindText")) {
                text1Submit.setText("请返回首页进行人脸采集，您提交的所有信息将在24小时内审核完成，请耐心等待");
                text1Submit.setVisibility(View.INVISIBLE);
            }
        }
        initView();
        initTitle();

    }

    /**
     * 初始化title
     */
    public void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText(R.string.submit_successfully);
    }

    private void initView() {


        DaggerSubmitSuccessActivityComponent.builder()
                .appComponent(getAppComponent())
                .submitSuccessPresenterModule(new SubmitSuccessPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);


        mUserName = PrefUtils.readUserName(this.getApplicationContext());
        mPassword = PrefUtils.readPassword(this.getApplicationContext());
    }

    public void initData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getLoginData(mapHeaders, mapParameters);
    }


    @Override
    public void setLoginData(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        try {
            int code = loginResponse.getStatus();
            String msg = loginResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LogUtil.e(TAG, "SESSION_ID: " + loginResponse.getData().getToken());
                LoginResponse.DataBean data = loginResponse.getData();
                String SESSION_ID = data.getToken();
                //保存账号密码   储存状态 SESSION_ID
                PrefUtils.writeUserName(mUserName, this.getApplicationContext());
                PrefUtils.writePhone(mUserName, this.getApplicationContext());
                PrefUtils.writePassword(mPassword, this.getApplicationContext());
                PrefUtils.writeCheckRemember(true, this.getApplicationContext());
                PrefUtils.writeSESSION_ID(SESSION_ID, this.getApplicationContext());

                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(this);
                finish();
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }

            }


        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }


    }

    @OnClick({R2.id.main_title_back, R2.id.register_success_entry})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.main_title_back) {
            finish();

        } else if (i == R.id.register_success_entry) {
            finish();
            //            LoginMethod();
        }
    }

    private void LoginMethod() {

        if (TextUtils.isEmpty(mUserName)) {
            ToastUtil.showToast(mContext, getResources().getString(
                    R.string.username_not_empty), Toast.LENGTH_SHORT);
            return;
        }
        String errorMsg = PhoneValidator.validate(mUserName);
        if (null != errorMsg) {
            ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(mPassword)) {
            ToastUtil.showToast(mContext, getResources().getString(
                    R.string.password_not_empty), Toast.LENGTH_SHORT);
            return;
        }

        //密码加密 并没有用到 我给注释了
        /**
         * 用户登录
         * 接口注意
         * 地址:/api/v1/login
         * 请求方式:post
         * http code：200
         * 请求参数
         * phone:用户名(手机号)(都传)
         * 密码登录
         * password:密码
         * 验证码登录
         * code
         * 返回参数
         * token
         * 接口返回
         * {
         *     "status": 1,
         *     "message": "登录成功",
         *     "data": {
         *         "token": "S9IUILwR98oAnV97jcDGzlc8w7xSczV7g9mdlP2+soksQMUBYOeetH8d7qT8/YVz",
         *         "user_id": 35
         *     }
         * }
         */
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("phone", mUserName);
        mapParameters.put("password", mPassword);
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        initData(headersTreeMap, mapParameters);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("loginResponse", loginResponse);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            LoginResponse loginResponse = (LoginResponse) savedInstanceState.getSerializable("loginResponse");
            this.loginResponse = loginResponse;

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
        if (presenter != null) {
            presenter.destory();
        }
    }
}
