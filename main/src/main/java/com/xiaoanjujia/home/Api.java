package com.xiaoanjujia.home;

import android.content.Context;

import com.xiaoanjujia.common.util.PrefUtils;
import com.xiaoanjujia.home.entities.DeviceInfo;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.FormBody;

/**
 * @Auther: xp
 * @Date: 2020/7/23 14:59
 * @Description:
 */
public class Api {
    private static final String PARAMS = "user-token=%s";

    public static void addTokenParam(StringBuilder stringBuilder,Context mContext) {
        String extra = String.format(PARAMS, PrefUtils.readSESSION_ID(mContext));
        stringBuilder.append(extra);
    }

    private static String[] getSignByForm(String token, Context mContext) {
        /**
         * 字段 参考值 备注
         * sign sdjnjndkjmdfskljmnmj 唯一值
         * vesion 1 版本号
         * app-type ios app的类型
         * did 12233 设备号
         * os 2.3 设备的操作系统
         * model apple app机型
         * user-token sdj44w343nweicjwrerkc 登录后需要携带的参数
         * time 12345455 当前时间
         */
        String[] ret = new String[2];
        ret[0] = System.currentTimeMillis() + "";

        DeviceInfo deviceInfo = new DeviceInfo(mContext);
        String osName = deviceInfo.getOsName();
        String versionCode = deviceInfo.getPlatver();
        String udid = deviceInfo.getUdid();
        String deviceModel = deviceInfo.getDeviceModel();
        FormBody formBody = new FormBody.Builder()
                .add("vesion", versionCode)
                .add("app-type", "android")
                .add("did", udid)
                .add("os", osName)
                .add("model", deviceModel)
                .add("time", ret[0])
                .build();


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(token);
        stringBuilder.append(ret[0]);
        ArrayList<String> params = new ArrayList<>();
        for (int i = 0; i < formBody.size(); i++) {
            params.add(formBody.name(i) + "=" + formBody.value(i));
        }
        StringBuilder extraBuilder = new StringBuilder();
        if (Util.isLogin(mContext)) {
            addTokenParam(extraBuilder,mContext);
        }

        for (String s : extraBuilder.toString().split("&")) {
            params.add(s);
        }
        Collections.sort(params);
        for (int i = 0; i < params.size(); i++) {
            stringBuilder.append(params.get(i)).append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        //        stringBuilder.append(Utils.md5Encode(""));
        //        ret[1] = Utils.md5Encode(stringBuilder.toString());
        return ret;
    }
}
