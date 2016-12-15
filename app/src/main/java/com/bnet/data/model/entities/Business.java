package com.bnet.data.model.entities;

import android.content.ContentValues;

import com.bnet.data.model.ContentValuesConverter;
import com.bnet.data.model.backend.Providable;
import com.bnet.data.model.backend.ProvidableRepository;
import com.bnet.data.model.backend.RepositoriesFactory;

public class Business implements Providable {
    private int id = -1;
    private String name;
    private Address address;
    private String phone;
    private String email;
    private String linkToWebsite;

    public Business(String name, Address address, String phone, String email, String linkToWebsite) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.linkToWebsite = linkToWebsite;
    }

    public Business() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getURIPath() {
        return "businesses";
    }

    @Override
    public ProvidableRepository getRepository() {
        return RepositoriesFactory.getBusinessesRepository();
    }

    @Override
    public Providable fromContentValues(ContentValues contentValues) {
        try {
            return ContentValuesConverter.contentValuesToBusiness(contentValues);
        } catch (Exception e) {
            throw new IllegalArgumentException("This contentValues is not a valid business");
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkToWebsite() {
        return linkToWebsite;
    }

    public void setLinkToWebsite(String linkToWebsite) {
        this.linkToWebsite = linkToWebsite;
    }
}
