package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @Auther: xp
 * @Date: 2019/9/18 09:15
 * @Description:
 */
public class AppUpdateResponse  implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : {"appstatus":1,"appurl":"https://www.xiaoanjujia.com/app_sdk/app_demo.php"}
     */

    private int status;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appstatus : 1
         * appurl : https://www.xiaoanjujia.com/app_sdk/app_demo.php
         */

        private int appstatus;
        private int flag;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        private String appurl;

        public int getAppstatus() {
            return appstatus;
        }

        public void setAppstatus(int appstatus) {
            this.appstatus = appstatus;
        }

        public String getAppurl() {
            return appurl;
        }

        public void setAppurl(String appurl) {
            this.appurl = appurl;
        }
    }
}
