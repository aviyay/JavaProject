package com.bnet.shared.model.datasource;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.bnet.shared.model.ContentValuesConverter;
import com.bnet.shared.model.backend.ProvidableRepository;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;

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

import static android.R.attr.id;

/**
 * Created by שי on 21/12/2016.
 */

public class PhpBusinessProvidableRepository implements ProvidableRepository<Business> {
  final String WEB_URL="http://tennenba.vlab.jct.ac.il/";
    @Override
    public int addAndReturnAssignedId(Business item) {
        ContentValues bis=ContentValuesConverter.businessToContentValues(item);
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("name", bis.getAsString("name"));
            params.put("country", bis.getAsString("country"));
            params.put("city", bis.getAsString("city"));
            params.put("street", bis.getAsString("street"));
            params.put("phone", bis.getAsString("phone"));
            params.put("email", bis.getAsString("email"));
            params.put("link", bis.getAsString("link"));
            String results = POST(WEB_URL + "business_add.php", params);
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
    public List<Business> getAll() {
        return getList(WEB_URL+"business_getAll.php");
    }

    @NonNull
    private List<Business> getList(String link) {
        JSONArray array;
        JSONObject obj;
        List<Business> list=new ArrayList<Business>();
        Business temp;
        try {
            array =new JSONObject(GET(link)).getJSONArray("business");
            for (int i=0;i<array.length();i++) {
                obj=array.getJSONObject(i);
                temp=new Business();
                temp.setId(obj.getInt("business_id"));
                temp.setName(obj.getString("name"));
                temp.setAddress(new Address(obj.getString("country"),obj.getString("city"),obj.getString("street")));
                temp.setPhone(obj.getString("phone"));
                temp.setEmail(obj.getString("email"));
                temp.setLinkToWebsite(obj.getString("link"));
                list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Business> getAllNews() {
        return getList(WEB_URL+"business_getNews.php");
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