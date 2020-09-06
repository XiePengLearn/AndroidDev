package com.xiaoanjujia.home.entities;

import java.io.Serializable;

public class PublishImageResponse  implements Serializable {

    private String TYPE;
    private String URI;

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
