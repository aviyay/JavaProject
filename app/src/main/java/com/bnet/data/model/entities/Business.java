package com.bnet.data.model.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.view.ContextThemeWrapper;

import com.bnet.data.model.ContentValuesConverter;
import com.bnet.data.model.backend.Providable;
import com.bnet.data.model.backend.ProvidableRepository;
import com.bnet.data.model.backend.RepositoriesFactory;

import java.text.ParseException;

public class Business implements Providable<Business> {
    private int id = -1;
    private String name;
    private Address address = new Address();
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
    public ProvidableRepository<Business> getRepository() {
        return RepositoriesFactory.getBusinessesRepository();
    }

    @Override
    public Business fromContentValues(ContentValues contentValues) {
        try {
            return ContentValuesConverter.contentValuesToBusiness(contentValues);
        } catch (Exception e) {
            throw new IllegalArgumentException("This contentValues is not a valid business");
        }
    }

    @Override
    public ContentValues toContentValues(Business item) {
        return ContentValuesConverter.businessToContentValues(item);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Business business = (Business) o;

        if (getId() != business.getId()) return false;
        if (!getName().equals(business.getName())) return false;
        if (!getAddress().equals(business.getAddress())) return false;
        if (!getPhone().equals(business.getPhone())) return false;
        if (!getEmail().equals(business.getEmail())) return false;
        return getLinkToWebsite().equals(business.getLinkToWebsite());

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getLinkToWebsite().hashCode();
        return result;
    }
}
