package com.example.jferris.p2pmessaging;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


/**
 * Messaging activity, can send and receive messages
 */
public class MessagingActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;
    private static final int SELECT_PHOTO = 2;
    MessageAdapter messageAdapter;
    TextView contactName;
    EditText body;
    ListView messageList;
    Button submitButton;
    Button imageButton;
    MessageController messageController = MessageController.getInstance();
    String contactUUID;
    String messageBody = null;
    FirebaseStorage storage;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        contactName = (TextView) findViewById(R.id.contactName);
        body = (EditText) findViewById(R.id.body);
        messageList = (ListView) findViewById(R.id.messageList);
        submitButton = (Button) findViewById(R.id.submitButton);
        imageButton = (Button) findViewById(R.id.imageButton);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

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

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission();
            }
        });

        messageAdapter = new MessageAdapter(this, MessageController.getMessageList());
        MessageController.receiveMessages(contactUUID, UserController.getCurrentUser().getUuid(), messageAdapter);

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

    /**
     * Takes result from gallery after image button pressed
     * Sends image to storage on firebase and message to contact with image info
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            final String picturePath = cursor.getString(columnIndex);
            cursor.close();

            StorageReference imageRef = storageRef.child(picturePath);
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] temp = baos.toByteArray();

            UploadTask uploadTask = imageRef.putBytes(temp);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    messageController.sendImage(picturePath, UserController.getCurrentUser().getUuid(), contactUUID);
                }
            });

        }
    }

    /**
     * Get permission to access images, taken from google android dev website
     */
    protected void getPermission() {
        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(MessagingActivity.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(MessagingActivity.this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                } else {

                    ActivityCompat.requestPermissions(MessagingActivity.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                }
            } else{
                ActivityCompat.requestPermissions(MessagingActivity.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        } else {

            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
        }
    }

    /**
     * On permission accepted go to pick a picture to send
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            } else {

            }
            return;
        }
    }
}
