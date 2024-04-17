package com.example.cs_321_team_project;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.Parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;

public class MediaList {
    private static JSONObject json = new JSONObject();
    private final Context context;
    private final String path;

    // Constructor
    public MediaList(Context m) {
        context = m;
        path = context.getFilesDir().getAbsolutePath() + "/" + "storage.json";
    }

    public void toJSON(ArrayList<String> list) {
        File file = new File(path);
        try {
            JSONArray array = new JSONArray();
            for(String s : list) {
                JSONObject object = new JSONObject();
                String[] splitString = s.split("/");
                object.put("name", splitString[0]);
                object.put("genre", splitString[1]);
                object.put("status", splitString[2]);
                object.put("favorite", splitString[3]);
                array.put(object);
            }
            json.put("media", array);
            FileWriter writer = new FileWriter(file);
            writer.write(json.toString());
            writer.close();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> fromJSON() {
        File file = new File(path);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            String line = reader.readLine();

            while(line != null) {
                builder.append(line);
                builder.append("\n");
                line = reader.readLine();
            }

            String json = builder.toString();

            ArrayList<String> list = new ArrayList<String>();
            JSONObject jsonObject = new JSONObject(json);
            if(!(jsonObject.has("media"))) {
                return list;
            }
            JSONArray jsonArray = jsonObject.getJSONArray("media");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String name = object.getString("name");
                String genre = object.getString("genre");
                String status = object.getString("status");
                String favorite = object.getString("favorite");
                list.add(name + "/" + genre + "/" + status + "/" + favorite);
            }
            return list;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFilePresent() {
        File file = new File(path);
        return file.exists();
    }
}
