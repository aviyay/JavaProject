package com.bnet.shared.model.entities;

public class Address {
    private String country = "";
    private String city = "";
    private String street = "";

    public Address() {
    }

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }
    /**
     * Get Country
     * @return The country in the address
     */
    public String getCountry() {
        return country;
    }
    /**
     * Set Country
     * @param country The country to be set
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * Get City
     * @return The city in the address
     */
    public String getCity() {
        return city;
    }
    /**
     * Set City
     * @param city The city to be set
     */
    public void setCity(String city) {
        this.city = city;
    }
    /**
     * Get Street
     * @return The street in the address
     */
    public String getStreet() {
        return street;
    }
    /**
     * Set Street
     * @param street The street to be set
     */
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

    @Override
    public String toString() {
        return getStreet() + " ," + getCity() + " ," + getCountry();
    }
}
