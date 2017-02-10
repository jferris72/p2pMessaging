package com.example.jferris.p2pmessaging;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jferris on 09/02/17.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;

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
        int direction = 1;
        //show message on left or right, depending on if
        //it's incoming or outgoing
        if (convertView == null) {
            int res = 0;
            if (direction == DIRECTION_INCOMING) {
                res = R.layout.message_right;
            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.message_left;
            }
        }
        return convertView;
    }
}
