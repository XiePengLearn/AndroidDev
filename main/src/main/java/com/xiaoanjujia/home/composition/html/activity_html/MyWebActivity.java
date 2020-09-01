package com.xiaoanjujia.home.composition.html.activity_html;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseActivity;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.util.statusbar.StatusBarUtil;
import com.xiaoanjujia.common.widget.X5WebView;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.common.widget.dialog.ConfirmDialog;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.login.login.LoginActivity;
import com.xiaoanjujia.home.composition.me.data.DataAnalysisActivity;
import com.xiaoanjujia.home.entities.ComExamineStatusResponse;
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
@Route(path = "/MyWebActivity/MyWebActivity")
public class MyWebActivity extends BaseActivity implements MyWebContract.View, ActivityWebInterface.JSActivityCallBack {
    @Inject
    MyWebPresenter presenter;
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
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;
    @BindView(R2.id.webView)
    X5WebView webView;
    @BindView(R2.id.deposit_page_loading)
    LinearLayout depositPageLoading;

    private static final String TAG = "NationExamActivity";
    @BindView(R2.id.data_fen_xi)
    Button dataFenXi;
    private Intent mIntent;
    private String mWebUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_webview);
        StatusBarUtil.setImmersiveStatusBar(this, true);
        unbinder = ButterKnife.bind(this);
        initTitle();
        initView();
        depositPageLoading.setVisibility(View.VISIBLE);
        initSetting(this);
    }

    private void initSetting(ActivityWebInterface.JSActivityCallBack jSActivityCallBack) {
        WebSettings settings = webView.getSettings();
        String userAgentString = settings.getUserAgentString();
        settings.setUserAgent(userAgentString + "xiaoan");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.addJavascriptInterface(new ActivityWebInterface().setJsCallback(jSActivityCallBack), "JsToAndroidBridge");
    }

    private void initView() {


        DaggerMyWebActivityComponent.builder()
                .appComponent(getAppComponent())
                .myWebPresenterModule(new MyWebPresenterModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        mIntent = getIntent();

        mWebUrl = mIntent.getStringExtra("url");
        webView.loadUrl(mWebUrl);
        MyWebChromClient webChromClient = new MyWebChromClient();
        webView.setWebChromeClient(webChromClient);


    }

    private void initTitle() {
        mainTitleBack.setVisibility(View.VISIBLE);
        mainTitleText.setText("");

    }

    @Override
    public String jsGetUserName() {

        return PrefUtils.readUserName(BaseApplication.getInstance());
    }

    @Override
    public String jsGetPassWord() {
        return PrefUtils.readPassword(BaseApplication.getInstance());
    }

    @Override
    public String jsGetUserToken() {
        return PrefUtils.readSESSION_ID(BaseApplication.getInstance());
    }

    @Override
    public String jsGetUserId() {

        return PrefUtils.readUserId(BaseApplication.getInstance());
    }

    @Override
    public void jsMerchantsCertification() {
        initData();
    }

    @Override
    public void jsGetLogOut() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PrefUtils.writeAuthenticationStatus("", BaseApplication.getInstance());
                Intent intent = new Intent(MyWebActivity.this, LoginActivity.class);
                intent.putExtra("param", "web");
                startActivity(intent);
            }
        });
    }

    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        presenter.getRequestData(headersTreeMap, mapParameters);
    }

    @OnClick(R2.id.data_fen_xi)
    public void onViewClicked() {
        initData();
    }

    class MyWebChromClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
                depositPageLoading.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title.contains("404")) {
                webView.setVisibility(View.GONE);
            } else {
                mainTitleText.setText(title);
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }


    @Override
    public void setResponseData(ComExamineStatusResponse mComExamineStatusResponse) {
        try {
            int code = mComExamineStatusResponse.getStatus();
            String msg = mComExamineStatusResponse.getMessage();
            if (code == ResponseCode.SUCCESS_OK) {
                ComExamineStatusResponse.DataBean data = mComExamineStatusResponse.getData();
               /* {
                    "status": 1,
                        "message": "未认证",
                        "data": {
                    "examine": 0,
                            "refuse_text": ""
                }
                }*/
              /* 0是未认证
                1是认证通过
                2是认证中
                3被拒绝*/
                if (data != null) {
                    String refuse_text = data.getRefuse_text();
                    if (data.getExamine() == 0) {
                        ARouter.getInstance().build("/publishActivity/publishActivity").greenChannel().navigation(MyWebActivity.this);
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(MyWebActivity.this.getApplicationContext(), msg);
                        }
                    } else if (data.getExamine() == 1) {
                        int id = data.getId();
                        Intent intent = new Intent(MyWebActivity.this, DataAnalysisActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        //                        ARouter.getInstance().build("/dataAnalysisActivity/dataAnalysisActivity").greenChannel().navigation(MyWebActivity.this);
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(MyWebActivity.this.getApplicationContext(), msg);
                        }
                    } else if (data.getExamine() == 2) {
                        ARouter.getInstance().build("/SubmitSuccessActivity/SubmitSuccessActivity").greenChannel().navigation(MyWebActivity.this);
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(MyWebActivity.this.getApplicationContext(), msg);
                        }
                    } else if (data.getExamine() == 3) {
                        if (!Utils.isNull(msg) && !Utils.isNull(refuse_text)) {
                            ConfirmDialog confirmDialog = new ConfirmDialog(MyWebActivity.this);
                            confirmDialog.setTitleStr(msg);
                            confirmDialog.setContentStr(refuse_text);
                            confirmDialog.show();
                        }
                    } else {
                        ARouter.getInstance().build("/publishActivity/publishActivity").greenChannel().navigation(MyWebActivity.this);
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(MyWebActivity.this.getApplicationContext(), msg);
                        }
                    }

                }


            } else if (code == ResponseCode.SEESION_ERROR) {
                //SESSION_ID为空别的页面 要调起登录页面
                ARouter.getInstance().build("/login/login").greenChannel().navigation(MyWebActivity.this);
            } else {
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.showToast(MyWebActivity.this.getApplicationContext(), msg);
                }

            }
        } catch (Exception e) {
            ToastUtil.showToast(MyWebActivity.this.getApplicationContext(), "解析数据失败");
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

    @OnClick({R2.id.main_title_back})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.main_title_back) {
            goBack();

        }
    }

    public void goBack() {
        if (webView.canGoBack())
            webView.goBack();
        else
            finish();
    }
}
