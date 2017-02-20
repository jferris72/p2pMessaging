package com.example.jferris.p2pmessaging;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter to display current contacts
 * Used in @ContactActivity
 * Displays contact name and email
 */

public class ContactAdapter extends ArrayAdapter<User> {

    private Context context;
    private ArrayList<User> contacts = new ArrayList<>();


    public ContactAdapter(Context context, ArrayList<User> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, viewGroup, false);
        }
        TextView contactName = (TextView) convertView.findViewById(R.id.contactName);
        TextView contactEmail = (TextView) convertView.findViewById(R.id.contactEmail);

        contactName.setText(contacts.get(position).getName());
        contactEmail.setText(contacts.get(position).getEmail());

        return convertView;
    }

    public ArrayList<User> getContacts() {
        return contacts;
    }
}
