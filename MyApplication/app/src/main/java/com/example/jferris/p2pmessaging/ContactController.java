package com.example.jferris.p2pmessaging;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jferris on 11/02/17.
 */

public class ContactController {
    private ArrayList<User> contacts;
    DatabaseReference mDatabase;


    public ContactController() {
    }

    public ArrayList<User> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<User> contacts) {
        this.contacts = contacts;
    }

    public void addContact(User contact) {
        mDatabase.child("contact").child(UserController.getCurrentUser().getUuid()).setValue(contact);
        contacts.add(contact);
    }

    public void fillContacts() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("contact").child(UserController.getCurrentUser().getUuid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(UserController.getCurrentUser().getUuid())) {
                    User contact = snapshot.getValue(User.class);
                    contacts.add(contact);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }

}
