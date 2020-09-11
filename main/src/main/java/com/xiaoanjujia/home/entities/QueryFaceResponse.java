package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/9/11 22:28
 * @Description:
 */
public class QueryFaceResponse {


    /**
     * data : [{"faceGroupIndexCode":"cd8a4e773eb246d4be4947d96ebfd04b","indexCode":"d6a9e4b483ba4e0e9c5aa6f0050e91dd","faceInfo":{"sex":"male","name":"小安1","certificateNum":"420204199605121656","certificateType":"ID"},"facePic":{"faceUrl":"https://36.112.172.190:6113/pic?=d14id180iec1=o4c-ap0e1b-16d4f8148aecc7i2b8*=ids1*=idp6*=tdpe*m5i16=8197987*70z8i-s=1de66*l1b7od5-fb366cd9&AccessKeyId=Rk+2rKXLjnHdShKt&Expires=1598776467&Signature=/qw1YRaRkbc8NpLotGpD5Ia1P44="}},{"faceGroupIndexCode":"cd8a4e773eb246d4be4947d96ebfd04b","indexCode":"bb7183f07a5e46b89ed47a9b8b4bb61a","faceInfo":{"sex":"male","name":"小安1","certificateNum":"420204199605121656","certificateType":"ID"},"facePic":{"faceUrl":"https://36.112.172.190:6113/pic?=d14id180iec1=o4c-ap0e1b-16d4f8148aecc7i2b8*=ids1*=idp5*=tdpe*m5i16=8494971*46z3i-s=1de66*l1b7od5-fb366cd9&AccessKeyId=Rk+2rKXLjnHdShKt&Expires=1598776467&Signature=mquRmY0NpjtgRECiqJLUyNOefWo="}},{"faceGroupIndexCode":"cd8a4e773eb246d4be4947d96ebfd04b","indexCode":"b6a15ca8f2ba45238681011687acd446","faceInfo":{"sex":"male","name":"小安","certificateNum":"420204199605121656","certificateType":"ID"},"facePic":{"faceUrl":"https://36.112.172.190:6113/pic?=d14id180iec1=o8c-ap0e1b-16d4f8148aecc7i2b8*=ids1*=idp4*=tdpe*m5i16=8697864*85z7i-s=2de66*l1b7od5-fb366cd9&AccessKeyId=Rk+2rKXLjnHdShKt&Expires=1598776467&Signature=vZtU4UtomG72TeQ6y13jaC0Z71s="}}]
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
         * faceGroupIndexCode : cd8a4e773eb246d4be4947d96ebfd04b
         * indexCode : d6a9e4b483ba4e0e9c5aa6f0050e91dd
         * faceInfo : {"sex":"male","name":"小安1","certificateNum":"420204199605121656","certificateType":"ID"}
         * facePic : {"faceUrl":"https://36.112.172.190:6113/pic?=d14id180iec1=o4c-ap0e1b-16d4f8148aecc7i2b8*=ids1*=idp6*=tdpe*m5i16=8197987*70z8i-s=1de66*l1b7od5-fb366cd9&AccessKeyId=Rk+2rKXLjnHdShKt&Expires=1598776467&Signature=/qw1YRaRkbc8NpLotGpD5Ia1P44="}
         */

        private String faceGroupIndexCode;
        private String indexCode;
        private FaceInfoBean faceInfo;
        private FacePicBean facePic;

        public String getFaceGroupIndexCode() {
            return faceGroupIndexCode;
        }

        public void setFaceGroupIndexCode(String faceGroupIndexCode) {
            this.faceGroupIndexCode = faceGroupIndexCode;
        }

        public String getIndexCode() {
            return indexCode;
        }

        public void setIndexCode(String indexCode) {
            this.indexCode = indexCode;
        }

        public FaceInfoBean getFaceInfo() {
            return faceInfo;
        }

        public void setFaceInfo(FaceInfoBean faceInfo) {
            this.faceInfo = faceInfo;
        }

        public FacePicBean getFacePic() {
            return facePic;
        }

        public void setFacePic(FacePicBean facePic) {
            this.facePic = facePic;
        }

        public static class FaceInfoBean {
            /**
             * sex : male
             * name : 小安1
             * certificateNum : 420204199605121656
             * certificateType : ID
             */

            private String sex;
            private String name;
            private String certificateNum;
            private String certificateType;

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCertificateNum() {
                return certificateNum;
            }

            public void setCertificateNum(String certificateNum) {
                this.certificateNum = certificateNum;
            }

            public String getCertificateType() {
                return certificateType;
            }

            public void setCertificateType(String certificateType) {
                this.certificateType = certificateType;
            }
        }

        public static class FacePicBean {
            /**
             * faceUrl : https://36.112.172.190:6113/pic?=d14id180iec1=o4c-ap0e1b-16d4f8148aecc7i2b8*=ids1*=idp6*=tdpe*m5i16=8197987*70z8i-s=1de66*l1b7od5-fb366cd9&AccessKeyId=Rk+2rKXLjnHdShKt&Expires=1598776467&Signature=/qw1YRaRkbc8NpLotGpD5Ia1P44=
             */

            private String faceUrl;

            public String getFaceUrl() {
                return faceUrl;
            }

            public void setFaceUrl(String faceUrl) {
                this.faceUrl = faceUrl;
            }
        }
    }
}
