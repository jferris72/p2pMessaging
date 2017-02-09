package com.example.jferris.p2pmessaging;

import java.util.UUID;

/**
 * Created by jferris on 08/02/17.
 */

public class User {
    private String name;
    private UUID uuid;

    public User (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
