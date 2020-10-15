package com.xiaoanjujia.home.entities;

import java.io.Serializable;

/**
 * @Auther: xp
 * @Date: 2019/9/18 09:15
 * @Description:
 */
public class ChangeAccountResponse implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : {"appstatus":1,"appurl":"https://www.xiaoanjujia.com/app_sdk/app_demo.php"}
     */

    private int status;
    private String message;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
