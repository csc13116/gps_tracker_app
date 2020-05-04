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

public class EnterCodeActivity extends AppCompatActivity {

    private Socket mSocket;
    Pinview connectionCode;
    Button btnChildConnect;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        connectionCode = findViewById(R.id.pinView_code);
        btnChildConnect = findViewById(R.id.btn_connect);

        try {
            mSocket = IO.socket("https://dacnpm-backend.herokuapp.com/connect");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        btnChildConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String inputCode = connectionCode.getValue();
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
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public void onBothPartiesConnect() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}