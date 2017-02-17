package com.example.jferris.p2pmessaging;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by jferris on 09/02/17.
 */

public class MessageListener implements ChildEventListener {
    private MessageAdapter adapter;

    public MessageListener (MessageAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        //Receive message
        try {
            for(DataSnapshot child: dataSnapshot.getChildren()) {
                adapter.add(child.getValue(Message.class));
            }
        } catch(Exception e) {
            //do nothing
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //Message edited, probably is read

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        //Remove message
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        //Do nothing
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        //Catch failed read from database
    }
}
