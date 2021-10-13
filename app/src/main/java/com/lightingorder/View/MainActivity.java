package com.lightingorder.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.lightingorder.Controller.ConnectivityController;
import com.lightingorder.Controller.AppStateController;
import com.lightingorder.Controller.UserSessionController;
import com.lightingorder.R;
import com.lightingorder.StdTerms;
import com.lightingorder.Model.messages.loginRequest;

public class MainActivity extends AppCompatActivity {

    EditText ed_user_id;
    Button b_login;
    UserSessionController user_contr = new UserSessionController();
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    ConnectivityController conn_contr = ConnectivityController.getConnectivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_user_id = (EditText) findViewById(R.id.user_id);
        b_login = (Button) findViewById(R.id.login);

        conn_contr.configPostMapping();
        conn_contr.startServer(StdTerms.server_port);
    }

    public void sendLogin(View view){

        user_contr.setUserID(ed_user_id.getText().toString());

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        user_contr.setUserIpAddress(ipAddress+":"+(StdTerms.server_port));

        ConnectivityController.sendLoginRequest(getApplicationContext(),user_contr);

        new CountDownTimer(3000, 1000) {
            public void onFinish() {
                // When timer is finished // Execute your code here
                Intent i = new Intent(getApplicationContext(), FunctionalityActivity.class);
                startActivity(i);
            }
            public void onTick(long millisUntilFinished) {
                // millisUntilFinished    The amount of time until finished.
            }
        }.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppStateController.getApplication().setCurrent_activity(this);
    }
}

