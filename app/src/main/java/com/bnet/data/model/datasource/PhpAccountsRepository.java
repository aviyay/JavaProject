package com.bnet.data.model.datasource;

import android.content.ContentValues;

import com.bnet.data.model.backend.AccountsRepository;
import com.bnet.data.model.entities.Account;
import com.bnet.shared.model.ContentValuesConverter;
import com.bnet.shared.model.PhpHelper;
import com.bnet.shared.model.entities.Address;
import com.bnet.shared.model.entities.Business;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shyte on 22/12/2016.
 */

public class PhpAccountsRepository  implements AccountsRepository{
    private static final String WEB_URL ="http://tennenba.vlab.jct.ac.il/" ;

    @Override
    public void add(Account account) {
        try {
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("username", account.getUsername());
            params.put("password", account.getPassword());
            String results = PhpHelper.POST(WEB_URL + "account_add.php", params);
            if(results.equals("")){
                throw new Exception("An error occurred on the server's side");
            }
            if (results.length()>5)
                if(results.substring(0, 5).equalsIgnoreCase("error")) {
                    throw new Exception(results.substring(5));
                }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @Override
    public Account getOrNull(String username) {
        for(Account act:getAll())
        {
            if(act.getUsername().equals(username))
                return act;
        }
        return null;
    }

    @Override
    public List<Account> getAll() {
        JSONArray array;
        JSONObject obj;
        List<Account> list=new ArrayList<Account>();
        Account temp;
        try {
            String result= PhpHelper.GET(WEB_URL+"account_getAll.php");
            if(result.equals("0 results"))
                return new ArrayList<Account>();
            array =new JSONObject(result).getJSONArray("accounts");
            for (int i=0;i<array.length();i++) {
                obj=array.getJSONObject(i);
                temp=new Account();
                temp.setUsername(obj.getString("username"));
                temp.setPassword(obj.getString("password"));
            list.add(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
