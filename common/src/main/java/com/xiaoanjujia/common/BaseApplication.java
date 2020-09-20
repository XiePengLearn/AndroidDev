package com.xiaoanjujia.common;

import android.app.Application;
import android.os.Handler;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

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
    public final static String WEICHAT_APP_ID = "wxc57aed6aa217aefd";
    public final static String WEICHAT_APP_SECRET = "3f6f4aebe151c09f7ddbf3afb60f5630";

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

        UMConfigure.init(this, "5f60803fa4ae0a7f7d05d1a0"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin(WEICHAT_APP_ID, WEICHAT_APP_SECRET);
        //豆瓣RENREN平台目前只能在服务器端配置
        //        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        //        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        //        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        //        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        //        PlatformConfig.setAlipay("2015111700822536");
        //        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        //        PlatformConfig.setPinterest("1439206");
        //        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        //        PlatformConfig.setDing("dingoalmlnohc0wggfedpk");
        //        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        //        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");
        //        PlatformConfig.setYnote("9c82bf470cba7bd2f1819b0ee26f86c6ce670e9b");
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
