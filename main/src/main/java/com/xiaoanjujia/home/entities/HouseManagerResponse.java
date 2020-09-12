package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/9/12 22:07
 * @Description:
 */
public class HouseManagerResponse {

    /**
     * data : [{"endDate":"2037-12-31T23:59:59.000+08:00","houseIndexCode":"ff7011c4-a28e-4149-8d58-0b93a69ca236","personId":"3c17d5694a8c4f1aacd60ae44c1267db","personHouseId":"b8d2339763d1424e8d02745b7c8163c7","releationType":0,"startDate":"2020-08-29T00:00:00.000+08:00"},{"endDate":"2021-08-11T00:00:00.000+08:00","houseIndexCode":"ff7011c4-a28e-4149-8d58-0b93a69ca236","personId":"1c576a28f3bd4f8aac5ef1058fae6f92","personHouseId":"19cd5c4c9c1e41f68b8eb11931453e98","releationType":1,"startDate":"2020-08-11T00:00:00.000+08:00"}]
     * message : success
     * status : 0
     */

    private String message;
    private String status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * endDate : 2037-12-31T23:59:59.000+08:00
         * houseIndexCode : ff7011c4-a28e-4149-8d58-0b93a69ca236
         * personId : 3c17d5694a8c4f1aacd60ae44c1267db
         * personHouseId : b8d2339763d1424e8d02745b7c8163c7
         * releationType : 0
         * startDate : 2020-08-29T00:00:00.000+08:00
         */

        private String endDate;
        private String houseIndexCode;
        private String personId;
        private String personHouseId;
        private int releationType;
        private String startDate;

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getHouseIndexCode() {
            return houseIndexCode;
        }

        public void setHouseIndexCode(String houseIndexCode) {
            this.houseIndexCode = houseIndexCode;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getPersonHouseId() {
            return personHouseId;
        }

        public void setPersonHouseId(String personHouseId) {
            this.personHouseId = personHouseId;
        }

        public int getReleationType() {
            return releationType;
        }

        public void setReleationType(int releationType) {
            this.releationType = releationType;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
    }
}
