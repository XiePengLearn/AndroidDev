package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @author xp
 * 创建日期： 2020/9/2$
 * 描述 用户余额$
 */
public class UserBalanceResponse implements Serializable {

    /**
     * status : 1
     * message : OK
     * data : {"user_money":"0.00"}
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
         * user_money : 0.00
         */

        private String user_money;

        public String getUser_money() {
            return user_money;
        }

        public void setUser_money(String user_money) {
            this.user_money = user_money;
        }
    }
}
