package com.example.cs_321_team_project;

import java.util.ArrayList;
import java.util.List;
public class MediaSearch {
    public static List<String>search(List<String>list,String given){
        List<String>dr=new ArrayList<>();
        for(String ep:list){
            if (ep.toLowerCase().contains(given.toLowerCase())){
                dr.add(ep);
            }
        }
        return dr;
    }

}
