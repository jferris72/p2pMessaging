package com.example.jferris.p2pmessaging;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {
    Button searchButton;
    EditText searchText;
    ListView addList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        searchButton = (Button) findViewById(R.id.searchButton);
        searchText = (EditText) findViewById(R.id.searchText);
        addList = (ListView) findViewById(R.id.addList);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
