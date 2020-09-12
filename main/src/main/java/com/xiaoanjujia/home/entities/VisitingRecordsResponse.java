package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/9/12 12:04
 * @Description:
 */
public class VisitingRecordsResponse {

    /**
     * data : [{"gender":1,"svrIndexCode":"abb8078f-a284-4d93-814d-7adc8f290fbf","orderId":"858b8e66-d196-11ea-9871-fb1996ad020b","plateNo":"","visitStartTime":"2020-07-29T20:25:57+08:00","visitPurpose":"做客","customa":"","personNum":1,"certificateNo":"","phoneNo":"15903380652","visitEndTime":"2020-07-30T00:05:00+08:00","verificationCode":"2689","visitorName":"测试","QRCode":"VjAwMSuDRTY48gpECPM8g/TVgfbeBuJ9WWrSymnh+2SJWlmlcw87pUjQG3iT16U7S4ASlPg=","receptionistName":"郭亮","plannedStartTime":"2020-07-29T20:27:00+08:00","picUri":"/pic?=d29id189iec1=o1c-ap5e1b-36d4f8148aecc7i2b4*=ids1*=idp8*=tdpe*m5i10=6595320*01z2i-s=2de64*l1b7od2-fb569cd2","visitorWorkUnit":"","plannedEndTime":"2020-07-29T23:59:00+08:00","receptionistCode":"0000","visitorStatus":5,"receptionistId":"d04ce4555257448d900b9e7ad28020d4"},{"gender":1,"svrIndexCode":"abb8078f-a284-4d93-814d-7adc8f290fbf","orderId":"134a8784-d192-11ea-a0b0-3bade7baf1fe","plateNo":"","visitStartTime":"2020-07-29T19:56:29+08:00","visitPurpose":"做客","customa":"","personNum":1,"certificateNo":"","phoneNo":"18969199889","visitEndTime":"2020-07-30T00:05:00+08:00","verificationCode":"5447","visitorName":"海康测试","QRCode":"VjAwMSta9PrhXQXDfq+2y0q3/JVU/YAZdpYIXDbJVXvWvNmYvME/4tHlscnNVlKZ6AZXCp4=","receptionistName":"郭亮","plannedStartTime":"2020-07-29T20:00:00+08:00","picUri":"/pic?=d29id189iec1=o2c-ap0e1b-26d4f8148aecc7i2b4*=ids1*=idp4*=tdpe*m5i10=6593620*90z6i-s=8de69*l1b7od2-fb569cd2","visitorWorkUnit":"","plannedEndTime":"2020-07-29T23:59:00+08:00","receptionistCode":"0000","visitorStatus":5,"receptionistId":"d04ce4555257448d900b9e7ad28020d4"}]
     * message : success
     * status : 1
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
         * gender : 1
         * svrIndexCode : abb8078f-a284-4d93-814d-7adc8f290fbf
         * orderId : 858b8e66-d196-11ea-9871-fb1996ad020b
         * plateNo :
         * visitStartTime : 2020-07-29T20:25:57+08:00
         * visitPurpose : 做客
         * customa :
         * personNum : 1
         * certificateNo :
         * phoneNo : 15903380652
         * visitEndTime : 2020-07-30T00:05:00+08:00
         * verificationCode : 2689
         * visitorName : 测试
         * QRCode : VjAwMSuDRTY48gpECPM8g/TVgfbeBuJ9WWrSymnh+2SJWlmlcw87pUjQG3iT16U7S4ASlPg=
         * receptionistName : 郭亮
         * plannedStartTime : 2020-07-29T20:27:00+08:00
         * picUri : /pic?=d29id189iec1=o1c-ap5e1b-36d4f8148aecc7i2b4*=ids1*=idp8*=tdpe*m5i10=6595320*01z2i-s=2de64*l1b7od2-fb569cd2
         * visitorWorkUnit :
         * plannedEndTime : 2020-07-29T23:59:00+08:00
         * receptionistCode : 0000
         * visitorStatus : 5
         * receptionistId : d04ce4555257448d900b9e7ad28020d4
         */

        private int gender;
        private String svrIndexCode;
        private String orderId;
        private String plateNo;
        private String visitStartTime;
        private String visitPurpose;
        private String customa;
        private int personNum;
        private String certificateNo;
        private String phoneNo;
        private String visitEndTime;
        private String verificationCode;
        private String visitorName;
        private String QRCode;
        private String receptionistName;
        private String plannedStartTime;
        private String picUri;
        private String visitorWorkUnit;
        private String plannedEndTime;
        private String receptionistCode;
        private int visitorStatus;
        private String receptionistId;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getSvrIndexCode() {
            return svrIndexCode;
        }

        public void setSvrIndexCode(String svrIndexCode) {
            this.svrIndexCode = svrIndexCode;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getVisitStartTime() {
            return visitStartTime;
        }

        public void setVisitStartTime(String visitStartTime) {
            this.visitStartTime = visitStartTime;
        }

        public String getVisitPurpose() {
            return visitPurpose;
        }

        public void setVisitPurpose(String visitPurpose) {
            this.visitPurpose = visitPurpose;
        }

        public String getCustoma() {
            return customa;
        }

        public void setCustoma(String customa) {
            this.customa = customa;
        }

        public int getPersonNum() {
            return personNum;
        }

        public void setPersonNum(int personNum) {
            this.personNum = personNum;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getVisitEndTime() {
            return visitEndTime;
        }

        public void setVisitEndTime(String visitEndTime) {
            this.visitEndTime = visitEndTime;
        }

        public String getVerificationCode() {
            return verificationCode;
        }

        public void setVerificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
        }

        public String getVisitorName() {
            return visitorName;
        }

        public void setVisitorName(String visitorName) {
            this.visitorName = visitorName;
        }

        public String getQRCode() {
            return QRCode;
        }

        public void setQRCode(String QRCode) {
            this.QRCode = QRCode;
        }

        public String getReceptionistName() {
            return receptionistName;
        }

        public void setReceptionistName(String receptionistName) {
            this.receptionistName = receptionistName;
        }

        public String getPlannedStartTime() {
            return plannedStartTime;
        }

        public void setPlannedStartTime(String plannedStartTime) {
            this.plannedStartTime = plannedStartTime;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getVisitorWorkUnit() {
            return visitorWorkUnit;
        }

        public void setVisitorWorkUnit(String visitorWorkUnit) {
            this.visitorWorkUnit = visitorWorkUnit;
        }

        public String getPlannedEndTime() {
            return plannedEndTime;
        }

        public void setPlannedEndTime(String plannedEndTime) {
            this.plannedEndTime = plannedEndTime;
        }

        public String getReceptionistCode() {
            return receptionistCode;
        }

        public void setReceptionistCode(String receptionistCode) {
            this.receptionistCode = receptionistCode;
        }

        public int getVisitorStatus() {
            return visitorStatus;
        }

        public void setVisitorStatus(int visitorStatus) {
            this.visitorStatus = visitorStatus;
        }

        public String getReceptionistId() {
            return receptionistId;
        }

        public void setReceptionistId(String receptionistId) {
            this.receptionistId = receptionistId;
        }
    }
}
