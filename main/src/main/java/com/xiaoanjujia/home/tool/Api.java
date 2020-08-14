package com.xiaoanjujia.home.tool;

import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.util.AesUtil;
import com.xiaoanjujia.common.util.PrefUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import okhttp3.FormBody;

/**
 * @Auther: xp
 * @Date: 2020/7/23 14:59
 * @Description:
 */
public class Api {
    private static final String PARAMS = "user-token=%s";
    private static final String KEY = "sgg45747ss223455";

    public static void addTokenParam(StringBuilder stringBuilder) {
        String extra = String.format(PARAMS, PrefUtils.readSESSION_ID(BaseApplication.getInstance()));
        stringBuilder.append(extra);
    }

    public static String[] getSignByForm() {
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

        DeviceInfo deviceInfo = new DeviceInfo();
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
        ArrayList<String> params = new ArrayList<>();
        for (int i = 0; i < formBody.size(); i++) {
            params.add(formBody.name(i) + "=" + formBody.value(i));
        }
        if (Util.isLogin()) {
            StringBuilder extraBuilder = new StringBuilder();
            addTokenParam(extraBuilder);
            params.add(extraBuilder.toString());
        }
        Collections.sort(params);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            stringBuilder.append(params.get(i)).append("&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        try {
            ret[1] = AesUtil.Encrypt(KEY,stringBuilder.toString());
//            ret[1] = AesUtil.Encrypt(KEY,"app-type=ios&did=3140EAA6-BC15-474E-965B-874F241E42E5&model=apple&os=13.5&time=1595424249000&vesion=1.0.0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static TreeMap<String, String> getHeadersTreeMap() {
        String[] mSignByForm = Api.getSignByForm();
        DeviceInfo deviceInfo = new DeviceInfo();
        String osName = deviceInfo.getOsName();
        String versionCode = deviceInfo.getPlatver();
        String udid = deviceInfo.getUdid();
        String deviceModel = deviceInfo.getDeviceModel();
        TreeMap<String, String> mapHeaders = new TreeMap<>();
        mapHeaders.put("app-type", "android");
        mapHeaders.put("did", udid);
        mapHeaders.put("model", deviceModel);
        mapHeaders.put("os", osName);
        mapHeaders.put("sign", mSignByForm[1]);
        mapHeaders.put("time", mSignByForm[0]);
        if (Util.isLogin()) {
            mapHeaders.put("token", PrefUtils.readSESSION_ID(BaseApplication.getInstance()));
        }
        mapHeaders.put("vesion", versionCode);
        return mapHeaders;
    }


}
