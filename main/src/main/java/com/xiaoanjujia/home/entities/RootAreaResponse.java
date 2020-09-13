package com.xiaoanjujia.home.entities;

/**
 * @Auther: xp
 * @Date: 2020/9/13 11:22
 * @Description:
 */
public class RootAreaResponse {
    /**
     * data : {"name":"铭嘉地产","indexCode":"root000000","treeCode":"0","parentIndexCode":"-1"}
     * message : success
     * status : 0
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
         * name : 铭嘉地产
         * indexCode : root000000
         * treeCode : 0
         * parentIndexCode : -1
         */

        private String name;
        private String indexCode;
        private String treeCode;
        private String parentIndexCode;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIndexCode() {
            return indexCode;
        }

        public void setIndexCode(String indexCode) {
            this.indexCode = indexCode;
        }

        public String getTreeCode() {
            return treeCode;
        }

        public void setTreeCode(String treeCode) {
            this.treeCode = treeCode;
        }

        public String getParentIndexCode() {
            return parentIndexCode;
        }

        public void setParentIndexCode(String parentIndexCode) {
            this.parentIndexCode = parentIndexCode;
        }
    }
}
