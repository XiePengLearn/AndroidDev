package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @author xp
 * 创建日期： 2020/9/6$
 * 描述 开锁页面人员信息$
 */
public class VisitorPersonInfoResponse implements Serializable {

    /**
     * data : [{"marriaged":0,"orgName":"铭嘉地产","gender":2,"orgPath":"@root000000@","syncFlag":0,"orgPathName":"铭嘉地产","orgIndexCode":"root000000","orgList":["root000000"],"updateTime":"2020-08-11T17:13:13.687+08:00","certificateNo":"13072719861044333","phoneNo":"13683673419","personName":"霍冉","pinyin":"huoran","roomNum":"101","personPhoto":[{"personPhotoIndexCode":"f182e3c0-0eaf-49a7-8590-f8d0f8a59be5","picUri":"/pic?9ddc663bf-5do7b1l*46ed61i-z0e*3s0337781115m9ep=t=i9p*i=d1s*i=d3b*i7dce*8428fcd6a-b1=74a-40o11cpi0c1d=41ie8=","serverIndexCode":"7ef23451-2765-48a7-bdb5-dbfb118f0061"}],"createTime":"2020-08-11T10:06:28.427+08:00","jobNo":"101","personId":"3c17d5694a8c4f1aacd60ae44c1267db","lodge":0,"age":0,"certificateType":111},{"marriaged":0,"orgName":"铭嘉地产","gender":1,"orgPath":"@root000000@","syncFlag":0,"orgPathName":"铭嘉地产","orgIndexCode":"root000000","orgList":["root000000"],"updateTime":"2020-08-11T17:13:13.687+08:00","certificateNo":"110221198412125016","phoneNo":"17343190575","personName":"候建兵","pinyin":"houjianbing","personPhoto":[],"createTime":"2020-08-10T09:28:16.516+08:00","jobNo":"111","personId":"65277007e605477fb80eaa25dd91e4b8","lodge":0,"age":0,"certificateType":111}]
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
         * marriaged : 0
         * orgName : 铭嘉地产
         * gender : 2
         * orgPath : @root000000@
         * syncFlag : 0
         * orgPathName : 铭嘉地产
         * orgIndexCode : root000000
         * orgList : ["root000000"]
         * updateTime : 2020-08-11T17:13:13.687+08:00
         * certificateNo : 13072719861044333
         * phoneNo : 13683673419
         * personName : 霍冉
         * pinyin : huoran
         * roomNum : 101
         * personPhoto : [{"personPhotoIndexCode":"f182e3c0-0eaf-49a7-8590-f8d0f8a59be5","picUri":"/pic?9ddc663bf-5do7b1l*46ed61i-z0e*3s0337781115m9ep=t=i9p*i=d1s*i=d3b*i7dce*8428fcd6a-b1=74a-40o11cpi0c1d=41ie8=","serverIndexCode":"7ef23451-2765-48a7-bdb5-dbfb118f0061"}]
         * createTime : 2020-08-11T10:06:28.427+08:00
         * jobNo : 101
         * personId : 3c17d5694a8c4f1aacd60ae44c1267db
         * lodge : 0
         * age : 0
         * certificateType : 111
         */

        private int marriaged;
        private String orgName;
        private int gender;
        private String orgPath;
        private int syncFlag;
        private String orgPathName;
        private String orgIndexCode;
        private String updateTime;
        private String certificateNo;
        private String phoneNo;
        private String personName;
        private String pinyin;
        private String roomNum;
        private String createTime;
        private String jobNo;
        private String personId;
        private int lodge;
        private int age;
        private int certificateType;
        private List<String> orgList;
        private List<PersonPhotoBean> personPhoto;

        public int getMarriaged() {
            return marriaged;
        }

        public void setMarriaged(int marriaged) {
            this.marriaged = marriaged;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getOrgPath() {
            return orgPath;
        }

        public void setOrgPath(String orgPath) {
            this.orgPath = orgPath;
        }

        public int getSyncFlag() {
            return syncFlag;
        }

        public void setSyncFlag(int syncFlag) {
            this.syncFlag = syncFlag;
        }

        public String getOrgPathName() {
            return orgPathName;
        }

        public void setOrgPathName(String orgPathName) {
            this.orgPathName = orgPathName;
        }

        public String getOrgIndexCode() {
            return orgIndexCode;
        }

        public void setOrgIndexCode(String orgIndexCode) {
            this.orgIndexCode = orgIndexCode;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getCertificateNo() {
            return certificateNo;
        }

        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getRoomNum() {
            return roomNum;
        }

        public void setRoomNum(String roomNum) {
            this.roomNum = roomNum;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getJobNo() {
            return jobNo;
        }

        public void setJobNo(String jobNo) {
            this.jobNo = jobNo;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public int getLodge() {
            return lodge;
        }

        public void setLodge(int lodge) {
            this.lodge = lodge;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getCertificateType() {
            return certificateType;
        }

        public void setCertificateType(int certificateType) {
            this.certificateType = certificateType;
        }

        public List<String> getOrgList() {
            return orgList;
        }

        public void setOrgList(List<String> orgList) {
            this.orgList = orgList;
        }

        public List<PersonPhotoBean> getPersonPhoto() {
            return personPhoto;
        }

        public void setPersonPhoto(List<PersonPhotoBean> personPhoto) {
            this.personPhoto = personPhoto;
        }

        public static class PersonPhotoBean {
            /**
             * personPhotoIndexCode : f182e3c0-0eaf-49a7-8590-f8d0f8a59be5
             * picUri : /pic?9ddc663bf-5do7b1l*46ed61i-z0e*3s0337781115m9ep=t=i9p*i=d1s*i=d3b*i7dce*8428fcd6a-b1=74a-40o11cpi0c1d=41ie8=
             * serverIndexCode : 7ef23451-2765-48a7-bdb5-dbfb118f0061
             */

            private String personPhotoIndexCode;
            private String picUri;
            private String serverIndexCode;

            public String getPersonPhotoIndexCode() {
                return personPhotoIndexCode;
            }

            public void setPersonPhotoIndexCode(String personPhotoIndexCode) {
                this.personPhotoIndexCode = personPhotoIndexCode;
            }

            public String getPicUri() {
                return picUri;
            }

            public void setPicUri(String picUri) {
                this.picUri = picUri;
            }

            public String getServerIndexCode() {
                return serverIndexCode;
            }

            public void setServerIndexCode(String serverIndexCode) {
                this.serverIndexCode = serverIndexCode;
            }
        }
    }
}
