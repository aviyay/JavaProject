package com.bnet.shared.model.entities;

import com.bnet.shared.model.backend.Providable;

public class Business implements Providable {
    private long id = -1;
    private String name = "";
    private Address address = new Address();
    private String phone = "";
    private String email = "";
    private String linkToWebsite = "";

    public Business() {
    }

    public Business(String name, Address address, String phone, String email, String linkToWebsite) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.linkToWebsite = linkToWebsite;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
    /**
     * Get Name
     * @return The name of the business
     */
    public String getName() {
        return name;
    }
    /**
     * Set Name
     * @param name The Name to be set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get Address
     * @return The address of the business
     */
    public Address getAddress() {
        return address;
    }
    /**
     * Set Address
     * @param address The Address to be set
     */
    public void setAddress(Address address) {
        this.address = address;
    }
    /**
     * Get Phone
     * @return The phone of the business
     */
    public String getPhone() {
        return phone;
    }
    /**
     * Set Phone
     * @param phone The Phone to be set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * Get Email
     * @return The email of the business
     */
    public String getEmail() {
        return email;
    }
    /**
     * Set Email
     * @param email The Email to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Get Link to the business website
     * @return The link to the business website
     */
    public String getLinkToWebsite() {
        return linkToWebsite;
    }
    /**
     * Set Website link
     * @param linkToWebsite The Website link to be set
     */
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
