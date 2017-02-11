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

public class ContactActivity extends Activity {
    Button contactButton;
    ListView contactList;
    EditText contactText;
    ContactController contactController;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactController = new ContactController();
        contactList = (ListView) findViewById(R.id.contactList);
        contactController.fillContacts();
        contactAdapter = new ContactAdapter(this, contactController.getContacts());
        contactList.setAdapter(contactAdapter);


        contactButton = (Button) findViewById(R.id.contactButton);
        contactText = (EditText) findViewById(R.id.contactText);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactText.getText().toString();
                int check = 0; //if 1 already contact
                for (User user: contactController.getContacts()) {
                    if (user.getName().equals(contact)) {

                    }
                    if(user.getUuid().equals(contact)) {
                        check = 1;
                        Toast.makeText(ContactActivity.this, "Already a contact", Toast.LENGTH_SHORT).show();
                    }
                    if (check == 0){
                        contactController.addById(contact);
                        contactAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        contactList.setEnabled(true);
        contactList.setOnItemClickListener(new ContactClickHandler());

//        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                Intent intent = new Intent(ContactActivity.this, MessagingActivity.class);
//                String uuid = contactAdapter.getContacts().get(position).getUuid();
//                intent.putExtra("user", uuid);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

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
