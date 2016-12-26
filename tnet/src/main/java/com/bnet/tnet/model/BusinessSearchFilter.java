package com.bnet.tnet.model;

import com.bnet.shared.model.entities.Business;

public class BusinessSearchFilter extends SearchFilter<Business> {

    @Override
    public boolean search(Business item) {
        if (item.getAddress().toString().toLowerCase().contains(searchText))
            return true;
        if (item.getEmail().toLowerCase().contains(searchText))
            return true;
        if (item.getLinkToWebsite().toLowerCase().contains(searchText))
            return true;
        if (item.getPhone().toLowerCase().contains(searchText))
            return true;
        if (item.getName().toLowerCase().contains(searchText))
            return true;
        if (Integer.toString(item.getId()).contains(searchText))
            return true;
        return false;
    }
}
