package com.example.jferris.p2pmessaging;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

/**
 * Created by jferris on 08/02/17.
 * Singleton so that only one instance can be made at any time
 * Used to send and receive messages
 */

public class MessageController {
    private static MessageController instance = null;
    private ArrayList<File> fileList;
    private static ArrayList<Message> messageList= new ArrayList<Message>();
    private UUID messageID;
    private static DatabaseReference mDatabase;

    protected MessageController () {
        //Used to create one shared instance of message controller
    }

    public static MessageController getInstance() {
        if(instance == null) {
            instance = new MessageController();
        }
        return instance;
    }

    public static DatabaseReference getDatabaseReference() {
        return mDatabase;
    }

//    public MessageController() {}

    /**
     * Send message to database to be received by contact
     * @param body
     * @param from
     * @param to
     */
    public void sendMessage(String body, String from, String to) {
        if (body == null || from == null || to == null) {
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        messageID = UUID.randomUUID();
        Message message = new Message(body, to, from, messageID.toString(), false);
        mDatabase.child("message").child(from).child(to).child(messageID.toString()).setValue(message);
    }

    public void sendImage(String imageLocation, String from, String to) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        messageID = UUID.randomUUID();
        Message message = new Message(imageLocation, to, from, messageID.toString(), true);
        mDatabase.child("message").child(from).child(to).child(messageID.toString()).setValue(message);
    }

    /**
     * Receive messages, makes a listener to get old messages and listen for new ones
     * Listens for changes to list to update read receipts
     * @param toUUID
     * @param fromUUID
     * @param messageAdapter
     */
    public static void receiveMessages(final String toUUID, final String fromUUID, final MessageAdapter messageAdapter) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        messageList.clear();
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Message newMessage = dataSnapshot.getValue(Message.class);
                messageList.add(newMessage);
                Collections.sort(messageList);
                if(messageAdapter != null) {
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

                Message newMessage = dataSnapshot.getValue(Message.class);
                for(int i = 0; i < messageList.size(); i++) {
                    if(messageList.get(i).getUuid().equals(newMessage.getUuid())) {
                        messageList.set(i, newMessage);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child("message").child(fromUUID).child(toUUID).addChildEventListener(childEventListener);
        mDatabase.child("message").child(toUUID).child(fromUUID).addChildEventListener(childEventListener);

    }

    public static ArrayList<Message> getMessageList() {
        return messageList;
    }

    public static void setMessageList(ArrayList<Message> messageList) {
        MessageController.messageList = messageList;
    }


}
