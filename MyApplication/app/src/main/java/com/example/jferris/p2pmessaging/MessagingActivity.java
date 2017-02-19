package com.example.jferris.p2pmessaging;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

import static android.R.id.message;

public class MessagingActivity extends Activity {
    MessageAdapter messageAdapter;
    TextView contactName;
    EditText body;
    ListView messageList;
    Button submitButton;
    MessageController messageController = MessageController.getInstance();
    String contactUUID;
    String messageBody = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        contactName = (TextView) findViewById(R.id.contactName);
        body = (EditText) findViewById(R.id.body);
        messageList = (ListView) findViewById(R.id.messageList);
        submitButton = (Button) findViewById(R.id.submitButton);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                contactUUID = null;
            } else {
                contactUUID = extras.getString("user");
            }
        } else {
            contactUUID = (String) savedInstanceState.getSerializable("user");
        }
        //MessageController.getMessages(contactUUID, UserController.getCurrentUser().getUuid(), messageAdapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageBody = body.getText().toString();
                if (messageBody != null) {
                    messageController.sendMessage(messageBody, UserController.getCurrentUser().getUuid(), contactUUID);
                    body.setText("");
                }
            }
        });
        messageAdapter = new MessageAdapter(this, MessageController.getMessageList());
        MessageController.receiveMessages(contactUUID, UserController.getCurrentUser().getUuid(), messageAdapter);

        contactName.setText(UserController.getCurrentUser().getName()); //TO DO
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        messageList.setAdapter(messageAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
