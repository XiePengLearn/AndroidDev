package com.xiaoanjujia.home.entities;

/**
 * @Auther: xp
 * @Date: 2020/9/11 21:29
 * @Description:
 */
public class AddFaceResponse {

    /**
     * data : {"faceUrl":"/pic?9ddc663bf-5do7b1l*96ed4=s-i1z59*4585909=71i5m*epdt=*4pdi=*1s=i1b0i7dce*8428fcd6a-b1e04a-18o11cpi0c1d=41ie8=","personId":"e5cab658a0024ae29837755c4933f04f","faceId":"b5b988d7-662a-49e5-aeb9-6479ad154fe1"}
     * message : success
     * status : 1
     */

    private DataBean data;
    private String message;
    private String status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * faceUrl : /pic?9ddc663bf-5do7b1l*96ed4=s-i1z59*4585909=71i5m*epdt=*4pdi=*1s=i1b0i7dce*8428fcd6a-b1e04a-18o11cpi0c1d=41ie8=
         * personId : e5cab658a0024ae29837755c4933f04f
         * faceId : b5b988d7-662a-49e5-aeb9-6479ad154fe1
         */

        private String faceUrl;
        private String personId;
        private String faceId;

        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getFaceId() {
            return faceId;
        }

        public void setFaceId(String faceId) {
            this.faceId = faceId;
        }
    }
}
