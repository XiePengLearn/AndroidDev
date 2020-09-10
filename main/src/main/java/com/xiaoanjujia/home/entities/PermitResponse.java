package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @author xp
 * 创建日期： 2020/9/7$
 * 描述 qr code$
 */
public class PermitResponse implements Serializable {

    /**
     * data : [{"gender":1,"nation":1,"orderId":"538a8726-f371-11ea-9cf0-cf99ee937498","plateNo":"京5666","visitStartTime":"2020-09-13T22:22:35+08:00","visitPurpose":"啦啦啦","customa":"","certificateNo":"411425199103199030","phoneNo":"15601276550","visitEndTime":"2020-09-14T15:22:35+08:00","verificationCode":"5570","privilegeGroupNames":["访客权限门禁点"],"visitorName":"谢鹏","QRCode":"VjAwMSupfzP4UohLB//laW9u1Sst/DpaOUILQTrQPTwlHsXwq2yT2bjGHl/A38Yefx8XTfQ=","receptionistName":"老谢","appointRecordId":"bb838787f6b540bdbd33cb20254801a4","receptionistCode":"01101002010010100102","visitorStatus":1,"receptionistId":"77edf8446c7c448e914caf14eeaf9f20","certificateType":111,"visitorId":"538a8726-f371-11ea-9cf0-cf99ee937498"}]
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
         * nation : 1
         * orderId : 538a8726-f371-11ea-9cf0-cf99ee937498
         * plateNo : 京5666
         * visitStartTime : 2020-09-13T22:22:35+08:00
         * visitPurpose : 啦啦啦
         * customa :
         * certificateNo : 411425199103199030
         * phoneNo : 15601276550
         * visitEndTime : 2020-09-14T15:22:35+08:00
         * verificationCode : 5570
         * privilegeGroupNames : ["访客权限门禁点"]
         * visitorName : 谢鹏
         * QRCode : VjAwMSupfzP4UohLB//laW9u1Sst/DpaOUILQTrQPTwlHsXwq2yT2bjGHl/A38Yefx8XTfQ=
         * receptionistName : 老谢
         * appointRecordId : bb838787f6b540bdbd33cb20254801a4
         * receptionistCode : 01101002010010100102
         * visitorStatus : 1
         * receptionistId : 77edf8446c7c448e914caf14eeaf9f20
         * certificateType : 111
         * visitorId : 538a8726-f371-11ea-9cf0-cf99ee937498
         */

        private int gender;
        private int nation;
        private String orderId;
        private String plateNo;
        private String visitStartTime;
        private String visitPurpose;
        private String customa;
        private String certificateNo;
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
        private int certificateType;
        private String visitorId;
        private List<String> privilegeGroupNames;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getNation() {
            return nation;
        }

        public void setNation(int nation) {
            this.nation = nation;
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

        public int getCertificateType() {
            return certificateType;
        }

        public void setCertificateType(int certificateType) {
            this.certificateType = certificateType;
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
