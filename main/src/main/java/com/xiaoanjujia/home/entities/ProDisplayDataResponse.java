package com.xiaoanjujia.home.entities;

/**
 * created by Administrator on 2020/9/28 0028
 */
public class ProDisplayDataResponse {
    /**
     * status : 1
     * message : OK
     * data : {"id":1,"phone":"18635805566"}
     */

    private int status;
    private String message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String data;

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


}
