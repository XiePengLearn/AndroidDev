package com.xiaoanjujia.home.tool;

import com.xiaoanjujia.common.BaseApplication;
import com.xiaoanjujia.common.util.DeviceUuidFactory;

/**
 * @Auther: xp
 * @Date: 2020/7/23 14:22
 * @Description:
 */
public class DeviceInfo {


    private String deviceModel = DeviceUtils.getAndroidModel();

    private String osName = DeviceUtils.getAndroidVer();

    private String udid = DeviceUuidFactory.getInstance(BaseApplication.getInstance()).getDeviceUuid() + "";

    private String platver = DeviceUtils.getVersionName();

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }


    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getPlatver() {
        return platver;
    }

    public void setPlatver(String platver) {
        this.platver = platver;
    }


    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceModel='" + deviceModel + '\'' +
                ", osName='" + osName + '\'' +
                ", udid='" + udid + '\'' +
                ", platver='" + platver + '\'' +
                '}';
    }
}
