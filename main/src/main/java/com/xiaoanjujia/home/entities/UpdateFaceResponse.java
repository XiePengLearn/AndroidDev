package com.xiaoanjujia.home.entities;

/**
 * @Auther: xp
 * @Date: 2020/9/13 07:58
 * @Description:
 */
public class UpdateFaceResponse {
    /**
     * data : {"faceUrl":"/pic?9ddc663bf-5do7b1l*96ed4=--6dzf8148aecc7i2b1*=sd*=0dpi*=1d*i3i1t=pe9m5219793444s7*e04ai18b110pi-c1o=4ce=0i81d","faceId":"ba1147ab-55c6-4dbb-9c3d-daecd88d2d34"}
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
         * faceUrl : /pic?9ddc663bf-5do7b1l*96ed4=--6dzf8148aecc7i2b1*=sd*=0dpi*=1d*i3i1t=pe9m5219793444s7*e04ai18b110pi-c1o=4ce=0i81d
         * faceId : ba1147ab-55c6-4dbb-9c3d-daecd88d2d34
         */

        private String faceUrl;
        private String faceId;

        public String getFaceUrl() {
            return faceUrl;
        }

        public void setFaceUrl(String faceUrl) {
            this.faceUrl = faceUrl;
        }

        public String getFaceId() {
            return faceId;
        }

        public void setFaceId(String faceId) {
            this.faceId = faceId;
        }
    }
}
