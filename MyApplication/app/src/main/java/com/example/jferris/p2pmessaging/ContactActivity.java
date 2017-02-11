package com.example.jferris.p2pmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactActivity extends Activity {
    Button contactButton;
    EditText contactText;
    ContactController contactController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactButton = (Button) findViewById(R.id.contactButton);
        contactText = (EditText) findViewById(R.id.contactText);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactText.getText().toString();
                for (User user: contactController.getContacts()) {
                    if (user.getName().equals(contact)) {

                    }
                    if(user.getUuid().equals(contact)) {
                        //Do nothing
                        Toast.makeText(ContactActivity.this, "Already a contact", Toast.LENGTH_SHORT).show();
                    } else {
                        contactController.addById(contact);
                    }
                }
            }
        });

    }
}
