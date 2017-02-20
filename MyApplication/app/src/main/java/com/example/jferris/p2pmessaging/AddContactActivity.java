package com.example.jferris.p2pmessaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Activity to add contacts
 * User can search available users by pressing search button
 * Will display list of matching users to add to contacts
 */
public class AddContactActivity extends AppCompatActivity {
    Button searchButton;
    EditText searchText;
    ListView addList;
    ContactController contactController;
    String keyword;
    ArrayList<User> users = new ArrayList<>();
    AddAdapter addAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        addList = (ListView) findViewById(R.id.addList);

        addAdapter = new AddAdapter(this, users);
        addList.setAdapter(addAdapter);

        contactController = ContactController.getInstance();
        UserController.getUsers();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    users.clear();
                    keyword = searchText.getText().toString();
                    for (User u : contactController.searchContacts(keyword)) {
                        users.add(u);
                    }
                    searchText.setText("");
                    addAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(AddContactActivity.this, "Cannot find any users", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
