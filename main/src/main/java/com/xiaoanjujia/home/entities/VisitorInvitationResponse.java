package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @author xp
 * 创建日期： 2020/9/8$
 * 描述 VisitorInvitation$
 */
public class VisitorInvitationResponse implements Serializable {

    /**
     * data : {"appointmentInfoList":[{"visitorName":"谢鹏","QRCode":"VjAwMSuegkn65rEzw5qhIkCYQKvUzkGXpH87k1c3TA358ry/tkj393hzBrvHnOwmLHWlIHc=","receptionistName":"老谢","orderId":"413edb4a-f1ea-11ea-8dc9-6770aaf1588b","receptionistId":"77edf8446c7c448e914caf14eeaf9f20","verificationCode":"6490"}],"appointRecordId":"ef7e7fefc42e4f7497648a694da74ebb"}
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
         * appointmentInfoList : [{"visitorName":"谢鹏","QRCode":"VjAwMSuegkn65rEzw5qhIkCYQKvUzkGXpH87k1c3TA358ry/tkj393hzBrvHnOwmLHWlIHc=","receptionistName":"老谢","orderId":"413edb4a-f1ea-11ea-8dc9-6770aaf1588b","receptionistId":"77edf8446c7c448e914caf14eeaf9f20","verificationCode":"6490"}]
         * appointRecordId : ef7e7fefc42e4f7497648a694da74ebb
         */

        private String appointRecordId;
        private List<AppointmentInfoListBean> appointmentInfoList;

        public String getAppointRecordId() {
            return appointRecordId;
        }

        public void setAppointRecordId(String appointRecordId) {
            this.appointRecordId = appointRecordId;
        }

        public List<AppointmentInfoListBean> getAppointmentInfoList() {
            return appointmentInfoList;
        }

        public void setAppointmentInfoList(List<AppointmentInfoListBean> appointmentInfoList) {
            this.appointmentInfoList = appointmentInfoList;
        }

        public static class AppointmentInfoListBean {
            /**
             * visitorName : 谢鹏
             * QRCode : VjAwMSuegkn65rEzw5qhIkCYQKvUzkGXpH87k1c3TA358ry/tkj393hzBrvHnOwmLHWlIHc=
             * receptionistName : 老谢
             * orderId : 413edb4a-f1ea-11ea-8dc9-6770aaf1588b
             * receptionistId : 77edf8446c7c448e914caf14eeaf9f20
             * verificationCode : 6490
             */

            private String visitorName;
            private String QRCode;
            private String receptionistName;
            private String orderId;
            private String receptionistId;
            private String verificationCode;

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

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getReceptionistId() {
                return receptionistId;
            }

            public void setReceptionistId(String receptionistId) {
                this.receptionistId = receptionistId;
            }

            public String getVerificationCode() {
                return verificationCode;
            }

            public void setVerificationCode(String verificationCode) {
                this.verificationCode = verificationCode;
            }
        }
    }
}
