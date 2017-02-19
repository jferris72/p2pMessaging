package com.example.jferris.p2pmessaging;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jferris on 08/02/17.
 * Object for storing message string, connected to users UUID
 * Stores to uuid and from uuid
 * Boolean to tell if read or not
 * Date object to know when it was sent
 * Implements comparable to sort by date
 */

public class Message implements Comparable<Message> {
    private String message;
    private String uuid;
    private Boolean isRead;
    private Date date;
    private String from;
    private String to;

    public Message(String message, String to, String from, String uuid) {
        this.message = message;
        this.uuid = uuid;
        this.isRead = false;
        this.date = new Date();
        this.to = to;
        this.from = from;
    }

    public Message() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean isRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int compareTo(Message o) {
        return getDate().compareTo(o.getDate());
    }
}
