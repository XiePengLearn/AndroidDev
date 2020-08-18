package com.xiaoanjujia.home.entities;


import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class TypeOfRoleResponse implements Serializable {


    /**
     * status : 1
     * message : ok
     * data : {"ordinarydate":[{"id":1,"name":"当天","datetype":1},{"id":2,"name":"本周","datetype":2},{"id":3,"name":"本月","datetype":3},{"id":4,"name":"上月","datetype":4},{"id":5,"name":"近三月","datetype":5}],"ordinaryrole":[{"id":6,"name":"客服","status":0},{"id":7,"name":"巡查员","status":0},{"id":9,"name":"测试人员1","status":0}]}
     */

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
        private List<OrdinarydateBean> ordinarydate;
        private List<OrdinaryroleBean> ordinaryrole;

        public List<OrdinarydateBean> getOrdinarydate() {
            return ordinarydate;
        }

        public void setOrdinarydate(List<OrdinarydateBean> ordinarydate) {
            this.ordinarydate = ordinarydate;
        }

        public List<OrdinaryroleBean> getOrdinaryrole() {
            return ordinaryrole;
        }

        public void setOrdinaryrole(List<OrdinaryroleBean> ordinaryrole) {
            this.ordinaryrole = ordinaryrole;
        }

        public static class OrdinarydateBean {
            /**
             * id : 1
             * name : 当天
             * datetype : 1
             */

            private int id;
            private String name;
            private int datetype;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getDatetype() {
                return datetype;
            }

            public void setDatetype(int datetype) {
                this.datetype = datetype;
            }
        }

        public static class OrdinaryroleBean {
            /**
             * id : 6
             * name : 客服
             * status : 0
             */

            private int id;
            private String name;
            private int status;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
