package com.example.jferris.p2pmessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Adapter to display messages for @MessagingActivity
 * Checks if message is sent or received, sent messages appear or right
 * Received messages appear on left
 * Checks to see if messages received marked read, if not display and mark as read
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    Context context;
    ArrayList<Message> messageList;

    public MessageAdapter(Context context, ArrayList<Message> messageList) {
        super(context, 0, messageList);
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Message getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        //show message on left or right, depending on if
        //it's incoming or outgoing
        int res = 0;
        Message currMessage = messageList.get(position);
        if (currMessage.getTo().equals(UserController.getCurrentUser().getUuid())) {
            if(currMessage.isImage()) {
                res = R.layout.image_left;
            } else {
                res = R.layout.message_left;
            }
            if(!currMessage.isRead()) {
                currMessage.setRead(true);
                MessageController.getDatabaseReference().child("message").child(currMessage.getFrom())
                        .child(currMessage.getTo()).child(currMessage.getUuid()).setValue(currMessage);
            }
            convertView = LayoutInflater.from(getContext()).inflate(res, viewGroup, false);
        } else {
            if(currMessage.isImage()) {
                res = R.layout.image_right;
            } else {
                res = R.layout.message_right;
            }

            convertView = LayoutInflater.from(getContext()).inflate(res, viewGroup, false);
            TextView readReceipt = (TextView) convertView.findViewById(R.id.readReceipt);
            if(currMessage.isRead()) {
                readReceipt.setText("Read");
            } else {
                readReceipt.setText("Sent");
            }
        }
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(res, viewGroup, false);
//        }
        if(currMessage.isImage()) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageBody);
            StorageReference pathReference = storageRef.child(currMessage.getMessage());

            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(pathReference)
                    .into(imageView);
        } else {
            TextView body = (TextView) convertView.findViewById(R.id.messageBody);
            body.setText(messageList.get(position).getMessage());
        }


        return convertView;
    }
}
