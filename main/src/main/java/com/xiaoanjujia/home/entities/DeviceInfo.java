package com.xiaoanjujia.home.entities;

import android.content.Context;

import com.xiaoanjujia.common.util.DeviceUuidFactory;

/**
 * @Auther: xp
 * @Date: 2020/7/23 14:22
 * @Description:
 */
public class DeviceInfo {
    private Context mContext;

    public DeviceInfo(Context mContext) {
        this.mContext = mContext;
    }

    private String deviceModel = DeviceUtils.getAndroidModel();

    private Integer deviceHeight = DeviceUtils.getScreenHeight(mContext);

    private Integer deviceWidth = DeviceUtils.getScreenWidth(mContext);

    private String osName = DeviceUtils.getAndroidVer();

    //    private String udid = DeviceIdUtil.getDeviceId(MyApp.getMyApplication());
    private String udid = DeviceUuidFactory.getInstance(mContext).getDeviceUuid() + "";

    private String platver = DeviceUtils.getVersionName(mContext);

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public Integer getDeviceHeight() {
        return deviceHeight;
    }

    public void setDeviceHeight(Integer deviceHeight) {
        this.deviceHeight = deviceHeight;
    }

    public Integer getDeviceWidth() {
        return deviceWidth;
    }

    public void setDeviceWidth(Integer deviceWidth) {
        this.deviceWidth = deviceWidth;
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
                ", deviceHeight=" + deviceHeight +
                ", deviceWidth=" + deviceWidth +
                ", osName='" + osName + '\'' +
                ", udid='" + udid + '\'' +
                ", platver='" + platver + '\'' +
                '}';
    }
}
