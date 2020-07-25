package com.xiaoanjujia.home.entities;


import java.io.Serializable;

/**
 * @Auther: xp
 * @Date: 2019/9/8 19:15
 * @Description:
 */
public class LoginResponse implements Serializable {


    /**
     * status : 1
     * message : 登录成功
     * data : {"token":"S9IUILwR98oAnV97jcDGzlc8w7xSczV7g9mdlP2+soksQMUBYOeetH8d7qT8/YVz","user_id":35}
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
         * token : S9IUILwR98oAnV97jcDGzlc8w7xSczV7g9mdlP2+soksQMUBYOeetH8d7qT8/YVz
         * user_id : 35
         */

        private String token;
        private int user_id;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
