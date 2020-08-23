package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class LogRefuseResponse {

    /**
     * status : 1
     * message : 拒绝成功
     * data : []
     */

    private int status;
    private String message;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
