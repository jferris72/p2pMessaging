package com.example.jferris.p2pmessaging;

import java.util.UUID;

/**
 * Created by jferris on 08/02/17.
 * Object for storing message string, connected to users UUID
 */

public class Message {
    private String message;
    private UUID uuid;

    public Message(String message, UUID uuid) {
        this.message = message;
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
