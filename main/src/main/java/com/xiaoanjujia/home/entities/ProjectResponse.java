package com.xiaoanjujia.home.entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class ProjectResponse implements Serializable {


    public static boolean isJsonObjectData(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            Object object = jsonObject.opt("data");
            if (object instanceof JSONObject) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean isJsonArrayData(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            Object object = jsonObject.opt("data");
            if (object instanceof JSONArray) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static String getMessage(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject.optString("message");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static int getStatus(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            return jsonObject.optInt("status");
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
