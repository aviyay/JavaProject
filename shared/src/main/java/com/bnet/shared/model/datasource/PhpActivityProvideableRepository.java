package com.bnet.shared.model.datasource;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.bnet.shared.model.ContentValuesConverter;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Activity;
import com.bnet.shared.model.entities.ActivityType;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;
import com.bnet.shared.model.entities.DateTime;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by שי on 22/12/2016.
 */

public class PhpActivityProvideableRepository implements ProvidableRepository<Activity> {
    final String WEB_URL="http://tennenba.vlab.jct.ac.il/";
    @Override
    public int addAndReturnAssignedId(Activity item) {
        ContentValues bis= ContentValuesConverter.activityToContentValues(item);
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("description", bis.getAsString("description"));
            params.put("price", bis.getAsString("price"));
            params.put("country", bis.getAsString("country"));
            params.put("start", bis.getAsString("start"));
            params.put("end", bis.getAsString("end"));
            params.put("type", bis.getAsString("type"));
            params.put("business_id", bis.getAsString("business_id"));
            String results = POST(WEB_URL + "activity_add.php", params);
            if(results.equals("")){
                throw new Exception("An error occurred on the server's side");
            }
            if (results.substring(0, 5).equalsIgnoreCase("error")) {
                throw new Exception(results.substring(5));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return -1;
    }

    @Override
    public List<Activity> getAll() {
        return getList(WEB_URL+"activity_getAll.php");
    }

    @NonNull
    private List<Activity> getList(String link) {
        JSONArray array;
        JSONObject obj;
        List<Activity> list=new ArrayList<Activity>();
        Activity temp;
        try {
            array =new JSONObject(GET(link)).getJSONArray("activities");
            for (int i=0;i<array.length();i++) {
                obj=array.getJSONObject(i);
                temp=new Activity();
                temp.setId(obj.getInt("activity_id"));
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

        return !getAllNews().isEmpty();
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException();
    }
    private static String GET(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            return response.toString();
        } else {
            return "";
        }
    }
    private static String POST(String url, Map<String,Object> params) throws
            IOException {
        //Convert Map<String,Object> into key=value&key=value pairs.
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()),"UTF-8"));
        }
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        // For POST only - START
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(postData.toString().getBytes("UTF-8"));
        os.flush();
        os.close();
        // For POST only - END
        int responseCode = con.getResponseCode();
        System.out.println("POST Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        else return "";
    }
}
