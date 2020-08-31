package com.xiaoanjujia.home.entities;


import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class ComcateListsResponse implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : [{"cate_name":"社区超市","cate_iimg_url":"https://a.xiaoanjujia.com/uploads/images/2020/07-21/8e04c6aacc1579e166be4728fb225b02.jpg"},{"cate_name":"搬家服务","cate_iimg_url":"https://a.xiaoanjujia.com/uploads/images/2020/07-21/8e04c6aacc1579e166be4728fb225b02.jpg"}]
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
         * cate_name : 社区超市
         * cate_iimg_url : https://a.xiaoanjujia.com/uploads/images/2020/07-21/8e04c6aacc1579e166be4728fb225b02.jpg
         */

        private String cate_name;
        private String cate_iimg_url;
        private int cate_id;

        public int getCate_id() {
            return cate_id;
        }

        public void setCate_id(int cate_id) {
            this.cate_id = cate_id;
        }

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public String getCate_iimg_url() {
            return cate_iimg_url;
        }

        public void setCate_iimg_url(String cate_iimg_url) {
            this.cate_iimg_url = cate_iimg_url;
        }
    }
}
