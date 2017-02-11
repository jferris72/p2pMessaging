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
 * Created by jferris on 11/02/17.
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
        //show message on left or right, depending on if
        //it's incoming or outgoing
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, viewGroup, false);
        }
        TextView contact = (TextView) convertView.findViewById(R.id.contact);

        contact.setText(contacts.get(position).getName());

//        messageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        return convertView;
    }

    public ArrayList<User> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<User> contacts) {
        this.contacts = contacts;
    }
}
