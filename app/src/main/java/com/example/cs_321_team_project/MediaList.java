package com.example.cs_321_team_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.view.Gravity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.io.File;

public class MediaList {
    private static JSONObject jsonObject = new JSONObject();
    private final Context context;

    // Constructor
    public MediaList(Context m){ context = m; }

    public void toJSON(ArrayList<String> list) {
        try {
            JSONArray array = new JSONArray();
            for(String s : list) {
                String[] splitString = s.split("/");
                JSONObject object = new JSONObject();
                object.put("name", splitString[0]);
                object.put("genre", splitString[1]);
                object.put("status", splitString[2]);
                array.put(object);
            }
            jsonObject.put("object", array);
            toFile();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> fromJSON() {
        try {
            fromFile();
            ArrayList<String> list = new ArrayList<String>();
            JSONArray array = jsonObject.getJSONArray("object");

            for(int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String name = object.getString("name");
                String genre = object.getString("genre");
                String status = object.getString("status");
                String formattedString = name + "/" + genre + "/" + status;
                list.add(formattedString);
            }

            return list;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void toFile() {
        try{

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fromFile() {
        String json = null;
        try {
            InputStream input = context.getAssets().open("storage.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            json = new String(buffer, "UTF-8");
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkFile(String fileName) {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public void clear() {
        // clear JSON object and keys
    }
}
