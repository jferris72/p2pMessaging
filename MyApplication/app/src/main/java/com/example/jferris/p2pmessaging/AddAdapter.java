package com.example.jferris.p2pmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter for showing contacts that the user can add
 * Displays user name and email
 * Has button to add contacts to user contact list
 */

public class AddAdapter extends ArrayAdapter<User> {


    private Context context;
    private ArrayList<User> contacts = new ArrayList<>();
    private ContactController contactController;


    public AddAdapter(Context context, ArrayList<User> contacts) {
        super(context, 0, contacts);
        this.context = context;
        this.contacts = contacts;
        contactController = new ContactController();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.add_list_item, viewGroup, false);
        }
        TextView contactName = (TextView) convertView.findViewById(R.id.addName);
        TextView contactEmail = (TextView) convertView.findViewById(R.id.addEmail);

        contactName.setText(contacts.get(position).getName());
        contactEmail.setText(contacts.get(position).getEmail());

        Button addContactButton = (Button) convertView.findViewById(R.id.addButton);

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactController.addById(contacts.get(position).getUuid());
            }
        });

        return convertView;
    }
}
