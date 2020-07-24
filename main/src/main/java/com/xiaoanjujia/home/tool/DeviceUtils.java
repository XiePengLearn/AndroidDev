package com.xiaoanjujia.home.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.xiaoanjujia.common.BaseApplication;

/**
 * @Auther: xp
 * @Date: 2020/7/23 14:26
 * @Description:
 */
public class DeviceUtils {
    public static String getAndroidVer() {
        return android.os.Build.VERSION.RELEASE;
    }


    public static String getAndroidModel() {
        return android.os.Build.BRAND + "_" + android.os.Build.MODEL;
    }

    /**
     * 得到app的版本号
     *
     * @param
     * @return
     */
    public static String getVersionName( ) {
        PackageInfo packInfo = null;
        try {
            packInfo = BaseApplication.getInstance().getPackageManager().getPackageInfo( BaseApplication.getInstance().getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packInfo == null ? "1.0.0" : packInfo.versionName;
    }

    /**
     * 获取系统语言
     *
     * @return
     */
    public static String getSystemLanguage(Context context) {
        String language = null;
        try {
            language = context.getResources().getConfiguration().locale.getLanguage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return language;
    }

//    public static int getScreenWidth(Context mContext) {
//        final Resources resources = mContext.getApplicationContext().getResources();
//        final DisplayMetrics dm = resources.getDisplayMetrics();
//        return dm.widthPixels;
//    }
//
//    public static int getScreenHeight(Context mContext) {
//        final Resources resources = mContext.getApplicationContext().getResources();
//        final DisplayMetrics dm = resources.getDisplayMetrics();
//        return dm.heightPixels;
//    }

    public static String getMacAddress(Context mContext) {
        String macAddress = null;
        WifiManager wifiManager = (WifiManager) mContext.getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (null == wifiManager ? null : wifiManager.getConnectionInfo());
        if (!wifiManager.isWifiEnabled()) {
            //必须先打开，才能获取到MAC地址
            wifiManager.setWifiEnabled(true);
            wifiManager.setWifiEnabled(false);

        }
        if (null != info) {
            macAddress = info.getMacAddress();
        }
        return  macAddress;
    }

    public static String getAppName(String pkgName,Context mContext) {
        PackageManager pm = mContext.getApplicationContext().getPackageManager();
        String appName;
        try {
            appName = pm.getApplicationLabel(pm.getApplicationInfo(pkgName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            appName = "";
        }
        return appName;
    }

}
