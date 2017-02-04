package com.bnet.data.model.entities;

public class Account {
    private String username;
    private String password;

    public Account() {}
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Get Username
     * @return String of the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Set Username
     * @param username The string to put in the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get Password
     * @return String of the Password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Set Password
     * @param password The string to put in the username
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
