package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @author xp
 * 创建日期： 2020/8/19$
 * 描述 物业管理列表日志$
 */
public class PropertyManagementListLogResponse implements Serializable {

    /**
     * status : 1
     * message : ok
     * data : [{"id":24,"role_id":1,"user_id":37,"log_imgs":"","log_text":"11111","abnormal_text":"","examinestatus":0,"create_time":"2020-08-18 15:49:19","name":"客服","week":"星期二"},{"id":23,"role_id":1,"user_id":37,"log_imgs":"","log_text":"11111","abnormal_text":"","examinestatus":0,"create_time":"2020-08-18 15:49:18","name":"客服","week":"星期二"}]
     */

    private int status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 24
         * role_id : 1
         * user_id : 37
         * log_imgs :
         * log_text : 11111
         * abnormal_text :
         * examinestatus : 0
         * create_time : 2020-08-18 15:49:19
         * name : 客服
         * week : 星期二
         */

        private int id;
        private int role_id;
        private int user_id;
        private String log_imgs;
        private String log_text;
        private String abnormal_text;
        private int examinestatus;
        private String create_time;
        private String name;
        private String week;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRole_id() {
            return role_id;
        }

        public void setRole_id(int role_id) {
            this.role_id = role_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getLog_imgs() {
            return log_imgs;
        }

        public void setLog_imgs(String log_imgs) {
            this.log_imgs = log_imgs;
        }

        public String getLog_text() {
            return log_text;
        }

        public void setLog_text(String log_text) {
            this.log_text = log_text;
        }

        public String getAbnormal_text() {
            return abnormal_text;
        }

        public void setAbnormal_text(String abnormal_text) {
            this.abnormal_text = abnormal_text;
        }

        public int getExaminestatus() {
            return examinestatus;
        }

        public void setExaminestatus(int examinestatus) {
            this.examinestatus = examinestatus;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }
    }
}
