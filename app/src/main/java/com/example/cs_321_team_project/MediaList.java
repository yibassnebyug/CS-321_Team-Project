package com.example.cs_321_team_project;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class MediaList {
    private JSONObject jsonObject = new JSONObject();
    private ArrayList<String> keys = new ArrayList<String>();

    // Constructor
    public MediaList() {
        //jsonObject  = new JSONObject();
    }
    public MediaList(String genre, String name) {
        toJSON(genre, name);
    }

    public MediaList(String genre, String name, String status) {
        // to be implemented
    }

    // Getters and setters
    /*public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }*/

    public void toJSON(String genre, String name) {
        if(jsonObject.has(genre)) { // if genre exists
            JSONArray jsonArray;

            try {
                jsonArray = jsonObject.getJSONArray(genre);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            // checks if inputted media is found under the genre
            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject o = jsonArray.getJSONObject(i);
                    if(o.getString("name").equals(name)) { return; }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            JSONObject newMedia = new JSONObject(); // creates a jsonObject for the new media

            try {
                newMedia.put("name", name);
                newMedia.put("status", "-");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(newMedia);
        }
        else if(jsonObject.isNull(genre)) { // if genre does not exist
            JSONObject newMedia = new JSONObject(); // creates a jsonObject for the new media

            try {
                newMedia.put("name", name);
                newMedia.put("status", "-");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray(); // creates a jsonArray to store media
            jsonArray.put(newMedia); // adds new media into the jsonArray

            try {
                jsonObject.put(genre, jsonArray); // links the array of media to a specific genre
                keys.add(genre);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<ArrayList<String>> fromJSON() {
        //JSONArray jsonArray = jsonObject.getJSONArray(genre);
        //return jsonObject;
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        for(String s : keys) {
            JSONArray array = jsonObject.optJSONArray(s);
            ArrayList<String> temp = new ArrayList<String>();
            for(int i = 0; i < array.length(); i++) {
                temp.add(array.optString(i));
            }
            list.add(temp);
        }

        return list;
    }

    public void clear() {
        // clear JSON object and keys
    }
}
