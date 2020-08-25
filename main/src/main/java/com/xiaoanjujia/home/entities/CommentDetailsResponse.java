package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class CommentDetailsResponse {


    /**
     * status : 1
     * message : OK
     * data : {"all_count":4,"good_count":1,"difference_count":3,"all_comments":[{"id":12,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:55"},{"id":11,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:54"},{"id":10,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:53"},{"id":9,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":3,"create_time":"2020-07-30 18:46:42"}],"good_comments":[{"id":9,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":3,"create_time":"2020-07-30 18:46:42"}],"difference_comments":[{"id":12,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:55"},{"id":11,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:54"},{"id":10,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:53"}]}
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
         * all_count : 4
         * good_count : 1
         * difference_count : 3
         * all_comments : [{"id":12,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:55"},{"id":11,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:54"},{"id":10,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:53"},{"id":9,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":3,"create_time":"2020-07-30 18:46:42"}]
         * good_comments : [{"id":9,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":3,"create_time":"2020-07-30 18:46:42"}]
         * difference_comments : [{"id":12,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:55"},{"id":11,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:54"},{"id":10,"user_id":35,"commun_id":3,"order_id":5,"comment_img":"https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png","content":"啊啊啊啊","star_rating":2,"create_time":"2020-07-30 18:46:53"}]
         */

        private int all_count;
        private int good_count;
        private int difference_count;
        private List<DataBean.AllCommentsBean> all_comments;
        private List<DataBean.GoodCommentsBean> good_comments;
        private List<DataBean.DifferenceCommentsBean> difference_comments;

        public int getAll_count() {
            return all_count;
        }

        public void setAll_count(int all_count) {
            this.all_count = all_count;
        }

        public int getGood_count() {
            return good_count;
        }

        public void setGood_count(int good_count) {
            this.good_count = good_count;
        }

        public int getDifference_count() {
            return difference_count;
        }

        public void setDifference_count(int difference_count) {
            this.difference_count = difference_count;
        }

        public List<DataBean.AllCommentsBean> getAll_comments() {
            return all_comments;
        }

        public void setAll_comments(List<DataBean.AllCommentsBean> all_comments) {
            this.all_comments = all_comments;
        }

        public List<DataBean.GoodCommentsBean> getGood_comments() {
            return good_comments;
        }

        public void setGood_comments(List<DataBean.GoodCommentsBean> good_comments) {
            this.good_comments = good_comments;
        }

        public List<DataBean.DifferenceCommentsBean> getDifference_comments() {
            return difference_comments;
        }

        public void setDifference_comments(List<DataBean.DifferenceCommentsBean> difference_comments) {
            this.difference_comments = difference_comments;
        }

        public static class AllCommentsBean {
            /**
             * id : 12
             * user_id : 35
             * commun_id : 3
             * order_id : 5
             * comment_img : https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png
             * content : 啊啊啊啊
             * star_rating : 2
             * create_time : 2020-07-30 18:46:55
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

        public static class GoodCommentsBean {
            /**
             * id : 9
             * user_id : 35
             * commun_id : 3
             * order_id : 5
             * comment_img : https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png
             * content : 啊啊啊啊
             * star_rating : 3
             * create_time : 2020-07-30 18:46:42
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

        public static class DifferenceCommentsBean {
            /**
             * id : 12
             * user_id : 35
             * commun_id : 3
             * order_id : 5
             * comment_img : https://a.xiaoanjujia.com/uploads/commentimg/20200814184042.png
             * content : 啊啊啊啊
             * star_rating : 2
             * create_time : 2020-07-30 18:46:55
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
}
