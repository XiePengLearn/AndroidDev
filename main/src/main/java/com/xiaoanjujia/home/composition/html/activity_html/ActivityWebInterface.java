package com.xiaoanjujia.home.composition.html.activity_html;

import android.webkit.JavascriptInterface;

import com.xiaoanjujia.common.util.NoDoubleClickUtils;
import com.xiaoanjujia.home.composition.html.store_html.WebInterFace;

/**
 * JavascriptInterface（首页非弹窗活动webView调用）
 */
public class ActivityWebInterface extends WebInterFace {

    private JSActivityCallBack jsCallBack;

    public ActivityWebInterface setJsCallback(JSActivityCallBack jSStoreCallBack) {
        this.jsCallBack = jSStoreCallBack;
        return this;
    }

    // 是在自线程中执行的
    @JavascriptInterface
    @Override
    public String getUserName() {
        if (jsCallBack != null) {
            return jsCallBack.jsGetUserName();
        }
        return "";
    }

    @JavascriptInterface
    @Override
    public String getPassWord() {
        if (jsCallBack != null) {
            return jsCallBack.jsGetPassWord();
        }
        return "";
    }

    @JavascriptInterface
    @Override
    public String getUserToken() {
        if (jsCallBack != null) {
            return jsCallBack.jsGetUserToken();
        }
        return "";
    }

    @JavascriptInterface
    @Override
    public void getMerchantsCertification() {
        if (jsCallBack != null) {
            if (!NoDoubleClickUtils.isDoubleClick()) {
                jsCallBack.jsMerchantsCertification();
            }
        }
    }

    @JavascriptInterface
    @Override
    public void getLogOut() {
        if (jsCallBack != null) {
            jsCallBack.jsGetLogOut();
        }
    }


    @JavascriptInterface
    @Override
    public String getUserId() {
        if (jsCallBack != null) {
            return jsCallBack.jsGetUserId();
        }
        return "";
    }

    public interface JSActivityCallBack {
        String jsGetUserName();

        String jsGetPassWord();

        String jsGetUserToken();

        String jsGetUserId();

        void jsMerchantsCertification();

        void jsGetLogOut();
    }

}
