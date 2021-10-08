package com.lightingorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText ed_user_id;
    Button b_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_user_id = (EditText) findViewById(R.id.user_id);
        b_login = (Button) findViewById(R.id.login);
        Toast t = Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG);
/*
        AsyncHttpServer server = new AsyncHttpServer();
        server.get("/login", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
               // String json_login = request.getBody().toString();
               // t.setText("Giuseppe");
                response.code(200);
                response.send("<html><head></head><body>Giuseppe Hello</body></html>");
            }
        });
        server.listen(5000);
*/
    }

    public void sendLogin(View view){
/*
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        //Address to send my Login
        Uri address = Uri.parse("http://localhost:5000/login");


        Intent i = new Intent(this, FunctionalityActivity.class);
        startActivity(i);


        //Body of my Login (ID, etc)

        JSONObject j = new JSONObject();
        try {
            j.put("userID", ed_user_id.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
      //  Toast t = Toast.makeText(getApplicationContext(), ed_user_id.getText().toString(), Toast.LENGTH_LONG);
       //  t.show();


   /*
        //POST Request whit JSON Body
        AsyncHttpRequest req = new AsyncHttpRequest(address, "POST");
        JSONObjectBody post_body = new JSONObjectBody(j);
        req.setBody(post_body);


        //Send HTTP POST Request
        try {
        AsyncHttpClient.getDefaultInstance().executeString(req, new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
                  //  t.setText(result);
                    // Response message test
                    // (is not LOGIN RESPONSE MESSAGE, BUT HTTP RESPONSE BY PROTOCOL)
                   // t.show();
            }
        });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
*/
        ArrayList<String> ruoli_utente = new ArrayList<String>();
        ruoli_utente.add(StdTerms.roles.Cameriere.name());
        ruoli_utente.add(StdTerms.roles.Accoglienza.name());
        Intent i = new Intent(this, FunctionalityActivity.class);
        i.putStringArrayListExtra("Ruoli", ruoli_utente);
        startActivity(i);
    }
}