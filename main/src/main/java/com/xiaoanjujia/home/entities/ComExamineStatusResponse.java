package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @Auther: xp
 * @Date: 2019/9/15 10:34
 * @Description:
 */
public class ComExamineStatusResponse implements Serializable {


    /**
     * status : 1
     * message : 未认证
     * data : {"examine":0,"refuse_text":""}
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
         * examine : 0
         * refuse_text :
         */

        private int examine;
        private String refuse_text;

        public int getExamine() {
            return examine;
        }

        public void setExamine(int examine) {
            this.examine = examine;
        }

        public String getRefuse_text() {
            return refuse_text;
        }

        public void setRefuse_text(String refuse_text) {
            this.refuse_text = refuse_text;
        }
    }
}
