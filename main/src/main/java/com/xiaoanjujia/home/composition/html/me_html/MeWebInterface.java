package com.xiaoanjujia.home.composition.html.me_html;

import com.xiaoanjujia.home.composition.html.store_html.WebInterFace;

/**
 * JavascriptInterface（首页非弹窗活动webView调用）
 */
public class MeWebInterface extends WebInterFace {

    private JSMeCallBack jsCallBack;

    public MeWebInterface setJsCallback(JSMeCallBack jSStoreCallBack) {
        this.jsCallBack = jSStoreCallBack;
        return this;
    }

    // 是在自线程中执行的

    @Override
    public String getUserName() {
        if (jsCallBack != null) {
            return jsCallBack.jsGetUserName();
        }
        return "";
    }

    @Override
    public String getPassWord() {
        if (jsCallBack != null) {
            return jsCallBack.jsGetPassWord();
        }
        return "";
    }

    @Override
    public String getUserToken() {
        if (jsCallBack != null) {
            return jsCallBack.jsGetUserToken();
        }
        return "";
    }


    public interface JSMeCallBack {
        String jsGetUserName();

        String jsGetPassWord();

        String jsGetUserToken();
    }

}
