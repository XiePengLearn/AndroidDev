package com.xiaoanjujia.home.entities;

/**
 * @Auther: xp
 * @Date: 2019/10/8 16:43
 * @Description:
 */
public class LegalPersonAuditResponse {


    /**
     * code : 200200
     * data : {"TRIAL_URI":"https://home.firefoxchina.cn/","TRIAL_TYPE":"1"}
     * msg :
     */

    private String   code;
    private DataBean data;
    private String   msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * TRIAL_URI : https://home.firefoxchina.cn/
         * TRIAL_TYPE : 1
         */

        private String TRIAL_URI;
        private String TRIAL_TYPE;

        public String getTRIAL_URI() {
            return TRIAL_URI;
        }

        public void setTRIAL_URI(String TRIAL_URI) {
            this.TRIAL_URI = TRIAL_URI;
        }

        public String getTRIAL_TYPE() {
            return TRIAL_TYPE;
        }

        public void setTRIAL_TYPE(String TRIAL_TYPE) {
            this.TRIAL_TYPE = TRIAL_TYPE;
        }
    }
}
