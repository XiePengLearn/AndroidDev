package com.xiaoanjujia.home.entities;

import java.util.List;

/**
 * @Auther: xp
 * @Date: 2020/9/13 15:47
 * @Description:
 */
public class RootNextRegionResponse {
    /**
     * data : [{"regionPath":"@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@e01dae84-10fc-4f21-8863-acac68ea41ae@","cascadeType":0,"catalogType":12,"cascadeCode":"0","available":true,"indexCode":"e01dae84-10fc-4f21-8863-acac68ea41ae","updateTime":"2020-08-06T14:30:01.421+08:00","sort":1,"nodeType":2,"leaf":false,"regionCode":"01201002010010100000","createTime":"2020-08-06T14:30:01.421+08:00","name":"1单元","parentIndexCode":"83237a94-2226-42bd-9c21-adfe380699dd"},{"regionPath":"@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@a787705e-33a8-445f-a2f2-4b8cc7bf8c98@","cascadeType":0,"catalogType":12,"cascadeCode":"0","available":true,"indexCode":"a787705e-33a8-445f-a2f2-4b8cc7bf8c98","updateTime":"2020-08-06T14:30:01.424+08:00","sort":2,"nodeType":2,"leaf":false,"regionCode":"01201002010010200000","createTime":"2020-08-06T14:30:01.424+08:00","name":"2单元","parentIndexCode":"83237a94-2226-42bd-9c21-adfe380699dd"},{"regionPath":"@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@aea4f1bc-2560-453e-ae67-f711e0f79245@","cascadeType":0,"catalogType":12,"cascadeCode":"0","available":true,"indexCode":"aea4f1bc-2560-453e-ae67-f711e0f79245","updateTime":"2020-08-06T14:30:01.428+08:00","sort":3,"nodeType":2,"leaf":false,"regionCode":"01201002010010300000","createTime":"2020-08-06T14:30:01.428+08:00","name":"3单元","parentIndexCode":"83237a94-2226-42bd-9c21-adfe380699dd"},{"regionPath":"@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@e88e7ad8-be62-481f-a45c-47c4030a028f@","cascadeType":0,"catalogType":12,"cascadeCode":"0","available":true,"indexCode":"e88e7ad8-be62-481f-a45c-47c4030a028f","updateTime":"2020-08-06T14:30:01.432+08:00","sort":4,"nodeType":2,"leaf":false,"regionCode":"01201002010010400000","createTime":"2020-08-06T14:30:01.432+08:00","name":"4单元","parentIndexCode":"83237a94-2226-42bd-9c21-adfe380699dd"},{"regionPath":"@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@3c3f1a25-06dd-4233-ac70-84be735a81c0@","cascadeType":0,"catalogType":12,"cascadeCode":"0","available":true,"indexCode":"3c3f1a25-06dd-4233-ac70-84be735a81c0","updateTime":"2020-08-06T14:30:01.435+08:00","sort":5,"nodeType":2,"leaf":false,"regionCode":"01201002010010500000","createTime":"2020-08-06T14:30:01.435+08:00","name":"5单元","parentIndexCode":"83237a94-2226-42bd-9c21-adfe380699dd"},{"regionPath":"@root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@786738cb-6f10-48be-bd67-06ded569af8f@","cascadeType":0,"catalogType":12,"cascadeCode":"0","available":true,"indexCode":"786738cb-6f10-48be-bd67-06ded569af8f","updateTime":"2020-08-06T14:30:01.437+08:00","sort":6,"nodeType":2,"leaf":false,"regionCode":"01201002010010600000","createTime":"2020-08-06T14:30:01.437+08:00","name":"6单元","parentIndexCode":"83237a94-2226-42bd-9c21-adfe380699dd"}]
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
         * regionPath : @root000000@67e12695-c84a-4237-8cc8-7704fa180e89@27891eaa-03fa-40da-9fce-47c3d19ecc69@83237a94-2226-42bd-9c21-adfe380699dd@e01dae84-10fc-4f21-8863-acac68ea41ae@
         * cascadeType : 0
         * catalogType : 12
         * cascadeCode : 0
         * available : true
         * indexCode : e01dae84-10fc-4f21-8863-acac68ea41ae
         * updateTime : 2020-08-06T14:30:01.421+08:00
         * sort : 1
         * nodeType : 2
         * leaf : false
         * regionCode : 01201002010010100000
         * createTime : 2020-08-06T14:30:01.421+08:00
         * name : 1单元
         * parentIndexCode : 83237a94-2226-42bd-9c21-adfe380699dd
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
    }
}
