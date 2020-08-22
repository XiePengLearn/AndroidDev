package com.xiaoanjujia.home.entities;


import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class StoreHotMoreResponse implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : [{"id":9,"hotspot_text":"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试","createTime":"2020-08-14"},{"id":8,"hotspot_text":"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试","createTime":"2020-08-14"},{"id":7,"hotspot_text":"测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试","createTime":"2020-08-14"}]
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
         * id : 9
         * hotspot_text : 测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试
         * createTime : 2020-08-14
         */

        private int id;
        private String hotspot_text;
        private String createTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHotspot_text() {
            return hotspot_text;
        }

        public void setHotspot_text(String hotspot_text) {
            this.hotspot_text = hotspot_text;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
