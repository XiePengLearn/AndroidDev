package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/9/13 12:52
 * @Description:
 */
public class SelectHousingResponse {


    /**
     * data : [{"regionPath":"@root000000@3f365275-9240-4fc4-8543-814ae4caaa0c@","cascadeType":0,"catalogType":10,"cascadeCode":"0","available":true,"indexCode":"3f365275-9240-4fc4-8543-814ae4caaa0c","updateTime":"2020-07-22T11:03:16.047+08:00","sort":6,"nodeType":1,"leaf":false,"regionCode":"01201001","createTime":"2020-07-22T09:56:51.079+08:00","name":"张营小区","parentIndexCode":"root000000"},{"regionPath":"@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@","cascadeType":0,"catalogType":10,"cascadeCode":"0","available":true,"indexCode":"67e12695-c84a-4237-8cc8-7704fa180e89","description":"位于南环路","updateTime":"2020-08-06T14:29:35.503+08:00","sort":7,"nodeType":1,"leaf":false,"regionCode":"01201002","createTime":"2020-07-22T09:57:59.255+08:00","name":"张营小区三栋楼","parentIndexCode":"root000000"},{"regionPath":"@root000000@857dda5d-38b5-4581-807e-6187c6cbce0f@","cascadeType":0,"catalogType":10,"cascadeCode":"0","available":true,"indexCode":"857dda5d-38b5-4581-807e-6187c6cbce0f","updateTime":"2020-08-06T14:47:37.457+08:00","sort":8,"nodeType":1,"leaf":false,"regionCode":"01201003","createTime":"2020-07-22T09:58:31.741+08:00","name":"何营小区A地块","parentIndexCode":"root000000"},{"regionPath":"@root000000@1e405483-c577-4fb5-b1f9-0b57aa6c05da@","cascadeType":0,"catalogType":10,"cascadeCode":"0","available":true,"indexCode":"1e405483-c577-4fb5-b1f9-0b57aa6c05da","updateTime":"2020-08-10T09:29:09.847+08:00","sort":9,"nodeType":1,"leaf":false,"regionCode":"01201222","createTime":"2020-07-22T09:58:52.222+08:00","name":"何营小区B地块","parentIndexCode":"root000000"},{"regionPath":"@root000000@92067959-1cc4-4429-9093-05a1ed827a5a@","cascadeType":0,"catalogType":10,"cascadeCode":"0","available":true,"indexCode":"92067959-1cc4-4429-9093-05a1ed827a5a","updateTime":"2020-08-06T15:26:53.661+08:00","sort":10,"nodeType":1,"leaf":false,"regionCode":"01201005","createTime":"2020-07-22T09:59:23.325+08:00","name":"何营小区C地块","parentIndexCode":"root000000"}]
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
         * regionPath : @root000000@3f365275-9240-4fc4-8543-814ae4caaa0c@
         * cascadeType : 0
         * catalogType : 10
         * cascadeCode : 0
         * available : true
         * indexCode : 3f365275-9240-4fc4-8543-814ae4caaa0c
         * updateTime : 2020-07-22T11:03:16.047+08:00
         * sort : 6
         * nodeType : 1
         * leaf : false
         * regionCode : 01201001
         * createTime : 2020-07-22T09:56:51.079+08:00
         * name : 张营小区
         * parentIndexCode : root000000
         * description : 位于南环路
         */

        private String regionPath;
        private int cascadeType;
        private int catalogType;
        private String cascadeCode;
        private boolean available;
        private String indexCode;
        private String updateTime;
        private int sort;
        private int nodeType;
        private boolean leaf;
        private String regionCode;
        private String createTime;
        private String name;
        private String parentIndexCode;
        private String description;

        public String getRegionPath() {
            return regionPath;
        }

        public void setRegionPath(String regionPath) {
            this.regionPath = regionPath;
        }

        public int getCascadeType() {
            return cascadeType;
        }

        public void setCascadeType(int cascadeType) {
            this.cascadeType = cascadeType;
        }

        public int getCatalogType() {
            return catalogType;
        }

        public void setCatalogType(int catalogType) {
            this.catalogType = catalogType;
        }

        public String getCascadeCode() {
            return cascadeCode;
        }

        public void setCascadeCode(String cascadeCode) {
            this.cascadeCode = cascadeCode;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        public String getIndexCode() {
            return indexCode;
        }

        public void setIndexCode(String indexCode) {
            this.indexCode = indexCode;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getNodeType() {
            return nodeType;
        }

        public void setNodeType(int nodeType) {
            this.nodeType = nodeType;
        }

        public boolean isLeaf() {
            return leaf;
        }

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentIndexCode() {
            return parentIndexCode;
        }

        public void setParentIndexCode(String parentIndexCode) {
            this.parentIndexCode = parentIndexCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
