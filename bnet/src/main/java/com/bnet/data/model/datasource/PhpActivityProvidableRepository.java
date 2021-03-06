package com.bnet.data.model.datasource;

import android.content.ContentValues;

import com.bnet.data.model.PhpHelper;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.DateTime;
import com.bnet.shared.model.services.utils.ProvidableUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PhpActivityProvidableRepository implements ProvidableRepository<Activity> {
    private final String WEB_URL="http://tennenba.vlab.jct.ac.il/";

    @Override
    public long addAndReturnAssignedId(Activity item) {
        ContentValues bis= ProvidableUtils.contentValuesConvert(item);
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("description", bis.getAsString("description"));
            params.put("price", bis.getAsString("price"));
            params.put("country", bis.getAsString("country"));
            params.put("start", bis.getAsString("start"));
            params.put("end", bis.getAsString("end"));
            params.put("type", bis.getAsString("type"));
            params.put("business_id", bis.getAsString("business_id"));
            String results = PhpHelper.POST(WEB_URL + "activity_add.php", params);
            if(results.equals("")){
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length()>5)
                if(results.substring(0, 5).equalsIgnoreCase("error")) {
                    throw new Exception(results.substring(5));
                }
            return Long.parseLong(results);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public List<Activity> getAll() {
        return getList(WEB_URL+"activity_getAll.php");
    }

    /**
     * Get a list of the activities returned from the Get request to the link
     * @param link The link of the server to send the request to
     * @return List of the activities from the server
     */
    private List<Activity> getList(String link) {
        JSONArray array;
        JSONObject obj;
        List<Activity> list=new ArrayList<>();
        Activity temp;
        try {
            String result= PhpHelper.GET(link);
            if(result.equals("0 results"))
                return new ArrayList<>();
            array =new JSONObject(result).getJSONArray("activities");
            for (int i=0;i<array.length();i++) {
                obj=array.getJSONObject(i);
                temp=new Activity();
                temp.setId(obj.getInt("_id"));
                temp.setDescription(obj.getString("description"));
                temp.setCountry(obj.getString("country"));
                temp.setPrice(obj.getDouble("price"));
                temp.setStart(DateTime.parse(obj.getString("start")));
                temp.setEnd(DateTime.parse(obj.getString("end")));
                temp.setType(ActivityType.valueOf(obj.getString("type")));
                temp.setBusinessId(obj.getInt("business_id"));
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Activity> getAllNews() {
        return getList(WEB_URL+"activity_getNews.php");
    }

    @Override
    public boolean isSomethingNew() {

        try {
            String result= PhpHelper.GET(WEB_URL+"activity_checkNew.php");
            return Boolean.parseBoolean(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void reset() {
    }

    @Override
    public Activity getOrNull(long id) {
        List<Activity> list=getList(WEB_URL+"activity_get.php?_id="+id);
        if(list.isEmpty())
            return null;
        return list.get(0);
    }
}
