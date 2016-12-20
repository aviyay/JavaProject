package com.bnet.shared.model.entities;

public class Address {
    private String country = "";
    private String city = "";
    private String street = "";

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }
    public Address()
    {

    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return getCountry().equals(address.getCountry())
                && getCity().equals(address.getCity())
                && getStreet().equals(address.getStreet());

    }

    @Override
    public int hashCode() {
        int result = getCountry().hashCode();
        result = 31 * result + getCity().hashCode();
        result = 31 * result + getStreet().hashCode();
        return result;
    }
}
