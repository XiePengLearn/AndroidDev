package com.xiaoanjujia.common;

import android.app.Application;
import android.os.Handler;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @Auther: xp
 * @Date: 2020/7/24 09:52
 * @Description:
 */
public class BaseApplication extends Application {
    private static BaseApplication mApplication;
    private static Handler mHandler;
    public static BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mHandler = new Handler();
        try {
            handleSSLHandshake();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Handler getHandler() {
        return mHandler;
    }

    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {

                }
                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {

                }
            }};
            SSLContext sc = SSLContext.getInstance("TLS");
            // trustAllCerts信任所有的证书
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception ignored) {

        }
    }
}
