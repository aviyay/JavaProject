package com.bnet.tnet.model;

import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.Business;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by שי on 23/12/2016.
 */

public class Searcher {
    public static boolean Search(Business item, String search)
    {
        String str=search.toLowerCase();
        if(item.getAddress().toString().toLowerCase().contains(str))
            return true;
        if(item.getEmail().toLowerCase().contains(str))
            return true;
        if(item.getLinkToWebsite().toLowerCase().contains(str))
            return true;
        if(item.getPhone().toLowerCase().contains(str))
            return true;
        if(item.getName().toLowerCase().contains(str))
            return true;
        if(Integer.toString(item.getId()).contains(str))
            return true;
        return false;
    }
    public static boolean Search(Activity item, String search)
    {
        String str=search.toLowerCase();
        if(Integer.toString(item.getBusinessId()).contains(str))
            return true;
        if(item.getCountry().toLowerCase().contains(str))
            return true;
        if(item.getDescription().toLowerCase().contains(str))
            return true;
        if(item.getEnd().toString().toLowerCase().contains(str))
            return true;
        if(item.getStart().toString().toLowerCase().contains(str))
            return true;
        if(item.getType().toString().toLowerCase().contains(str))
            return true;
        if(Double.toString(item.getPrice()).contains(str))
            return true;
        if(Integer.toString(item.getId()).contains(str))
            return true;
        return false;
    }
    public static ArrayList<Activity> FilterActivity(ArrayList<Activity> list, String str)
    {
        ArrayList<Activity> array=new ArrayList<>();
        for(Activity item: list)
            if(Search(item,str))
                array.add(item);
        return array;
    }
    public static ArrayList<Business> FilterBusiness(ArrayList<Business> list, String str)
    {
        ArrayList<Business> array=new ArrayList<>();
        for(Business item: list)
            if(Search(item,str))
                array.add(item);
        return array;
    }
}
