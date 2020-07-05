package com.example.gpstrackerapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

import java.net.URISyntaxException;

import static com.example.gpstrackerapp.HomePageActivity.LAUNCH_MAP;

public class InviteCodeActivity extends AppCompatActivity {

    private Socket mSocket;
    TextView tvCode;
    TelephonyManager tMgr;
    String getNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_code);

        tvCode = (TextView) findViewById(R.id.textView_code);

        try {
            mSocket = IO.socket("https://dacnpm-backend.herokuapp.com/connect");
            mSocket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        /*
        this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_NUMBERS}, PackageManager.PERMISSION_GRANTED);
        this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
        this.requestPermissions(new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
         */


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_NUMBERS}, PackageManager.PERMISSION_GRANTED);
            this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, PackageManager.PERMISSION_GRANTED);
            this.requestPermissions(new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        }

        tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        getNumber = tMgr.getLine1Number();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("userId", "5e932814d26d1d1d9c5cd034");
            jsonBody.put("phoneNumber", getNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mSocket.emit("parent wait",jsonBody); //Fixed ID for testing: 5e932814d26d1d1d9c5cd034, otherwise, change to userId
        mSocket.on("wait connect", onWaitConnect);

        mSocket.on("child connect", onChildConnect);
    }

    private Emitter.Listener onWaitConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        String code = object.getString("connectionString");
                        tvCode.setText(code);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private Emitter.Listener onChildConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        String childID = object.getString("connect");
                        //Toast.makeText(InviteCodeActivity.this, childID, Toast.LENGTH_LONG).show();
                        confirmChildScreen(childID); //Enable once child-side is completed
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    public void confirmChildScreen(String childID) {
        Intent intent = new Intent(this, ConfirmChildActivity.class);
        intent.putExtra("CHILD_ID", childID);
        startActivityForResult(intent, LAUNCH_MAP);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setResult(LAUNCH_MAP, data);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("Disconnect from Socket Server!");
    }
}

