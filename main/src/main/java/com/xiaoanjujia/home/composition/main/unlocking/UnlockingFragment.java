package com.xiaoanjujia.home.composition.main.unlocking;

import android.content.Intent;
import android.net.Uri;
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
import com.xiaoanjujia.common.util.NoDoubleClickUtils;
import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.common.util.ResponseCode;
import com.xiaoanjujia.common.util.ToastUtil;
import com.xiaoanjujia.common.widget.LoadingView;
import com.xiaoanjujia.common.widget.X5WebView;
import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;
import com.xiaoanjujia.common.widget.dialog.NormalDialog;
import com.xiaoanjujia.common.widget.headerview.JDHeaderView;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrFrameLayout;
import com.xiaoanjujia.common.widget.pulltorefresh.PtrHandler;
import com.xiaoanjujia.home.MainDataManager;
import com.xiaoanjujia.home.composition.html.activity_html.MyWebActivity;
import com.xiaoanjujia.home.composition.login.login.LoginActivity;
import com.xiaoanjujia.home.composition.unlocking.add_personal_information.AddPersonalInformationActivity;
import com.xiaoanjujia.home.composition.unlocking.face.FaceActivity;
import com.xiaoanjujia.home.composition.unlocking.house_manager.HouseManagerActivity;
import com.xiaoanjujia.home.composition.unlocking.qr_code.VisitorActivity;
import com.xiaoanjujia.home.composition.unlocking.visitor_invitation.VisitorInvitationActivity;
import com.xiaoanjujia.home.entities.VisitorPersonInfoResponse;
import com.xiaoanjujia.home.tool.Api;

import java.util.HashMap;
import java.util.List;
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
    @BindView(R2.id.unlocking_add_house_tv)
    TextView unlockingAddHouseTv;
    @BindView(R2.id.main_title_right)
    ImageView mainTitleRight;
    @BindView(R2.id.unlocking_add_house_iv)
    ImageView unlockingAddHouseIv;
    @BindView(R2.id.main_title_container)
    LinearLayout mainTitleContainer;
    @BindView(R2.id.unlocking_house_title)
    TextView unlockingHouseTitle;
    @BindView(R2.id.unlocking_add_house_ll)
    LinearLayout unlockingAddHouseLl;
    @BindView(R2.id.unlocking_one_line_1)
    LinearLayout unlockingOneLine1;
    @BindView(R2.id.unlocking_one_line_2)
    LinearLayout unlockingOneLine2;
    @BindView(R2.id.unlocking_one_line_3)
    LinearLayout unlockingOneLine3;
    @BindView(R2.id.unlocking_two_line_1)
    LinearLayout unlockingTwoLine1;
    @BindView(R2.id.unlocking_two_line_2)
    LinearLayout unlockingTwoLine2;
    @BindView(R2.id.unlocking_two_line_3)
    LinearLayout unlockingTwoLine3;
    @BindView(R2.id.unlocking_three_line_1)
    LinearLayout unlockingThreeLine1;
    @BindView(R2.id.unlocking_three_line_2)
    LinearLayout unlockingThreeLine2;
    @BindView(R2.id.unlocking_three_line_3)
    LinearLayout unlockingThreeLine3;
    @BindView(R2.id.unlocking_four_line_1)
    LinearLayout unlockingFourLine1;
    @BindView(R2.id.unlocking_four_line_2)
    LinearLayout unlockingFourLine2;
    @BindView(R2.id.unlocking_four_line_3)
    LinearLayout unlockingFourLine3;
    @BindView(R2.id.progressBar)
    ProgressBar progressBar;
    @BindView(R2.id.webView)
    X5WebView webView;
    @BindView(R2.id.betslip_order_loading)
    LoadingView betslipOrderLoading;
    @BindView(R2.id.deposit_page_loading)
    LinearLayout depositPageLoading;
    @BindView(R2.id.find_pull_refresh_header)
    JDHeaderView findPullRefreshHeader;
    private String mWebUrl;
    private NormalDialog mNormalDialog;
    private String personName;
    private String personId;
    private boolean isHaveHouse;

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
        initData();
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

    private void normalDialog() {
        if (mNormalDialog == null) {
            mNormalDialog = new NormalDialog(getActivity(), onLoadBookCodeDialogClickListener);
            mNormalDialog.setDialogContent(String.format("确定拨打物业电话吗?%s", "15610267550"));
            mNormalDialog.setFirstAlTvStr("取消");
            mNormalDialog.setSecondAlTvStr("确定");
            mNormalDialog.setDialogTitle("提示!");
        }
        mNormalDialog.show();
    }

    private View.OnClickListener onLoadBookCodeDialogClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == R.id.dialog_normal_first_btn_tv) {
                mNormalDialog.dismiss();
            } else if (id == R.id.dialog_normal_second_btn_tv) {
                String shop_phone = "15601267550";
                if (!Utils.isNull(shop_phone)) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    Uri data = Uri.parse("tel:" + shop_phone);
                    intent.setData(data);
                    startActivity(intent);
                }
            }
            mNormalDialog.dismiss();
        }
    };

    public static UnlockingFragment newInstance() {
        UnlockingFragment newInstanceFragment = new UnlockingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "key");
        newInstanceFragment.setArguments(bundle);
        return newInstanceFragment;
    }

    private int mRoleType;

    //roletype:---0是普通用户---1是物业主管----2是物业人员
    public void initView() {

        String mSession_id = PrefUtils.readSESSION_ID(mContext.getApplicationContext());

        DaggerUnlockingFragmentComponent.builder()
                .appComponent(getAppComponent())
                .unlockingFragmentModule(new UnlockingFragmentModule(this, MainDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        findPullRefreshHeader.setPtrHandler(this);

        mRoleType = PrefUtils.readRoleType(BaseApplication.getInstance());
        if (mRoleType == 1) {
            //1是物业主管
            unlockingOneLine3.setVisibility(View.VISIBLE);
        } else if (mRoleType == 2) {
            //2是物业人员
            unlockingOneLine3.setVisibility(View.VISIBLE);
        } else {
            //普通用户
            unlockingOneLine3.setVisibility(View.INVISIBLE);
        }
    }

    public void initData() {
        Map<String, Object> mapParameters = new HashMap<>(2);
        mapParameters.put("paramName", "phoneNo");
        mapParameters.put("paramValue", PrefUtils.readPhone(BaseApplication.getInstance()));
        TreeMap<String, String> headersTreeMap = Api.getHeadersTreeMap();
        mPresenter.getRequestData(headersTreeMap, mapParameters);
    }


    @Override
    public void setResponseData(VisitorPersonInfoResponse mVisitorPersonInfoResponse) {
        try {
            String code = mVisitorPersonInfoResponse.getStatus();
            String msg = mVisitorPersonInfoResponse.getMessage();
            if (code.equals(ResponseCode.SUCCESS_OK_STRING)) {
                List<VisitorPersonInfoResponse.DataBean> data = mVisitorPersonInfoResponse.getData();
                if (data != null && data.size() > 0) {
                    VisitorPersonInfoResponse.DataBean dataBean = data.get(0);
                    if (dataBean != null) {
                        personName = dataBean.getPersonName();
                        personId = dataBean.getPersonId();
                        String orgPathName = dataBean.getOrgPathName();
                        if (!Utils.isNull(personName)) {
                            unlockingHouseTitle.setText(personName);
                            PrefUtils.writePersonInfo("true",BaseApplication.getInstance());
                        } else {
                            unlockingHouseTitle.setText("请添加个人信息，绑定房屋");
                            unlockingAddHouseIv.setVisibility(View.GONE);
                            PrefUtils.writePersonInfo("",BaseApplication.getInstance());
                        }
                        if (!Utils.isNull(orgPathName)) {
                            unlockingAddHouseTv.setText(orgPathName);
                            PrefUtils.writePersonInfo("true",BaseApplication.getInstance());
                        } else {
                            unlockingAddHouseTv.setText("暂无房屋信息");
                            PrefUtils.writePersonInfo("",BaseApplication.getInstance());
                            //                            if (!Utils.isNull(personName)) {
                            //                                unlockingAddHouseTv.setText("暂无房屋信息");
                            //                            } else {
                            //                                unlockingAddHouseTv.setText("暂无房屋信息");
                            //                                unlockingAddHouseIv.setVisibility(View.GONE);
                            //                            }
                        }
                    }
                } else {
                    unlockingHouseTitle.setText("请添加个人信息，绑定房屋");
                    unlockingAddHouseTv.setText("暂无房屋信息");
                    unlockingAddHouseIv.setVisibility(View.GONE);
                    PrefUtils.writePersonInfo("",BaseApplication.getInstance());
                }


            } else if (code.equals(ResponseCode.SEESION_ERROR_STRING)) {
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


    //    @OnClick({R2.id.unlocking_independent_booking_for_visitors, R2.id.unlocking_visiting_scholar, R2.id.unlocking_visitors_to_review, R2.id.unlocking_visitors_store})
    //    public void onViewClicked(View view) {
    //        int id = view.getId();
    //        if (id == R.id.unlocking_independent_booking_for_visitors) {
    //
    //            ARouter.getInstance().build("/PermitActivity/PermitActivity").greenChannel().navigation(mContext);
    //        } else if (id == R.id.unlocking_visiting_scholar) {
    //            ARouter.getInstance().build("/FaceActivity/FaceActivity").greenChannel().navigation(mContext);
    //        } else if (id == R.id.unlocking_visitors_to_review) {
    //            ARouter.getInstance().build("/SelectHousingActivity/SelectHousingActivity").greenChannel().navigation(mContext);
    //        } else if (id == R.id.unlocking_visitors_store) {
    //            ARouter.getInstance().build("/publishActivity/publishActivity").greenChannel().navigation(mContext);
    //        }
    //    }
    @OnClick({R2.id.main_title_back, R2.id.unlocking_add_house_ll, R2.id.unlocking_one_line_1,
            R2.id.unlocking_one_line_2, R2.id.unlocking_one_line_3, R2.id.unlocking_two_line_1,
            R2.id.unlocking_two_line_2, R2.id.unlocking_two_line_3, R2.id.unlocking_three_line_1,
            R2.id.unlocking_three_line_2, R2.id.unlocking_three_line_3, R2.id.unlocking_four_line_1,
            R2.id.unlocking_four_line_2, R2.id.unlocking_four_line_3})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.main_title_back) {

        } else if (id == R.id.unlocking_add_house_ll) {

        } else if (id == R.id.unlocking_one_line_1) {
            //            ARouter.getInstance().build("/visitorActivity/visitorActivity").greenChannel().navigation(mContext);
            String mPersonInfo = PrefUtils.readPersonInfo(BaseApplication.getInstance());
            if(Utils.isNull(mPersonInfo)){
                ToastUtil.showToast(BaseApplication.getInstance(),"请先添加个人信息");
                return;
            }
            if (!NoDoubleClickUtils.isDoubleClick()) {
                Intent intent = new Intent(mContext, VisitorActivity.class);
                if (!Utils.isNull(personName)) {
                    intent.putExtra("houseName", personName);
                }
                startActivity(intent);
            }
        } else if (id == R.id.unlocking_one_line_2) {
            //房屋管理
            String mPersonInfo = PrefUtils.readPersonInfo(BaseApplication.getInstance());
            if(Utils.isNull(mPersonInfo)){
                ToastUtil.showToast(BaseApplication.getInstance(),"请先添加个人信息");
                return;
            }
            if (!NoDoubleClickUtils.isDoubleClick()) {
                Intent intent = new Intent(mContext, HouseManagerActivity.class);
                if (!Utils.isNull(personId)) {
                    intent.putExtra("personId", personId);
                }
                startActivity(intent);
            }
        } else if (id == R.id.unlocking_one_line_3) {
            //物业
            if (mRoleType == 1) {
                //1是物业主管
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    ARouter.getInstance().build("/supervisorActivity/supervisorActivity").greenChannel().navigation(mContext);
                }
            } else if (mRoleType == 2) {
                //2是物业人员
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    ARouter.getInstance().build("/staffActivity/staffActivity").greenChannel().navigation(mContext);
                }
            } else {
                //普通用户
                unlockingOneLine3.setVisibility(View.INVISIBLE);
            }
        } else if (id == R.id.unlocking_two_line_1) {
            //添加个人信息
            if (!NoDoubleClickUtils.isDoubleClick()) {
                Intent intent = new Intent(mContext, AddPersonalInformationActivity.class);
                if (!Utils.isNull(personId)) {
                    intent.putExtra("personId", personId);
                }
                startActivity(intent);
            }
        } else if (id == R.id.unlocking_two_line_2) {
            //人脸识别
            String mPersonInfo = PrefUtils.readPersonInfo(BaseApplication.getInstance());
            if(Utils.isNull(mPersonInfo)){
                ToastUtil.showToast(BaseApplication.getInstance(),"请先添加个人信息");
                return;
            }
            if (!NoDoubleClickUtils.isDoubleClick()) {
                Intent intent = new Intent(mContext, FaceActivity.class);
                if (!Utils.isNull(personId)) {
                    intent.putExtra("personId", personId);
                }
                startActivity(intent);
            }
        } else if (id == R.id.unlocking_two_line_3) {
            //访客预约
            String mPersonInfo = PrefUtils.readPersonInfo(BaseApplication.getInstance());
            if(Utils.isNull(mPersonInfo)){
                ToastUtil.showToast(BaseApplication.getInstance(),"请先添加个人信息");
                return;
            }
            if (!NoDoubleClickUtils.isDoubleClick()) {
                Intent intent = new Intent(mContext, VisitorInvitationActivity.class);
                if (!Utils.isNull(personId)) {
                    intent.putExtra("personId", personId);
                    intent.putExtra("personName", personName);
                }
                startActivity(intent);
            }

            //            ARouter.getInstance().build("/visitorInvitationActivity/visitorInvitationActivity").greenChannel().navigation(mContext);
        } else if (id == R.id.unlocking_three_line_1) {
            normalDialog();
        } else if (id == R.id.unlocking_three_line_2) {
            normalDialog();
        } else if (id == R.id.unlocking_three_line_3) {
            normalDialog();
        } else if (id == R.id.unlocking_four_line_1) {
            normalDialog();
        } else if (id == R.id.unlocking_four_line_2) {

        } else if (id == R.id.unlocking_four_line_3) {

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
