package com.xiaoanjujia.home.composition.main.unlocking;

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
import android.widget.RelativeLayout;
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
import com.xiaoanjujia.common.widget.alphaview.AlphaButton;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.html.activity_html.MyWebActivity;
import com.xiaoanjujia.home.composition.login.login.LoginActivity;
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
 * @Description: 快速开发Fragment
 */
public class UnlockingFragment extends BaseFragment implements UnlockingFragmentContract.View, PtrHandler, UnlockingWebInterface.JSUnlockingCallBack {
    @Inject
    UnlockingFragmentPresenter mPresenter;
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
    @BindView(R2.id.unlocking_independent_booking_for_visitors)
    AlphaButton unlockingIndependentBookingForVisitors;
    @BindView(R2.id.unlocking_visiting_scholar)
    AlphaButton unlockingVisitingScholar;
    @BindView(R2.id.unlocking_visitors_to_review)
    AlphaButton unlockingVisitorsToReview;
    @BindView(R2.id.unlocking_visitors_store)
    AlphaButton unlockingVisitorsStore;
    @BindView(R2.id.no_data_img)
    ImageView noDataImg;
    @BindView(R2.id.rl_fragment_no_data)
    RelativeLayout rlFragmentNoData;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;
    @BindView(R2.id.webView)
    X5WebView webView;
    private String mWebUrl;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unlocking, container, false);
        unbinder = ButterKnife.bind(this, view);
        mWebUrl = "https://www.xiaoanjujia.com/mobile/index.php?m=category&a=ceshi";
        return view;
    }

    @Override
    public void initEvent() {
        initView();
        //        initData();
        initTitle();
        initWebView();
        initSetting(this);
    }

    private void initWebView() {

        webView.loadUrl(mWebUrl);
        MyWebChromClient webChromClient = new MyWebChromClient();
        webView.setWebChromeClient(webChromClient);


    }

    private void initSetting(UnlockingWebInterface.JSUnlockingCallBack jSMeCallBack) {
        WebSettings settings = webView.getSettings();
        String userAgentString = settings.getUserAgentString();
        settings.setUserAgent(userAgentString + "xiaoan");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.addJavascriptInterface(new UnlockingWebInterface().setJsCallback(jSMeCallBack), "JsToAndroidBridge");
    }

    /**
     * 初始化title
     */
    private void initTitle() {
        mainTitleBack.setVisibility(View.INVISIBLE);
        mainTitleText.setText("开锁");
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
        findPullRefreshHeader.setPtrHandler(this);
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


    @OnClick({R2.id.unlocking_independent_booking_for_visitors, R2.id.unlocking_visiting_scholar, R2.id.unlocking_visitors_to_review, R2.id.unlocking_visitors_store})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.unlocking_independent_booking_for_visitors) {

            ARouter.getInstance().build("/VisitorActivity/VisitorActivity").greenChannel().navigation(mContext);
        } else if (id == R.id.unlocking_visiting_scholar) {
            ARouter.getInstance().build("/VisitorInvitationActivity/VisitorInvitationActivity").greenChannel().navigation(mContext);
        } else if (id == R.id.unlocking_visitors_to_review) {
            ARouter.getInstance().build("/VisitorAuditActivity/VisitorAuditActivity").greenChannel().navigation(mContext);
        } else if (id == R.id.unlocking_visitors_store) {
            ARouter.getInstance().build("/publishActivity/publishActivity").greenChannel().navigation(mContext);
        }
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
