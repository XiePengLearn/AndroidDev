package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @author xp
 * 创建日期： 2020/9/7$
 * 描述 qr code$
 */
public class QrCodeResponse implements Serializable {
    /**
     * data : {"cardNo":"9990000029","barCode":"VjAwMSu5hNy9oYLfZX0N9naoc/WYbCnGhAYD4tF3Cu95AJBc/ZjZWfzOJDWeFbHWkvG6Ba4="}
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
         * cardNo : 9990000029
         * barCode : VjAwMSu5hNy9oYLfZX0N9naoc/WYbCnGhAYD4tF3Cu95AJBc/ZjZWfzOJDWeFbHWkvG6Ba4=
         */

        private String cardNo;
        private String barCode;

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }
    }
}
