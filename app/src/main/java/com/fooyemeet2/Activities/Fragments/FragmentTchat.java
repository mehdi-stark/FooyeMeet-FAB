package com.fooyemeet2.Activities.Fragments;

import android.app.Notification;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import com.fooyemeet2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.faid on 16/06/2016.
 */

public class FragmentTchat extends Fragment {

    private static final String TAG = "MainActivity";

    private EditText msgContentEditText;
    private Button sendMsgButton;
    private RecyclerView msgsRecyclerView;

    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://xxx.xx.xx.xxx:3000");
            Log.v(TAG, "Fine!");
        } catch (URISyntaxException e) {
            Log.v(TAG, "Error Connecting to IP!" + e.getMessage());
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    Log.d(username, message);

                }
            })
            ;}
    };
    /*
    private void addMessage(String username, String message) {
        mMessages.add(new Notification.MessagingStyle.Message.Builder(NotificationCompat.MessagingStyle.Message.TYPE_MESSAGE).username(username).message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        mMessages.add(new Message.)
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSocket.on("new message", onNewMessage);
        mSocket.connect();

        return inflater.inflate(R.layout.tchat, container, false);
    }
}
