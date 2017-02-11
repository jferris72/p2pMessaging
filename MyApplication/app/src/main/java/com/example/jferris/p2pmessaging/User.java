package com.example.jferris.p2pmessaging;

/**
 * Created by jferris on 08/02/17.
 */

public class User {
    private String name;
    private String uuid;

    public User () {
    }

    public User (String name, String UUID) {
        this.name = name;
        this.uuid = UUID;
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
}
