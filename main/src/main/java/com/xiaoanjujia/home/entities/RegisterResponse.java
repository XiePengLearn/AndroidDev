package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @Auther: xp
 * @Date: 2019/9/14 23:31
 * @Description:
 */
public class RegisterResponse implements Serializable {


    /**
     * status : 1
     * message : ok
     * data : {"password":"15da5b87fbda7ab1a95e471a1247abce","user_name":"18635805566"}
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
         * password : 15da5b87fbda7ab1a95e471a1247abce
         * user_name : 18635805566
         */

        private String password;
        private String user_name;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
