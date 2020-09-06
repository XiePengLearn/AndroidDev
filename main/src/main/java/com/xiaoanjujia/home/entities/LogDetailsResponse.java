package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class LogDetailsResponse  implements Serializable {


    /**
     * status : 1
     * message : ok
     * data : {"id":2,"role_id":1,"log_imgs":["https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg"],"log_text":"是的是的撒多所多多方法的方法都发的发的的地方广东佛山","abnormal_text":"","examinestatus":3,"refuse_text":"sdsd是的","create_time":"2020-08-04 16:50:12","name":"客服","week":"星期二"}
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
         * id : 2
         * role_id : 1
         * log_imgs : ["https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg"]
         * log_text : 是的是的撒多所多多方法的方法都发的发的的地方广东佛山
         * abnormal_text :
         * examinestatus : 3
         * refuse_text : sdsd是的
         * create_time : 2020-08-04 16:50:12
         * name : 客服
         * week : 星期二
         */

        private int id;
        private int role_id;
        private String log_text;
        private String abnormal_text;
        private int examinestatus;
        private String refuse_text;
        private String create_time;
        private String name;
        private String week;
        private List<String> log_imgs;

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

        public String getRefuse_text() {
            return refuse_text;
        }

        public void setRefuse_text(String refuse_text) {
            this.refuse_text = refuse_text;
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

        public List<String> getLog_imgs() {
            return log_imgs;
        }

        public void setLog_imgs(List<String> log_imgs) {
            this.log_imgs = log_imgs;
        }
    }
}
