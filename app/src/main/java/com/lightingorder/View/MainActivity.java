package com.lightingorder.View;

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
import com.lightingorder.Controller.UserController;
import com.lightingorder.View.FunctionalityActivity;
import com.lightingorder.R;
import com.lightingorder.StdTerms;
import com.lightingorder.Model.User;
import com.lightingorder.Model.messages.loginRequest;

public class MainActivity extends AppCompatActivity {

    EditText ed_user_id;
    Button b_login;
    UserController user_contr = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_user_id = (EditText) findViewById(R.id.user_id);
        b_login = (Button) findViewById(R.id.login);

        Gson gson=new Gson();

        AsyncHttpServer server = new AsyncHttpServer();
        server.post("/login", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                //Retrieve the message body as String (in JSON format)
                String req = request.getBody().toString();
                //Create the object using the JSON string
                loginRequest msg_rcvd = gson.fromJson(req,loginRequest.class);
                // Add to the Hashmap the user role and the address of the proxy which manage the requests from that role
                user_contr.addRoleAndProxy(msg_rcvd.result, msg_rcvd.proxySource);
                runOnUiThread(new Runnable() {
                    public void run()
                    {
                        Toast t = Toast.makeText(MainActivity.this, "Ruolo "+ msg_rcvd.result + " aggiunto!", Toast.LENGTH_LONG);
                        t.show();
                    }
                });
                //Sending the response to the proxy
                response.code(200);
                response.send("Msg received");
            }
        });
        server.listen(StdTerms.server_port);
    }

    public void sendLogin(View view){

        user_contr.setUserID(ed_user_id.getText().toString());

        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        user_contr.setUserIpAddress(ipAddress+":"+Integer.toString(StdTerms.server_port));

        //ConnectivityController.sendLoginRequest(getApplicationContext(),user_contr);

        new CountDownTimer(1000, 1000) {
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
}