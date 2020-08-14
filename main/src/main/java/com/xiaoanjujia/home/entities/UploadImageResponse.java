package com.xiaoanjujia.home.entities;

/**
 * @Auther: xp
 * @Date: 2019/9/21 16:11
 * @Description:
 */
public class UploadImageResponse {

    /**
     * code : 200200
     * msg : 说明
     * data : {"IMAGE_URI":"图片URI地址"}
     */
    /*{
     *     "status": 1,
     *     "message": "OK",
     *     "data": {
     *         "path": "https://a.xiaoanjujia.com/uploads/images/2020/07-21/b104c02321833ac7dcc323539f5a2ebe.jpg,https://a.xiaoanjujia.com/uploads/images/2020/07-21/ea5b207875b7f9d49b3c5094d07bb8b0.jpg"
     *     }
     * }*/
    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * IMAGE_URI : 图片URI地址
         */

        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
