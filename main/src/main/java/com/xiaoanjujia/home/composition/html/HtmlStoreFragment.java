package com.xiaoanjujia.home.composition.html;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.sxjs.jd.R;
import com.sxjs.jd.R2;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.xiaoanjujia.common.base.BaseFragment;
import com.xiaoanjujia.common.widget.X5WebView;
import com.xiaoanjujia.home.composition.html.activity_html.MeWebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther: xp
 * @Date: 2019/9/19 20:37
 * @Description:
 */
public class HtmlStoreFragment extends BaseFragment {


    public static String mUrlDataKey = "url";
    public static String mJDHeaderView = "JDHeaderView";
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
    private Bundle mArguments;
    private String mWebUrl;
    private View mView;

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_store_html, container, false);
        unbinder = ButterKnife.bind(this, mView);
        mWebUrl = "https://www.xiaoanjujia.com/mobile/";
        initViewMethod();
        initTitle();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.onResume();
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
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onLazyLoad() {
        //        mArguments = getArguments();
        //        mWebUrl = mArguments.getString(mUrlDataKey);
    }

    private void initViewMethod() {

        webView.loadUrl(mWebUrl);
        MyWebChromClient webChromClient = new MyWebChromClient();
        webView.setWebChromeClient(webChromClient);
        webView.setWebViewClient(new MyWebViewClient());

    }


    public static HtmlStoreFragment newInstance() {
        HtmlStoreFragment examMiddleFragment = new HtmlStoreFragment();
        Bundle bundle = new Bundle();
        //        bundle.putString(mUrlDataKey, url);
        examMiddleFragment.setArguments(bundle);
        return examMiddleFragment;
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            Intent intent = new Intent(getActivity(), MeWebActivity.class);
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
