package com.example.jferris.p2pmessaging;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by jferris on 09/02/17.
 */

public class UserController {
    private static UserController instance = null;
    private static User currentUser = null;
    private static DatabaseReference mDatabase;
    private static ArrayList<User> contacts;


    protected UserController() {
        //create one shared instance of user controller
    }

    public UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public static User getCurrentUser() {
        if(currentUser == null) {
//            createUser();
        }
        return currentUser;
    }

    public static Boolean setCurrentUser(User user) {
        currentUser = user;
        return true;
    }

    public static ArrayList<User> getContacts() {
        return contacts;
    }

    public static void setContacts(ArrayList<User> contacts) {
        UserController.contacts = contacts;
    }

    /**
     * Create user, add to database if not in it already
     * @param name
     * @param uuid
     */
    public static void createUser(String name, String uuid) {
        currentUser = new User(name, uuid);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(currentUser.getUuid())) {
                    mDatabase.child("user").child(currentUser.getUuid()).setValue(currentUser);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }
}
