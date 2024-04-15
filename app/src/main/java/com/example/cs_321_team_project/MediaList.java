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
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path + "/storage.json");
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            output.writeObject(jsonObject);
            output.close();
        } catch (Exception e) {
            // something
        }
    }

    public void fromFile() {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            File file = new File(path + "/storage.json");
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            jsonObject = (JSONObject) input.readObject();
            input.close();
        } catch (Exception e) {

        }
    }

    public void clear() {
        // clear JSON object and keys
    }
}
