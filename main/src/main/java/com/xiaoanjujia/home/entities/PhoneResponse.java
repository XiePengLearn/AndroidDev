package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @author xp
 * 创建日期： 2020/9/6$
 * 描述 开锁页面人员信息$
 */
public class PhoneResponse implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : {"id":1,"phone":"18635805566"}
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
         * id : 1
         * phone : 18635805566
         */

        private int id;
        private String phone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
