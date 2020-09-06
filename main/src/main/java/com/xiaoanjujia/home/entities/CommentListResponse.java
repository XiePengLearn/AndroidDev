package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class CommentListResponse  implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : [{"id":14,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-08-25 22:30:28"},{"id":13,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-08-25 22:30:25"},{"id":12,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:55"},{"id":11,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:54"},{"id":10,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:53"},{"id":9,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":3,"create_time":"2020-07-30 18:46:42"}]
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
         * id : 14
         * user_id : 35
         * commun_id : 3
         * order_id : 5
         * comment_img : https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png
         * content : 啊啊啊啊
         * star_rating : 2
         * create_time : 2020-08-25 22:30:28
         */

        private int id;
        private int user_id;
        private int commun_id;
        private int order_id;
        private String comment_img;
        private String content;
        private int star_rating;
        private String create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCommun_id() {
            return commun_id;
        }

        public void setCommun_id(int commun_id) {
            this.commun_id = commun_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getComment_img() {
            return comment_img;
        }

        public void setComment_img(String comment_img) {
            this.comment_img = comment_img;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStar_rating() {
            return star_rating;
        }

        public void setStar_rating(int star_rating) {
            this.star_rating = star_rating;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
