package com.bnet.data.model.datasource;

import com.bnet.data.model.backend.AccountsRepository;
import com.bnet.data.model.entities.Account;
import com.bnet.shared.model.PhpHelper;

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
        List<Account> list=getList(WEB_URL+"account_get.php?username="+username);
        if(list.isEmpty())
            return null;
        return list.get(0);
    }
    private List<Account> getList(String link) {
        JSONArray array;
        JSONObject obj;
        List<Account> list=new ArrayList<Account>();
        Account temp;
        try {
            String result= PhpHelper.GET(link);
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
    @Override
    public List<Account> getAll() {
      return getList(WEB_URL+"account_getAll.php");
    }
}
