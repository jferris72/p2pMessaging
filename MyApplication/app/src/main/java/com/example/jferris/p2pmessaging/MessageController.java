package com.example.jferris.p2pmessaging;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private DatabaseReference mDatabase;
    private ArrayList<File> fileList;
    protected MessageController () {
        //Used to create one shared instance of message controller
    }

    public static MessageController getInstance() {
        if(instance == null) {
            instance = new MessageController();
        }
        return instance;
    }

//    public MessageController() {}

    /**
     * Sends a message to firebase
     * @param message
     * @param user
     */
    public void sendMessage(Message message, UUID user) {

        if (message == null || user == null) {
            return;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        UUID messageID = UUID.randomUUID();
        message = new Message("placeholder string", messageID);
        mDatabase.child("message").child(user.toString()).child(messageID.toString()).setValue(message);
    }

    public Message receiveMessages(UUID user) {
        Message message = null;
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                // A new comment has been added, add it to the displayed list

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.


                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.child("message").child(user.toString()).addChildEventListener(childEventListener);
        return message;
    }

    public void addToFile(Message message) {

    }
}
