package com.example.jferris.p2pmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Displays users contacts
 * User can click on a contact to begin messaging them @MessagingActivity
 * Can press the search button to be directed to @AddContactActivity
 */

public class ContactActivity extends Activity {
    Button contactButton;
    ListView contactList;
    ContactController contactController;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactController = ContactController.getInstance();
        contactList = (ListView) findViewById(R.id.contactList);
        contactAdapter = new ContactAdapter(this, contactController.getContacts());
        contactList.setAdapter(contactAdapter);

        contactButton = (Button) findViewById(R.id.contactButton);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this,AddContactActivity.class);
                startActivity(intent);
            }
        });

        contactList.setEnabled(true);
        contactList.setOnItemClickListener(new ContactClickHandler());


    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        contactController.fillContacts(contactAdapter);
    }

    public class ContactClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(ContactActivity.this, MessagingActivity.class);
            String uuid = contactAdapter.getContacts().get(position).getUuid();
            intent.putExtra("user", uuid);
            startActivity(intent);
        }
    }
}
