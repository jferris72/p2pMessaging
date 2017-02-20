package com.example.jferris.p2pmessaging;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

/**
 * Controller for contacts
 * Has methods to fill, add and search contacts
 */

public class ContactController {
    private ArrayList<User> contacts = new ArrayList<>();
    DatabaseReference mDatabase;
    private static ContactController instance = null;


    protected ContactController() {
    }

    public static ContactController getInstance() {
        if (instance == null) {
            instance = new ContactController();
        }
        return instance;
    }

    public ArrayList<User> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<User> contacts) {
        this.contacts = contacts;
    }

    /**
     * Gets all users contacts from database and adds them to contacts ArrayList
     * First clears list then fills
     * @param contactAdapter
     */
    public void fillContacts(final ContactAdapter contactAdapter) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("contact").child(UserController.getCurrentUser().getUuid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                contacts.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    User contact = child.getValue(User.class);
                    contacts.add(contact);
                }
                contactAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Failed to fill
            }
        });

    }

    /**
     * Adds a contact by users UUID
     * @param contact
     */
    public void addById(String contact) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("user").child(contact).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    User user = null;
                    user = snapshot.getValue(User.class);
                    if(user != null) {
                        contacts.add(user);
                        mDatabase.child("contact").child(UserController.getCurrentUser().getUuid()).child(user.getUuid()).setValue(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //failed to add
            }
        });

    }

    /**
     * Searches users using a string keyword
     * Returns all those that match the keyword
     * @param keyword
     * @return
     */
    public ArrayList<User> searchContacts(String keyword) {
        ArrayList<User> users;
        ArrayList<User> matchedUsers;
        String lowerCaseName;
        keyword = keyword.toLowerCase();
        Pattern p;
        matchedUsers = new ArrayList<>();

        try {
            users = UserController.getUsers();
            p = Pattern.compile(keyword);
            for (User u : users) {
                lowerCaseName = u.getName().toLowerCase();
                if (p.matcher(lowerCaseName).find()) {
                    matchedUsers.add(u);
                }
            }
        } catch(Exception e) {
            return null;
        }

        return matchedUsers;
    }
}
