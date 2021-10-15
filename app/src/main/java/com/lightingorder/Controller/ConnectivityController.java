package com.lightingorder.Controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpRequest;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.body.StringBody;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.lightingorder.Model.Data;
import com.lightingorder.Model.MenuAndWareHouseArea.MenuItem;
import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.Model.RestaurantArea.Table;
import com.lightingorder.Model.messages.baseMessage;
import com.lightingorder.Model.messages.loginRequest;
import com.lightingorder.Model.messages.menuRequest;
import com.lightingorder.Model.messages.orderRequest;
import com.lightingorder.Model.messages.tableOperation;
import com.lightingorder.Model.messages.tableRequest;
import com.lightingorder.StdTerms;
import com.lightingorder.View.TableActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class ConnectivityController {   //Singleton
    private static ConnectivityController istanza = null;
    private final AsyncHttpServer server = new AsyncHttpServer();

    private ConnectivityController(){}
    public static synchronized ConnectivityController getConnectivity(){
        if(istanza == null){
            istanza = new ConnectivityController();
        }
        return istanza;
    }

    public void configPostMapping() {

        Gson gson = new Gson();
        UserSessionController user_contr = new UserSessionController();

        istanza.server.post("/login", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                //Retrieve the message body as String (in JSON format)
                String req = request.getBody().toString();
                //Create the object using the JSON string
                loginRequest msg_rcvd = gson.fromJson(req, loginRequest.class);
                //Add role and relative proxy to the user session hashmap
                user_contr.addRoleAndProxy(msg_rcvd.result,msg_rcvd.proxySource);
                AppStateController.getApplication().getCurrent_activity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast t = Toast.makeText(AppStateController.getApplication().getCurrent_activity(), "Ruolo " + msg_rcvd.result + " aggiunto!", Toast.LENGTH_LONG);
                        t.show();
                    }
                });
                //Sending the response to the proxy
                response.code(200);
                response.send("Message received");
            }
        });


        istanza.server.post("/request", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                //Retrieve the message body as String (in JSON format)
                String req = request.getBody().toString();
                //Create the object using the JSON string
                baseMessage msg_rcvd = gson.fromJson(req, baseMessage.class);
                String message_type  = msg_rcvd.messageName;
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                String txt_to_show = "";

                if(!(msg_rcvd.result.contains("Failed") || msg_rcvd.result.contains("NotFound"))) {
                    switch (message_type) {
                        case "cancelOrderedItemRequest":
                            txt_to_show = "Product removed from the order";
                            break;

                        case "cancelOrderRequest":
                            txt_to_show = "Order removed";
                            break;

                        case "orderToTableGenerationRequest":
                            txt_to_show = "Order added";
                            break;

                        case "freeTableRequest":
                            tableOperation free_msg = gson.fromJson(req, tableOperation.class);
                            Data.getData().updateTableState(free_msg.tableID,
                                                            free_msg.tableRoomNumber,
                                                            StdTerms.statesList.free.name());

                            txt_to_show = "Table state updated";
                            break;

                        case "userWaitingForOrderRequest":
                            tableOperation usr_wait_msg = gson.fromJson(req, tableOperation.class);
                            Data.getData().updateTableState(usr_wait_msg.tableID,
                                                            usr_wait_msg.tableRoomNumber,
                                                            StdTerms.statesList.waitingForOrders.name());

                            txt_to_show = "Table state updated";
                            break;

                        case "itemCompleteRequest":
                            txt_to_show = "Action registered";
                            break;

                        case "itemWorkingRequest":
                            txt_to_show = "Request accepted";
                            break;

                        case "tableRequest":
                            ArrayList<Table> tables = new ArrayList<Table>();
                            try {
                                JSONArray j = new JSONArray(msg_rcvd.response);
                                for(int i=0; i<j.length(); i++){
                                    Table single_table = new Table();
                                    single_table = (Table) gson.fromJson(j.get(i).toString(), Table.class);
                                    tables.add(single_table);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Data.getData().setTablesList(tables);
                            Intent i = new Intent(AppStateController.getApplication().getCurrent_activity(), TableActivity.class);
                            AppStateController.getApplication().getCurrent_activity().startActivity(i);
                            txt_to_show = "Table list updated";
                            break;

                        case "menuRequest":
                            ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
                            try {
                                JSONArray j = new JSONArray(msg_rcvd.response);
                                for(int k=0; k<j.length(); k++){
                                    MenuItem single_item = new MenuItem();
                                    single_item = (MenuItem) gson.fromJson(j.get(k).toString(), MenuItem.class);
                                    menu.add(single_item);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Data.getData().setMenuList(menu);
                            txt_to_show = "Menu list updated";
                            break;

                        case "orderRequest":
                            ArrayList<Order> orders = new ArrayList<Order>();
                            try {
                                JSONArray j = new JSONArray(msg_rcvd.response);
                                for(int k=0; k<j.length(); k++){
                                    Order single_order = new Order();
                                    single_order = (Order) gson.fromJson(j.get(k).toString(), Order.class);
                                    orders.add(single_order);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Data.getData().setOrdersList(orders);
                            txt_to_show = "Order list updated";
                            break;

                        default:
                            txt_to_show = "Message not recognized";
                    }
                }
                else txt_to_show = "There were problem with the Main System";
                String finalTxt_to_show = txt_to_show;
                AppStateController.getApplication().getCurrent_activity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast t = Toast.makeText(AppStateController.getApplication().getCurrent_activity(), finalTxt_to_show, Toast.LENGTH_LONG);
                        t.show();
                    }
                });

                //Sending the response to the proxy
                response.code(200);
                response.send("Message received");
            }
        });


        istanza.server.post("/notification", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                //Retrieve the message body as String (in JSON format)
                String req = request.getBody().toString();
                //Create the object using the JSON string
                baseMessage msg_rcvd = gson.fromJson(req, baseMessage.class);
                String message_type  = msg_rcvd.messageName;

                if(!(msg_rcvd.result.contains("Failed") || msg_rcvd.result.contains("NotFound"))) {


                }
                else {}

                AppStateController.getApplication().getCurrent_activity().runOnUiThread(new Runnable() {
                    public void run() {

                    }
                });

                //Sending the response to the proxy
                response.code(200);
                response.send("Message received");
            }
        });

    }


    public void startServer(int port){
        istanza.server.listen(port);
    }


    static private void sendPost(Context context, String body, String urlDestination){

        //POST Request whit String Body
        AsyncHttpRequest req = new AsyncHttpRequest(Uri.parse("http://"+urlDestination), "POST");
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

                        AppStateController.getApplication().setProxyConnectionState(source.code());
                    } else {
                        t.setText("Proxy not reachable");
                        AppStateController.getApplication().setProxyConnectionState(600);// In this case 600 means that an exception has been thrown
                    }
                    t.show();

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendLoginRequest(Context ctx, UserSessionController us_contr){
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

    public static void sendMenuRequest(Context ctx, UserSessionController us_contr, String proxy_addr){
        menuRequest req_body = new menuRequest(
                us_contr.getUserID(),
                "",
                StdTerms.messages.menuRequest.name(),
                "",
                "",
                false,
                "" );
        Gson gson = new Gson();
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(ctx, msg_body, proxy_addr);
    }

    public static void sendTableRequest(Context ctx, UserSessionController us_contr, String proxy_addr){

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


    public static void sendTableOperationRequest(Context ctx, UserSessionController us_contr, String proxy_addr,
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


    public static void sendOrderRequest(Context ctx,UserSessionController us_contr, String proxy_addr){

        String area = us_contr.getCurrentRole();

        Gson gson = new Gson();
        orderRequest req_body = new orderRequest(
                us_contr.getUserID(),
                proxy_addr,
                "orderRequest",
                "",
                "",
                true,
                area);
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(ctx, msg_body,proxy_addr);
    }

}
