package com.xiaoanjujia.home.composition.login.forget;

import android.graphics.Bitmap;
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
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.CodeUtils;
import com.xiaoanjujia.common.util.HandlerFactory;
import com.xiaoanjujia.common.util.PhoneValidator;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.StringUtils;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.entities.ForgerResponse;
import com.xiaoanjujia.home.entities.RegisterCodeResponse;
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
@Route(path = "/forget/forget")
public class ForgerPasswordActivity extends BaseActivity implements ForgerPasswordContract.View {

    @Inject
    ForgerPasswordPresenter presenter;
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
    AlphaButton btnGetValidateCode;
    @BindView(R2.id.reg_password)
    EditText regPassword;
    @BindView(R2.id.et_image_code)
    EditText etImageCode;
    @BindView(R2.id.image_code)
    ImageView imageCode;
    @BindView(R2.id.reg_btn_register)
    AlphaButton regBtnRegister;
    @BindView(R2.id.ll_register_root_view)
    LinearLayout llRegisterRootView;



    private int timeLong = 90;
    private Timer mTimer;
    private CodeUtils mCodeUtils;
    private ForgerResponse forgerResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forger);
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
        DaggerForgerPasswordActivityComponent.builder()
                .appComponent(getAppComponent())
                .forgerPasswordPresenterModule(new ForgerPasswordPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        mCodeUtils = CodeUtils.getInstance();
        Bitmap bitmap = mCodeUtils.createBitmap();
        imageCode.setImageBitmap(bitmap);
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText(R.string.register_forget_password);
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
     * @param forgerResponse
     */
    @Override
    public void setResponseData(ForgerResponse forgerResponse) {
        this.forgerResponse = forgerResponse;
        try {
            int code = forgerResponse.getStatus();
            String msg = forgerResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                ToastUtil.showToast(this.getApplicationContext(), getResources().getString(R.string.forger_successfully));
                PrefUtils.writeUserName(regPhone.getText().toString().trim(), BaseApplication.getInstance());
                PrefUtils.writePassword(regPassword.getText().toString().trim(), BaseApplication.getInstance());
                PrefUtils.writeCheckRemember(true, BaseApplication.getInstance());
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("forgerResponse", forgerResponse);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            ForgerResponse forgerResponse = (ForgerResponse) savedInstanceState.getSerializable("forgerResponse");
            this.forgerResponse = forgerResponse;

        }
    }


    @OnClick({R2.id.main_title_back, R2.id.btn_getValidateCode, R2.id.reg_btn_register, R2.id.image_code

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

        } else if (i == R.id.image_code) {
            //更换图形验证码
            Bitmap bitmap = mCodeUtils.createBitmap();
            imageCode.setImageBitmap(bitmap);

        }
    }

    private void RegisterMethod() {

        String lPassword = regPassword.getText().toString().trim();
        if (TextUtils.isEmpty(lPassword)) {
            ToastUtil.showToast(
                    mContext,
                    "请填写密码（密码长度6-16个字符，要包含字母和数字）", Toast.LENGTH_SHORT);
            return;
        }
        if (!StringUtils.isPasswordRegex(lPassword)) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.password_character_restrict), Toast.LENGTH_SHORT);
            return;
        }
        //        if (!lPassword.equals(lAgainPassword)) {
        //            ToastUtil.showToast(
        //                    mContext,
        //                    mContext.getResources().getString(
        //                            R.string.password_not_same), Toast.LENGTH_SHORT);
        //            return;
        //        }
        String errorMsg = PhoneValidator.validate(regPhone.getText().toString()
                .trim());
        if (null != errorMsg) {
            ToastUtil.showToast(mContext, errorMsg, Toast.LENGTH_SHORT);
            return;
        }

        String lValidateCode = regVerificationCode.getText().toString().trim();
        if (null == lValidateCode || lValidateCode.length() == 0) {
            ToastUtil.showToast(
                    mContext,
                    mContext.getResources().getString(
                            R.string.verification_not_empty),
                    Toast.LENGTH_SHORT);
            return;
        }

        /**
         * 修改密码
         * header参数每次都要携带
         *
         * 1.接口地址 /api/v1/editPassword
         * 2.请求方式 put
         * 请求参数
         *
         * phone:用户名手机号
         * password:密码
         * password1:确认密码
         * code:1234验证码
         * 接口返回
         *
         * {
         *     "status": 1,
         *     "message": "修改成功",
         *     "data": []
         * }
         */

        Map<String, Object> mapParameters = new HashMap<>(6);
        mapParameters.put("phone", regPhone.getText().toString().trim());
        mapParameters.put("password", lPassword);
        mapParameters.put("password1", lPassword);
        //        mapParameters.put("RANDOM_NUMBER", lRandomNumber);
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
