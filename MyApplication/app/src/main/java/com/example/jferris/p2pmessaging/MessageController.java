package com.example.jferris.p2pmessaging;

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

//    public MessageController() {}

    /**
     *
     * @param body
     * @param from
     * @param to
     */
    public void sendMessage(String body, String from, String to) {

        if (body == null || from == null || to == null) {
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Message message = new Message(body, to, from);
        messageID = UUID.randomUUID();
        mDatabase.child("message").child(from).child(to).child(messageID.toString()).setValue(message);
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

    public static void getMessages(String toUUID, String fromUUID, final MessageAdapter messageAdapter) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("message").child(fromUUID).child(toUUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    messageList.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Message message = child.getValue(Message.class);
                        messageList.add(message);
                    }
                    messageAdapter.notifyDataSetChanged();
                    Collections.sort(messageList);
                } catch(Exception e) {
                    Log.i("getMessagesException:", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public static ArrayList<Message> getMessageList() {
        return messageList;
    }

    public static void setMessageList(ArrayList<Message> messageList) {
        MessageController.messageList = messageList;
    }

    public static void sortMessageList() {

    }

}
