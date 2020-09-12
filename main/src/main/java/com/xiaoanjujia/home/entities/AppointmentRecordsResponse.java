package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/9/12 12:04
 * @Description: 访客 记录
 */
public class AppointmentRecordsResponse {

    /**
     * data : [{"gender":1,"orderId":"65ddb256-e866-11ea-a48e-df1e1ab0186e","visitStartTime":"2020-08-29T15:00:00+08:00","visitPurpose":"参观","customa":"","phoneNo":"13613513927","visitEndTime":"2020-08-29T19:00:00+08:00","verificationCode":"8099","privilegeGroupNames":["访客权限门禁点"],"visitorName":"井号1","QRCode":"VjAwMSsq9iop+DDErm/wcwiUIValTRVYjtKzXrClRDTmBIx3ghEz4fUEmwjrbC52CNhRcGM=","receptionistName":"景灏","appointRecordId":"440506ed3111403a9abf0cd2e18bf19e","receptionistCode":"01101003","visitorStatus":3,"receptionistId":"aebcf3a7b59b4fb889daaea2b45c2bf5","visitorId":"65ddb256-e866-11ea-a48e-df1e1ab0186e"},{"gender":1,"orderId":"6711169e-e867-11ea-b37d-4befb56478be","visitStartTime":"2020-08-30T15:00:00+08:00","visitPurpose":"参观","customa":"","phoneNo":"13613513927","visitEndTime":"2020-08-30T19:00:00+08:00","verificationCode":"0398","privilegeGroupNames":["访客权限门禁点"],"visitorName":"井号1","QRCode":"VjAwMStVP5L9rLplpxhVkdYkSG2anhjOT2uyIE4RL5EWT+67JV92SeDbKMsqSidD3xKAeWc=","receptionistName":"景灏","appointRecordId":"070de6dd4a77464d851b6e1eeb87c392","receptionistCode":"01101003","visitorStatus":2,"receptionistId":"aebcf3a7b59b4fb889daaea2b45c2bf5","visitorId":"6711169e-e867-11ea-b37d-4befb56478be"}]
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
         * gender : 1
         * orderId : 65ddb256-e866-11ea-a48e-df1e1ab0186e
         * visitStartTime : 2020-08-29T15:00:00+08:00
         * visitPurpose : 参观
         * customa :
         * phoneNo : 13613513927
         * visitEndTime : 2020-08-29T19:00:00+08:00
         * verificationCode : 8099
         * privilegeGroupNames : ["访客权限门禁点"]
         * visitorName : 井号1
         * QRCode : VjAwMSsq9iop+DDErm/wcwiUIValTRVYjtKzXrClRDTmBIx3ghEz4fUEmwjrbC52CNhRcGM=
         * receptionistName : 景灏
         * appointRecordId : 440506ed3111403a9abf0cd2e18bf19e
         * receptionistCode : 01101003
         * visitorStatus : 3
         * receptionistId : aebcf3a7b59b4fb889daaea2b45c2bf5
         * visitorId : 65ddb256-e866-11ea-a48e-df1e1ab0186e
         */

        private int gender;
        private String orderId;
        private String visitStartTime;
        private String visitPurpose;
        private String customa;
        private String phoneNo;
        private String visitEndTime;
        private String verificationCode;
        private String visitorName;
        private String QRCode;
        private String receptionistName;
        private String appointRecordId;
        private String receptionistCode;
        private int visitorStatus;
        private String receptionistId;
        private String visitorId;
        private String plateNo;

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        private List<String> privilegeGroupNames;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
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

        public String getAppointRecordId() {
            return appointRecordId;
        }

        public void setAppointRecordId(String appointRecordId) {
            this.appointRecordId = appointRecordId;
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

        public String getVisitorId() {
            return visitorId;
        }

        public void setVisitorId(String visitorId) {
            this.visitorId = visitorId;
        }

        public List<String> getPrivilegeGroupNames() {
            return privilegeGroupNames;
        }

        public void setPrivilegeGroupNames(List<String> privilegeGroupNames) {
            this.privilegeGroupNames = privilegeGroupNames;
        }
    }
}
