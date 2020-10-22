package com.xiaoanjujia.home.composition.login.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.AppManager;
import com.xiaoanjujia.common.util.LogUtil;
import com.xiaoanjujia.common.util.PhoneValidator;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.ClearEditText;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.html.activity_html.MyWebActivity;
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
@Route(path = "/login/login")
public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginPresenter presenter;


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
    @BindView(R2.id.edit_account)
    ClearEditText editAccount;
    @BindView(R2.id.edit_password)
    ClearEditText editPassword;
    @BindView(R2.id.login_entry)
    AlphaButton loginEntry;
    @BindView(R2.id.login_remember_passwords)
    CheckBox loginRememberPasswords;
    @BindView(R2.id.fast_login)
    TextView fastLogin;
    @BindView(R2.id.login_find_password)
    TextView loginFindPassword;
    @BindView(R2.id.register)
    TextView register;

    @BindView(R2.id.yin_si)
    LinearLayout yinSi;
    private Button mLoginEntry;
    private LoginResponse loginResponse;
    private boolean isClickForgetPassword = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        String param = getIntent().getStringExtra("param");
        if (!Utils.isNull(param) && param.equals("web")) {
            AppManager.getInstance().finishOthersToActivity(LoginActivity.class);
        }

        initView();
        initTitle();

    }

    /**
     * 初始化title
     */
    public void initTitle() {
        mainTitleBack.setVisibility(View.INVISIBLE);
        mainTitleText.setText(R.string.login_entry);
    }

    private void initView() {


        DaggerLoginActivityComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        editAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editAccount.setClearIconVisible(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editPassword.setClearIconVisible(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        String lUserName = PrefUtils.readUserName(this.getApplicationContext());

        if (!TextUtils.isEmpty(lUserName)) {
            editAccount.setText(lUserName.trim());
            editAccount.setSelection(lUserName.length());// 获得焦点
        } else {
            editAccount.setText("");
        }

        String mPassword = PrefUtils.readPassword(this.getApplicationContext());

        if (!TextUtils.isEmpty(mPassword)) {
            editPassword.setText(mPassword.trim());
            editPassword.setSelection(mPassword.length());// 获得焦点
        } else {
            editPassword.setText("");
        }

        loginRememberPasswords.setChecked(PrefUtils.readCheckRemember(this.getApplicationContext()));


    }

    public void initData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getLoginData(mapHeaders, mapParameters);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isClickForgetPassword) {
            isClickForgetPassword = false;
            String phone = PrefUtils.readUserName(this.getApplicationContext());
            String password = PrefUtils.readPassword(this.getApplicationContext());
            boolean checked = PrefUtils.readCheckRemember(this.getApplicationContext());

            if (checked) {
                if (!TextUtils.isEmpty(phone)) {
                    editAccount.setText(phone);
                }
                if (!TextUtils.isEmpty(password)) {
                    editPassword.setText(password);
                }
            }
        }
    }


    @Override
    public void setLoginData(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        //roletype:---0是普通用户---1是物业主管----2是物业人员
        try {
            int code = loginResponse.getStatus();
            String msg = loginResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                LogUtil.e(TAG, "SESSION_ID: " + loginResponse.getData().getToken());
                boolean checked = loginRememberPasswords.isChecked();
                LoginResponse.DataBean data = loginResponse.getData();
                String SESSION_ID = data.getToken();
                int roletype = data.getRoletype();
                int user_id = data.getUser_id();

                PrefUtils.writeRoleType(roletype, BaseApplication.getInstance());
                PrefUtils.writeUserId(String.valueOf(user_id), BaseApplication.getInstance());
                PrefUtils.writePhone(editAccount.getText().toString().trim(), this.getApplicationContext());

                if (checked) {
                    //保存账号密码   储存状态 SESSION_ID
                    PrefUtils.writeUserName(editAccount.getText().toString().trim(), this.getApplicationContext());
                    PrefUtils.writePassword(editPassword.getText().toString().trim(), this.getApplicationContext());
                    PrefUtils.writeCheckRemember(true, this.getApplicationContext());
                    PrefUtils.writeSESSION_ID(SESSION_ID, this.getApplicationContext());


                } else {
                    //保存账号密码   储存状态 SESSION_ID
                    PrefUtils.writeUserName("", this.getApplicationContext());
                    PrefUtils.writePassword("", this.getApplicationContext());
                    PrefUtils.writeCheckRemember(false, this.getApplicationContext());
                    PrefUtils.writeSESSION_ID(SESSION_ID, this.getApplicationContext());
                }
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

    @OnClick({R2.id.main_title_back, R2.id.edit_account, R2.id.edit_password,
            R2.id.login_remember_passwords, R2.id.login_find_password,
            R2.id.login_entry, R2.id.register
            , R2.id.fast_login
            , R2.id.yin_si
    })
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.main_title_back) {

        } else if (i == R.id.edit_account) {
            String editAccountData = editAccount.getText().toString().trim();

            editAccount.setSelection(editAccountData.length());// 获得焦点
        } else if (i == R.id.edit_password) {
            String editPasswordData = editPassword.getText().toString().trim();

            editPassword.setSelection(editPasswordData.length());// 获得焦点
        } else if (i == R.id.login_remember_passwords) {

        } else if (i == R.id.login_find_password) {
            //忘记密码
            isClickForgetPassword = true;
            ARouter.getInstance().build("/forget/forget").greenChannel().navigation(this);
            //            finish();

        } else if (i == R.id.login_entry) {
            //登录
            LoginMethod();
        } else if (i == R.id.register) {
            ARouter.getInstance().build("/register/register").greenChannel().navigation(this);
            //            finish();
        } else if (i == R.id.fast_login) {
            ARouter.getInstance().build("/codeLogin/codeLogin").greenChannel().navigation(this);
            //            finish();
        } else if (i == R.id.yin_si) {
            Intent intent = new Intent(LoginActivity.this, MyWebActivity.class);
            intent.putExtra("url", "https://www.xiaoanjujia.com/xieyi.html");
            startActivity(intent);
        }
    }

    private void LoginMethod() {
        String lAccount = editAccount.getText().toString().trim();
        if (TextUtils.isEmpty(lAccount)) {
            ToastUtil.showToast(mContext, getResources().getString(
                    R.string.username_not_empty), Toast.LENGTH_SHORT);
            return;
        }
        String errorMsg = PhoneValidator.validate(lAccount);
        if (null != errorMsg) {
            ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
            return;
        }

        String lPassword = editPassword.getText().toString().trim();
        if (TextUtils.isEmpty(lPassword)) {
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
        mapParameters.put("phone", lAccount);
        mapParameters.put("password", lPassword);

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
