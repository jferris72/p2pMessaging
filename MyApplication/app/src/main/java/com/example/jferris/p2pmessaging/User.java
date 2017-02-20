package com.example.jferris.p2pmessaging;

/**
 * User object, contains:
 * String name of user
 * String unique ID of user
 * String email of user
 */

public class User {
    private String name;
    private String uuid;
    private String email;

    public User () {
    }

    public User (String name, String UUID, String email) {
        this.name = name;
        this.uuid = UUID;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
