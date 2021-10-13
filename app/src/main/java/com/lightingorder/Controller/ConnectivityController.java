package com.lightingorder.Controller;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.StringBody;
import com.lightingorder.Model.User;
import com.lightingorder.Model.messages.loginRequest;
import com.lightingorder.Model.messages.tableOperation;
import com.lightingorder.Model.messages.tableRequest;
import com.lightingorder.StdTerms;

public class ConnectivityController {
    private static ConnectivityController istanza = null;


    private ConnectivityController(){}

    public static synchronized ConnectivityController getConnectivity(){
        if(istanza == null){
            istanza = new ConnectivityController();
        }
        return istanza;
    }

    static private void sendPost(Context context, String body, String urlDestination){
        //POST Request whit String Body
        AsyncHttpRequest req = new AsyncHttpRequest(Uri.parse(urlDestination), "POST");
        StringBody post_body = new StringBody(body);
        req.setBody(post_body);

        Toast t = Toast.makeText(context, "Proxy received your message", Toast.LENGTH_SHORT);

        //Send HTTP POST Request
        try {
            AsyncHttpClient.getDefaultInstance().executeString(req, new AsyncHttpClient.StringCallback() {
                @Override
                public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
                    if(e == null) {
                        if(source.code() >= 200 && source.code() < 300)
                            t.setText("Proxy received your message");
                        else
                           t.setText("There were problems contacting the Proxy");
                    } else
                        t.setText("Proxy not reachable");

                    t.show();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendLoginRequest(Context ctx, UserController us_contr){

        //Body of my request ---> request type = loginRequest
        loginRequest req_body = new loginRequest(
                us_contr.getUserID(), //user
                "", //proxySource
                StdTerms.messages.loginRequest.name(),  //messageName
                "",     //result
                "",     //response
                us_contr.getUserIpAddress());    //url

        //Convert into string and send Post request
        Gson gson = new Gson();
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(ctx, msg_body, StdTerms.proxyLoginAddress);
    }

    public static void sendTableRequest(Context ctx, UserController us_contr, String proxy_addr){

        //Body of my request ---> request type = tableRequest
        tableRequest req_body = new tableRequest(
                us_contr.getUserID(), //user
                "",
                StdTerms.messages.tableRequest.name(),  //messageName
                "",
                "",
                true,
                1,
                false,
                "");

        //Convert into string and send Post request
        Gson gson = new Gson();
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(ctx, msg_body, proxy_addr);
    }


    public static void sendTableOperationRequest(Context ctx, UserController us_contr, String proxy_addr,
                                                 String tableID, int tableRoom, String new_state){

        String message_name = "";
        if (new_state.equals(StdTerms.statesList.free.name())){
             message_name = StdTerms.messages.freeTableRequest.name();
        }
        else if(new_state.equals(StdTerms.statesList.Occupied.name())){
            message_name = StdTerms.messages.userWaitingForOrderRequest.name();
        }

        Gson gson = new Gson();
        tableOperation req_body = new tableOperation(
                us_contr.getUserID(),
                proxy_addr,
                message_name,
                "",
                "",
                tableID,
                tableRoom);
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(ctx, msg_body,proxy_addr);
    }

}
