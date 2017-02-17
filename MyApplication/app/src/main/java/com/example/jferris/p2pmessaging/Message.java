package com.example.jferris.p2pmessaging;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jferris on 08/02/17.
 * Object for storing message string, connected to users UUID
 */

public class Message implements Comparable<Message> {
    private String message;
//    private UUID uuid;
    private Boolean isRead;
    private Date date;
    private String from;
    private String to;
//    private static final int DIRECTION_INCOMING = 0;
//    private static final int DIRECTION_OUTGOING = 1;
//    private int direction;

    public Message(String message, String to, String from) {
        this.message = message;
//        this.uuid = uuid;
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

//    public UUID getUuid() {
//        return uuid;
//    }
//
//    public void setUuid(UUID uuid) {
//        this.uuid = uuid;
//    }

    public Boolean getRead() {
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
//
//    public int getDirection() {
//        return direction;
//    }
//
//    public void setDirection(int direction) {
//        this.direction = direction;
//    }
//
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
