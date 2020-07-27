package com.xiaoanjujia.home.composition.login.code_login;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.HandlerFactory;
import com.xiaoanjujia.common.util.PhoneValidator;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.LoginResponse;
import com.xiaoanjujia.home.entities.RegisterCodeResponse;
import com.xiaoanjujia.home.entities.RegisterResponse;
import com.xiaoanjujia.home.tool.Api;
import com.xiaoanjujia.home.tool.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xiepeng
 */
@Route(path = "/codeLogin/codeLogin")
public class CodeLoginActivity extends BaseActivity implements CodeLoginContract.View {

    @Inject
    CodeLoginPresenter presenter;
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
    @BindView(R2.id.reg_phone)
    EditText regPhone;
    @BindView(R2.id.reg_verification_code)
    EditText regVerificationCode;
    @BindView(R2.id.btn_getValidateCode)
    Button btnGetValidateCode;
    @BindView(R2.id.reg_btn_register)
    Button regBtnRegister;
    @BindView(R2.id.ll_register_root_view)
    LinearLayout llRegisterRootView;


    private int timeLong = 90;
    private Timer mTimer;
    //    private CodeUtils mCodeUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_login);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        initView();
        initTitle();
        String userName = PrefUtils.readUserName(this.getApplicationContext());
        if (!Util.isNull(userName)) {
            regPhone.setText(userName);
            regPhone.setSelection(userName.length());
        }

    }

    private void initView() {
        DaggerCodeLoginActivityComponent.builder()
                .appComponent(getAppComponent())
                .codeLoginPresenterModule(new CodeLoginPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        //        mCodeUtils = CodeUtils.getInstance();
        //        Bitmap bitmap = mCodeUtils.createBitmap();
        //        imageCode.setImageBitmap(bitmap);
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText(R.string.register_code_login);
    }

    /**
     * {
     * "status": 1,
     * "message": "ok",
     * "data": {
     * "password": "15da5b87fbda7ab1a95e471a1247abce",
     * "user_name": "18635805566"
     * }
     *
     * @param loginResponse
     */
    @Override
    public void setResponseData(LoginResponse loginResponse) {
        try {
            int code = loginResponse.getStatus();
            String msg = loginResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(BaseApplication.getInstance(), msg);
                }
                LoginResponse.DataBean data = loginResponse.getData();
                String SESSION_ID = data.getToken();
                //保存账号密码   储存状态 SESSION_ID
                PrefUtils.writeUserName(regPhone.getText().toString().trim(), BaseApplication.getInstance());
                PrefUtils.writeCheckRemember(true, BaseApplication.getInstance());
                PrefUtils.writeCheckRemember(true, BaseApplication.getInstance());
                PrefUtils.writeSESSION_ID(SESSION_ID, BaseApplication.getInstance());

                ARouter.getInstance().build("/main/MainActivity").greenChannel().navigation(CodeLoginActivity.this);
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

    //    {"status":1,"message":"OK","data":[]}
    @Override
    public void setCodeResponseData(RegisterCodeResponse registerCodeResponse) {
        try {
            int code = registerCodeResponse.getStatus();
            String msg = registerCodeResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                countDown();
                ToastUtil.showToast(this.getApplicationContext(), "验证码发送成功");
            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面

            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(this.getApplicationContext(), msg);
                }
                btnGetValidateCode.setClickable(true);
            }


        } catch (Exception e) {
            ToastUtil.showToast(this.getApplicationContext(), "解析数据失败");
        }
    }


    @OnClick({R2.id.main_title_back, R2.id.btn_getValidateCode, R2.id.reg_btn_register

    })
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.main_title_back) {

            finish();

        } else if (i == R.id.btn_getValidateCode) {
            String lPhone = regPhone.getText().toString().trim();
            String errorMsg = PhoneValidator.validate(lPhone);
            if (null != errorMsg) {
                ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
                return;
            }
            getValidateCodeRequest();

        } else if (i == R.id.reg_btn_register) {

            //注册
            RegisterMethod();

        } /*else if (i == R.id.image_code) {
            //更换图形验证码
            Bitmap bitmap = mCodeUtils.createBitmap();
            imageCode.setImageBitmap(bitmap);

        }*/
    }

    private void RegisterMethod() {
        String errorMsg = PhoneValidator.validate(regPhone.getText().toString()
                .trim());
        if (null != errorMsg) {
            ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
            return;
        }

        String lValidateCode = regVerificationCode.getText().toString().trim();
        if (Util.isNull(lValidateCode)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.verification_not_empty),
                    Toast.LENGTH_SHORT);
            return;
        }

        /**
         * phone:用户名(手机号)
         * password:密码
         * code:验证码
         * 返回参数
         *
         * user_name:用户名(手机号)
         * password
         * 接口返回
         *
         * {
         *     "status": 1,
         *     "message": "ok",
         *     "data": {
         *         "password": "15da5b87fbda7ab1a95e471a1247abce",
         *         "user_name": "18635805566"
         *     }
         */

        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("phone", regPhone.getText().toString().trim());
        //        mapParameters.put("password", lPassword);
        mapParameters.put("code", lValidateCode);

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        initData(headersTreeMap, mapParameters);
    }

    public void initData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getRequestData(mapHeaders, mapParameters);
    }

    /**
     * 获取验证码
     * <p>
     * 字段 参考值 备注
     * sign sdjnjndkjmdfskljmnmj 唯一值
     * vesion 1 版本号
     * app-type ios app的类型
     * did 12233 设备号
     * os 2.3 设备的操作系统
     * model apple app机型
     * user-token sdj44w343nweicjwrerkc 登录后需要携带的参数
     * time 12345455 当前时间
     */
    public void getValidateCodeRequest() {

        btnGetValidateCode.setClickable(false);

        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("phone", regPhone.getText().toString().trim());

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        initGetCodeData(headersTreeMap, mapParameters);


    }

    private void initGetCodeData(TreeMap<String, String> mapHeaders, Map<String, Object> mapParameters) {
        presenter.getCodeRequestData(mapHeaders, mapParameters);
    }

    private final int COUNTDOWN = 3;
    HandlerFactory.OnMessageListener messageListener = new HandlerFactory.OnMessageListener() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case COUNTDOWN:
                    //                    btnGetValidateCode.setText(String.valueOf(msg.arg1) + "s");
                    btnGetValidateCode.setText(msg.arg1 + "s");
                    if (msg.arg1 == 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            regVerificationCode.setHint(R.string.register_verification_code);
                            btnGetValidateCode.setText(R.string.register_get_verification_code);
                            btnGetValidateCode.setClickable(true);
                        }
                    }
                    break;
            }
        }
    };
    HandlerFactory.WeakHandler mHandler = HandlerFactory.buildWeakHandler(this, messageListener);

    /**
     * 倒计时器
     */
    public void countDown() {
        timeLong = 90;
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message message = Message.obtain(mHandler);
                message.what = COUNTDOWN;
                message.arg1 = timeLong--;
                message.sendToTarget();
            }
        }, 1000, 1000);
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

        if (mTimer != null) {
            mTimer.cancel();

        }
    }
}
