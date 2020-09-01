package com.xiaoanjujia.home.composition.html.store_html;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.base.BaseFragment;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.widget.X5WebView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.html.activity_html.MyWebActivity;
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

/**
 * @Auther: xp
 * @Date: 2019/10
 * @Description: 快速开发Fragment
 */
public class StoreWebFragment extends BaseFragment implements StoreWebFragmentContract.View, PtrHandler, StoreWebInterface.JSStoreCallBack {
    private static final String TAG = "StoreWebFragment";
    @Inject
    StoreWebFragmentPresenter mPresenter;
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

    private String mWebUrl;


    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_html, container, false);
        unbinder = ButterKnife.bind(this, view);
        //        mWebUrl = "https://www.xiaoanjujia.com/mobile/index.php?m=user";
        mWebUrl = "https://www.xiaoanjujia.com/mobile/index.php";
        initViewMethod();
        initTitle();
        initSetting(this);
        depositPageLoading.setVisibility(View.VISIBLE);
        return view;
    }

    private void initSetting(StoreWebInterface.JSStoreCallBack jSStoreCallBack) {
        WebSettings settings = webView.getSettings();
        String userAgentString = settings.getUserAgentString();
        settings.setUserAgent(userAgentString + "xiaoan");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.addJavascriptInterface(new StoreWebInterface().setJsCallback(jSStoreCallBack), "JsToAndroidBridge");
    }

    /**
     * 初始化title
     */
    public void initTitle() {
        mainTitleBack.setVisibility(View.INVISIBLE);
        mainTitleText.setText("");
        mainTitleContainer.setVisibility(View.GONE);
    }

    @Override
    public void initEvent() {
        initView();


    }

    @Override
    public void onLazyLoad() {
        //        showJDLoadingDialog();
    }

    private void initViewMethod() {

        webView.loadUrl(mWebUrl);
        MyWebChromClient webChromClient = new MyWebChromClient();
        webView.setWebChromeClient(webChromClient);
        webView.setWebViewClient(new MyWebViewClient());

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
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PrefUtils.writeAuthenticationStatus("", BaseApplication.getInstance());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.putExtra("param", "web");
                    startActivity(intent);
                }
            });
        }

    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            Intent intent = new Intent(getActivity(), MyWebActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);

            return true;
        }
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
                //                jkxTitleCenter.setText(title);
            }
        }
    }

    public static StoreWebFragment newInstance() {
        StoreWebFragment newInstanceFragment = new StoreWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        newInstanceFragment.setArguments(bundle);
        return newInstanceFragment;
    }

    public void initView() {
        DaggerStoreWebFragmentComponent.builder()
                .appComponent(getAppComponent())
                .storeWebFragmentModule(new StoreWebFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
    }

    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(1);
        mapParameters.put("user_id", PrefUtils.readUserId(BaseApplication.getInstance()));

        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();

        mPresenter.getRequestData(headersTreeMap, mapParameters);
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
                        ARouter.getInstance().build("/publishActivity/publishActivity").greenChannel().navigation(getActivity());
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(mActivity.getApplicationContext(), msg);
                        }
                    } else if (data.getExamine() == 1) {
                        int id = data.getId();
                        Intent intent = new Intent(getActivity(), DataAnalysisActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                        //                        ARouter.getInstance().build("/dataAnalysisActivity/dataAnalysisActivity").greenChannel().navigation(MyWebActivity.this);
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(getActivity(), msg);
                        }
                    } else if (data.getExamine() == 2) {
                        ARouter.getInstance().build("/SubmitSuccessActivity/SubmitSuccessActivity").greenChannel().navigation(getActivity());
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(mActivity.getApplicationContext(), msg);
                        }
                    } else {
                        ARouter.getInstance().build("/publishActivity/publishActivity").greenChannel().navigation(getActivity());
                        if (!TextUtils.isEmpty(msg)) {
                            ToastUtil.showToast(mActivity.getApplicationContext(), msg);
                        }
                    }

                }


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
        try {
            if (webView != null) {

                ViewParent parent = webView.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(webView);
                }
                webView.removeAllViews();
                webView.destroy();
                webView = null;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            super.onDestroyView();
        }
    }


}
