package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/8/29 21:01
 * @Description:
 */
public class DataAnalysisResponse implements Serializable {

    /**
     * status : 1
     * message : OK
     * data : {"addfans":0,"count_fans":4,"day_visit":0,"day_comment":0,"day_like":0,"day_contact":0,"datetime":"2020-07-29","day_new_visit_num":0,"day_new_visit_bai":"0%","day_old_visit_num":0,"day_old_visit_bai":"0%","yesterday_visit":0,"yesterday_browse":0,"statistics":[{"createsTime":"00","count":0},{"createsTime":"01","count":0},{"createsTime":"02","count":0},{"createsTime":"03","count":0},{"createsTime":"04","count":0},{"createsTime":"05","count":0},{"createsTime":"06","count":0},{"createsTime":"07","count":0},{"createsTime":"08","count":0},{"createsTime":"09","count":0},{"createsTime":"10","count":0},{"createsTime":"11","count":0},{"createsTime":"12","count":0},{"createsTime":"13","count":0},{"createsTime":"14","count":0},{"createsTime":"15","count":0},{"createsTime":"16","count":0},{"createsTime":"17","count":0},{"createsTime":"18","count":0},{"createsTime":"19","count":0},{"createsTime":"20","count":0},{"createsTime":"21","count":0},{"createsTime":"22","count":0},{"createsTime":"23","count":0}]}
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
         * addfans : 0
         * count_fans : 4
         * day_visit : 0
         * day_comment : 0
         * day_like : 0
         * day_contact : 0
         * datetime : 2020-07-29
         * day_new_visit_num : 0
         * day_new_visit_bai : 0%
         * day_old_visit_num : 0
         * day_old_visit_bai : 0%
         * yesterday_visit : 0
         * yesterday_browse : 0
         * statistics : [{"createsTime":"00","count":0},{"createsTime":"01","count":0},{"createsTime":"02","count":0},{"createsTime":"03","count":0},{"createsTime":"04","count":0},{"createsTime":"05","count":0},{"createsTime":"06","count":0},{"createsTime":"07","count":0},{"createsTime":"08","count":0},{"createsTime":"09","count":0},{"createsTime":"10","count":0},{"createsTime":"11","count":0},{"createsTime":"12","count":0},{"createsTime":"13","count":0},{"createsTime":"14","count":0},{"createsTime":"15","count":0},{"createsTime":"16","count":0},{"createsTime":"17","count":0},{"createsTime":"18","count":0},{"createsTime":"19","count":0},{"createsTime":"20","count":0},{"createsTime":"21","count":0},{"createsTime":"22","count":0},{"createsTime":"23","count":0}]
         */

        private int addfans;
        private int count_fans;
        private int day_visit;
        private int day_comment;
        private int day_like;
        private int day_contact;
        private String datetime;
        private String day_time;

        public String getDay_time() {
            return day_time;
        }

        public void setDay_time(String day_time) {
            this.day_time = day_time;
        }

        private int day_new_visit_num;
        private String day_new_visit_bai;
        private int day_old_visit_num;
        private String day_old_visit_bai;
        private int yesterday_visit;
        private int yesterday_browse;
        private int yesterday_contact;

        public int getYesterday_contact() {
            return yesterday_contact;
        }

        public void setYesterday_contact(int yesterday_contact) {
            this.yesterday_contact = yesterday_contact;
        }

        private List<StatisticsBean> statistics;

        public int getAddfans() {
            return addfans;
        }

        public void setAddfans(int addfans) {
            this.addfans = addfans;
        }

        public int getCount_fans() {
            return count_fans;
        }

        public void setCount_fans(int count_fans) {
            this.count_fans = count_fans;
        }

        public int getDay_visit() {
            return day_visit;
        }

        public void setDay_visit(int day_visit) {
            this.day_visit = day_visit;
        }

        public int getDay_comment() {
            return day_comment;
        }

        public void setDay_comment(int day_comment) {
            this.day_comment = day_comment;
        }

        public int getDay_like() {
            return day_like;
        }

        public void setDay_like(int day_like) {
            this.day_like = day_like;
        }

        public int getDay_contact() {
            return day_contact;
        }

        public void setDay_contact(int day_contact) {
            this.day_contact = day_contact;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public int getDay_new_visit_num() {
            return day_new_visit_num;
        }

        public void setDay_new_visit_num(int day_new_visit_num) {
            this.day_new_visit_num = day_new_visit_num;
        }

        public String getDay_new_visit_bai() {
            return day_new_visit_bai;
        }

        public void setDay_new_visit_bai(String day_new_visit_bai) {
            this.day_new_visit_bai = day_new_visit_bai;
        }

        public int getDay_old_visit_num() {
            return day_old_visit_num;
        }

        public void setDay_old_visit_num(int day_old_visit_num) {
            this.day_old_visit_num = day_old_visit_num;
        }

        public String getDay_old_visit_bai() {
            return day_old_visit_bai;
        }

        public void setDay_old_visit_bai(String day_old_visit_bai) {
            this.day_old_visit_bai = day_old_visit_bai;
        }

        public int getYesterday_visit() {
            return yesterday_visit;
        }

        public void setYesterday_visit(int yesterday_visit) {
            this.yesterday_visit = yesterday_visit;
        }

        public int getYesterday_browse() {
            return yesterday_browse;
        }

        public void setYesterday_browse(int yesterday_browse) {
            this.yesterday_browse = yesterday_browse;
        }

        public List<StatisticsBean> getStatistics() {
            return statistics;
        }

        public void setStatistics(List<StatisticsBean> statistics) {
            this.statistics = statistics;
        }

        public static class StatisticsBean {
            /**
             * createsTime : 00
             * count : 0
             */

            private String createsTime;
            private int count;

            public String getCreatesTime() {
                return createsTime;
            }

            public void setCreatesTime(String createsTime) {
                this.createsTime = createsTime;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
