package com.example.cs_321_team_project;

import org.json.JSONException;
import org.json.JSONObject;

public class MediaList {
    private String name;

    // Constructor
    public MediaList(String name) {
        this.name = name;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static MediaList fromJSON(JSONObject jsonObject) {
        try {
            String name = jsonObject.getString("name");
            return new MediaList(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
