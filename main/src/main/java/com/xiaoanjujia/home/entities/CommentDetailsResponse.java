package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @Auther: xp
 * @Description:
 */
public class CommentDetailsResponse  implements Serializable {

    /**
     * status : 1
     * message : OK
     * data : {"all_count":6,"good_count":1,"difference_count":5}
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
         * all_count : 6
         * good_count : 1
         * difference_count : 5
         */

        private int all_count;
        private int good_count;
        private int difference_count;
        private int liksestatus; //liksestatus:1已点,0未点

        public int getLiksestatus() {
            return liksestatus;
        }

        public void setLiksestatus(int liksestatus) {
            this.liksestatus = liksestatus;
        }

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
    }
}
