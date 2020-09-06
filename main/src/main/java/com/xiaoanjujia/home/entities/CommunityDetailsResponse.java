package com.xiaoanjujia.home.entities;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: xp
 * @Description:
 */
public class CommunityDetailsResponse  implements Serializable {


    /**
     * status : 1
     * message : OK
     * data : {"id":5,"community_id":3,"title":"啊啊啊1111骚点的","top_imgs":["https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg"],"region":"北京市","detailed_address":"朝阳区1022","advertisement_img":"https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","advertisement_url":"https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","title_text":"啊啊啊是的撒多所多多","contacts_phone":"18635805566","name_acronym":"小安","shop_name":"小安居家","shop_phone":"1863805566","shop_order_count":12}
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
        /**
         * id : 5
         * community_id : 3
         * title : 啊啊啊1111骚点的
         * top_imgs : ["https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg","https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg"]
         * region : 北京市
         * detailed_address : 朝阳区1022
         * advertisement_img : https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg
         * advertisement_url : https://a.xiaoanjujia.com/uploads/images/2020/07-27/685fac350b8cf99251ffe459f24fad24.jpg
         * title_text : 啊啊啊是的撒多所多多
         * contacts_phone : 18635805566
         * name_acronym : 小安
         * shop_name : 小安居家
         * shop_phone : 1863805566
         * shop_order_count : 12
         */

        private int id;
        private int community_id;
        private String title;
        private String region;
        private String detailed_address;
        private String advertisement_img;
        private String advertisement_url;
        private String title_text;
        private String contacts_phone;
        private String name_acronym;
        private String shop_name;
        private String shop_phone;
        private String category_name;
        private String grade;
        private int shop_order_count;
        private List<String> top_imgs;

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDetailed_address() {
            return detailed_address;
        }

        public void setDetailed_address(String detailed_address) {
            this.detailed_address = detailed_address;
        }

        public String getAdvertisement_img() {
            return advertisement_img;
        }

        public void setAdvertisement_img(String advertisement_img) {
            this.advertisement_img = advertisement_img;
        }

        public String getAdvertisement_url() {
            return advertisement_url;
        }

        public void setAdvertisement_url(String advertisement_url) {
            this.advertisement_url = advertisement_url;
        }

        public String getTitle_text() {
            return title_text;
        }

        public void setTitle_text(String title_text) {
            this.title_text = title_text;
        }

        public String getContacts_phone() {
            return contacts_phone;
        }

        public void setContacts_phone(String contacts_phone) {
            this.contacts_phone = contacts_phone;
        }

        public String getName_acronym() {
            return name_acronym;
        }

        public void setName_acronym(String name_acronym) {
            this.name_acronym = name_acronym;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_phone() {
            return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
            this.shop_phone = shop_phone;
        }

        public int getShop_order_count() {
            return shop_order_count;
        }

        public void setShop_order_count(int shop_order_count) {
            this.shop_order_count = shop_order_count;
        }

        public List<String> getTop_imgs() {
            return top_imgs;
        }

        public void setTop_imgs(List<String> top_imgs) {
            this.top_imgs = top_imgs;
        }
    }
}
