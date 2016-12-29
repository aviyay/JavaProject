package com.bnet.shared.model.entities;

import com.bnet.shared.model.backend.Providable;

public class Business implements Providable {
    private int id = -1;
    private String name = "";
    private Address address = new Address();
    private String phone = "";
    private String email = "";
    private String linkToWebsite = "";

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

        return getName().equals(business.getName())
                && getAddress().equals(business.getAddress())
                && getPhone().equals(business.getPhone())
                && getEmail().equals(business.getEmail())
                && getLinkToWebsite().equals(business.getLinkToWebsite());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getAddress().hashCode();
        result = 31 * result + getPhone().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getLinkToWebsite().hashCode();
        return result;
    }
}
