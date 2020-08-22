package com.xiaoanjujia.home.composition.html.store_html;

import android.webkit.JavascriptInterface;


public abstract class WebInterFace {

    // 是在子线程中执行的
    @JavascriptInterface
    public abstract String getUserName();

    @JavascriptInterface
    public abstract String getPassWord();
    @JavascriptInterface
    public abstract String getUserToken();


}
