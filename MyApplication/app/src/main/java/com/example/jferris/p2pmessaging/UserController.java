package com.example.jferris.p2pmessaging;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Controls user data
 * Has methods to create user on DB and get users from DB
 * Contains reference to current logged in user
 * Singleton, only one global controller for accessing current user across activities
 */

public class UserController {
    private static UserController instance = null;
    private static User currentUser = null;
    private static DatabaseReference mDatabase;
    private static ArrayList<User> users = new ArrayList<>();


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

    /**
     * Create user, add to database if not in it already
     * @param name
     * @param uuid
     */
    public static void createUser(String name, String uuid, String email) {
        currentUser = new User(name, uuid, email);
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
            }
        });
    }

    /**
     * Gets all the users from the database and stores them in an array
     * @return
     */

    public static ArrayList<User> getUsers() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    users.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        User user = child.getValue(User.class);
                        users.add(user);
                    }
                } catch(Exception e) {
                    Log.i("getUsersException:", e.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return users;
    }
}
