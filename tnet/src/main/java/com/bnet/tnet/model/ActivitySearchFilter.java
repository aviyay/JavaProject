package com.bnet.tnet.model;

import com.bnet.shared.model.entities.Activity;

public class ActivitySearchFilter extends SearchFilter<Activity> {

    @Override
    public boolean search(Activity item) {
        if (item.getCountry().toLowerCase().contains(searchText))
            return true;
        if (item.getDescription().toLowerCase().contains(searchText))
            return true;
        if (item.getEnd().toString().toLowerCase().contains(searchText))
            return true;
        if (item.getStart().toString().toLowerCase().contains(searchText))
            return true;
        if (item.getType().toString().toLowerCase().contains(searchText))
            return true;
        if (Double.toString(item.getPrice()).contains(searchText))
            return true;

        return false;
    }
}
