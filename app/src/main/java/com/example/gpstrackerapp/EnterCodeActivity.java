package com.example.gpstrackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.goodiebag.pinview.Pinview;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import static com.example.gpstrackerapp.config.BackendConnection.CONNECTION_URL;

public class EnterCodeActivity extends AppCompatActivity {
    private Socket mSocket;
    Pinview mConnectionCode;
    Button mConnectChildButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        mConnectionCode = findViewById(R.id.pinView_code);
        mConnectChildButton = findViewById(R.id.button_connect);

        try {
            mSocket = IO.socket(CONNECTION_URL);
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mConnectChildButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inputCode = mConnectionCode.getValue();
                mSocket.emit("child wait", inputCode);
            }
        });

        mSocket.on("found", onFoundConnect);
    }

    private Emitter.Listener onFoundConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        String childID = object.getString("connect");
                        onBothPartiesConnect();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public void onBothPartiesConnect() {
        Intent intent = new Intent(this, ChildHomePageActivity.class);
        startActivity(intent);
    }
}