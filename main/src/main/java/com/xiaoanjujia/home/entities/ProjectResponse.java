package com.xiaoanjujia.home.entities;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class ProjectResponse implements Serializable {
    public int result;
    public String info;
    public Object data;

    public static ProjectResponse parser(String content, Class t) {
        ProjectResponse response = new ProjectResponse();
        try {
            JSONObject jsonObject = new JSONObject(content);
            response.result = jsonObject.optInt("status");
            response.info = jsonObject.optString("message");
            Object object = jsonObject.opt("data");
            if (object instanceof JSONObject) {
                String data = object.toString();
                response.data = JSON.parseObject(data, t);
            } else if (object instanceof JSONArray) {
                String data = object.toString();
                response.data = JSON.parseArray(data, t);
            } else {
                response.data = jsonObject.optString("data");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }
}
