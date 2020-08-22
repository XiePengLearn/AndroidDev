package com.xiaoanjujia.home.entities;


import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class CommunityListResponse implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : [{"id":5,"community_id":3,"single_money":"0.25","category_id":1,"title":"啊啊啊1111骚点的","show_img":"https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","release_time":"2020-07-24 14:32:58","title_text":"啊啊啊是的撒多你哈呀多多","count":4}]
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
         * id : 5
         * community_id : 3
         * single_money : 0.25
         * category_id : 1
         * title : 啊啊啊1111骚点的
         * show_img : https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg
         * release_time : 2020-07-24 14:32:58
         * title_text : 啊啊啊是的撒多你哈呀多多
         * count : 4
         */

        private int id;
        private int community_id;
        private String single_money;
        private int category_id;
        private String title;
        private String show_img;
        private String release_time;
        private String title_text;
        private int count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public String getSingle_money() {
            return single_money;
        }

        public void setSingle_money(String single_money) {
            this.single_money = single_money;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShow_img() {
            return show_img;
        }

        public void setShow_img(String show_img) {
            this.show_img = show_img;
        }

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public String getTitle_text() {
            return title_text;
        }

        public void setTitle_text(String title_text) {
            this.title_text = title_text;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
