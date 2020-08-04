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
     * data : {"token":"LZAn6zYfH06M+fYUeiyA8Iq2wLFTup12g3v6RyzWRAHWZvJijQTwwGl9OE6Y2rto","user_id":6,"role_id":0,"roletype":0,"rolename":""}
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
         * token : LZAn6zYfH06M+fYUeiyA8Iq2wLFTup12g3v6RyzWRAHWZvJijQTwwGl9OE6Y2rto
         * user_id : 6
         * role_id : 0
         * roletype : 0
         * rolename :
         */

        private String token;
        private int user_id;
        private int role_id;
        private int roletype;
        private String rolename;

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

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public int getRoletype() {
            return roletype;
        }

        public void setRoletype(int roletype) {
            this.roletype = roletype;
        }

        public String getRolename() {
            return rolename;
        }

        public void setRolename(String rolename) {
            this.rolename = rolename;
        }
    }
}
