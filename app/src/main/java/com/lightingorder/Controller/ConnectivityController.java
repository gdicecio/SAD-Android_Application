package com.lightingorder.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
import com.lightingorder.Model.messages.cancelOrderRequest;
import com.lightingorder.Model.messages.itemOpRequest;
import com.lightingorder.Model.messages.loginRequest;
import com.lightingorder.Model.messages.menuRequest;
import com.lightingorder.Model.messages.orderRequest;
import com.lightingorder.Model.messages.orderToTableGenerationRequest;
import com.lightingorder.Model.messages.tableOperation;
import com.lightingorder.Model.messages.tableRequest;
import com.lightingorder.StdTerms;
import com.lightingorder.View.OrderListActivity;
import com.lightingorder.View.TableActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
                        Toast t = Toast.makeText(AppStateController.getApplication().getCurrent_activity(), "Ruolo " + msg_rcvd.result + " riconosciuto!", Toast.LENGTH_LONG);
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
                Gson gsonReq = new Gson();
                String txt_to_show = "";
                String resp = ""+msg_rcvd.response;

                if(!(msg_rcvd.result.contains("Failed") || msg_rcvd.result.contains("NotFound") ||
                        resp.contains("NotCreated"))) {
                    switch (message_type) {
                        case "cancelOrderedItemRequest":
                            sendTableRequest(
                                    user_contr,
                                    user_contr.getHashRuoli_Proxy().get(StdTerms.roles.Cameriere.name()));
                            txt_to_show = "Product removed from the order";
                            Log.d("SERVER","CancelOrderedItemRequest: Product removed from the order");
                            break;

                        case "cancelOrderRequest":
                            sendTableRequest(
                                    user_contr,
                                    user_contr.getHashRuoli_Proxy().get(StdTerms.roles.Cameriere.name()));
                            txt_to_show = "Order removed";
                            Log.d("SERVER","CancelOrderRequest: Order removed");
                            break;

                        case "orderToTableGenerationRequest":
                            sendTableRequest(
                                    user_contr,
                                    user_contr.getHashRuoli_Proxy().get(StdTerms.roles.Cameriere.name()));
                            txt_to_show = "Order added";
                            Log.d("SERVER","OrderToTableGenerationRequest: Order added");
                            break;

                        case "itemCompleteRequest":
                            //TODO
                            txt_to_show = "Action registered";
                            Log.d("SERVER","ItemCompleteRequest: Action registred");
                            break;

                        case "itemWorkingRequest":
                            //TODO
                            txt_to_show = "Request accepted";
                            Log.d("SERVER","ItemWorkingRequest: Request accepted");
                            break;

                        case "freeTableRequest":
                            tableOperation free_msg = gsonReq.fromJson(req, tableOperation.class);
                            Data.getData().updateTableState(free_msg.tableID,
                                                            free_msg.tableRoomNumber,
                                                            StdTerms.statesList.free.name());
                            Activity current = AppStateController.getApplication().getCurrent_activity();
                            if(current.getLocalClassName().equals("View.TableActivity")){
                                current.finish();
                                current.startActivity(current.getIntent());
                            }
                            txt_to_show = "Table state updated";
                            Log.d("SERVER","FreeTableRequest: Table state updated");
                            break;

                        case "userWaitingForOrderRequest":
                            tableOperation usr_wait_msg = gsonReq.fromJson(req, tableOperation.class);
                            Data.getData().updateTableState(usr_wait_msg.tableID,
                                                            usr_wait_msg.tableRoomNumber,
                                                            StdTerms.statesList.waitingForOrders.name());

                            Activity current2 = AppStateController.getApplication().getCurrent_activity();
                            if(current2.getLocalClassName().equals("View.TableActivity")){
                                current2.finish();
                                current2.startActivity(current2.getIntent());
                            }
                            txt_to_show = "Table state updated";
                            Log.d("SERVER","UserWaitingForOrderRequest: Table state updated");
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

                            Activity current3 = AppStateController.getApplication().getCurrent_activity();
                            Intent i;
                            i = new Intent(current3, TableActivity.class);
                            current3.startActivity(i);
                            /*if(current3.getLocalClassName().equals("View.FunctionalityActivity")){
                                i = new Intent(current3, TableActivity.class);
                                current3.startActivity(i);
                            }
                            else if(current3.getLocalClassName().equals("View.OrderListActivity")
                            ||  current3.getLocalClassName().equals("View.OrderActivity")){
                                i = new Intent(current3, OrderListActivity.class);
                                current3.startActivity(i);
                            }*/

                            txt_to_show = "Table list updated";
                            Log.d("SERVER","TableRequest: Table list updated");
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
                            Log.d("SERVER","MenuRequest: Menu list updated");
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
                            //TODO vai alla schermata per realizzatori
                            txt_to_show = "Order list updated";
                            break;

                        default:
                            txt_to_show = "Message not recognized";
                            Log.e("SERVER","Message not recognized");
                    }
                }
                else{
                    txt_to_show = "Main System: operation not allowed";
                    Log.e("SERVER", "Main System : operation not allowed");
                }
                String finalTxt_to_show = txt_to_show;
                AppStateController.getApplication().getCurrent_activity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast t = Toast.makeText(AppStateController.getApplication().getCurrent_activity(), finalTxt_to_show, Toast.LENGTH_SHORT);
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


    static private void sendPost(String body, String urlDestination){

        //POST Request whit String Body
        AsyncHttpRequest req = new AsyncHttpRequest(Uri.parse("http://"+urlDestination), "POST");
        StringBody post_body = new StringBody(body);
        req.setBody(post_body);

        //Send HTTP POST Request
        try {
            AsyncHttpClient.getDefaultInstance().executeString(req, new AsyncHttpClient.StringCallback() {
                @Override
                public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
                    if(e == null) {
                        if(source.code() >= 200 && source.code() < 300)
                            Log.d("PROXY","Proxy received your message");
                        else
                           Log.d("PROXY","There were problems contacting the Proxy");

                        AppStateController.getApplication().setProxyConnectionState(source.code());
                    } else {
                        Log.d("PROXY","Proxy not reachable");
                        AppStateController.getApplication().setProxyConnectionState(600);// In this case 600 means that an exception has been thrown
                    }
                    //t.show();

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void sendLoginRequest(UserSessionController us_contr){
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
        ConnectivityController.sendPost(msg_body, StdTerms.proxyLoginAddress);

    }

    public static void sendMenuRequest(UserSessionController us_contr, String proxy_addr){
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
        ConnectivityController.sendPost(msg_body, proxy_addr);
    }

    public static void sendTableRequest(UserSessionController us_contr, String proxy_addr){

        //Body of my request ---> request type = tableRequest
        tableRequest req_body = new tableRequest(
                us_contr.getUserID(), //user
                us_contr.getCurrentRole(),
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
        ConnectivityController.sendPost(msg_body, proxy_addr);
    }


    public static void sendTableOperationRequest(UserSessionController us_contr, String proxy_addr,
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
        ConnectivityController.sendPost(msg_body,proxy_addr);
    }


    public static void sendOrderRequest(UserSessionController us_contr, String proxy_addr){

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
        ConnectivityController.sendPost(msg_body,proxy_addr);
    }

    public static void sendAddOrderToTableRequest(UserSessionController us_contr,
                                                  String proxy_addr, String tableID, int roomNumber,
                                                  List<String> items_name,List<List<String>> additive, List<List<String>> sub,
                                                  List<Integer> priority){

        orderToTableGenerationRequest.orderParameters param = new orderToTableGenerationRequest.orderParameters(
                        items_name,
                        additive,
                        sub,
                        priority);

        Gson gson = new Gson();
        orderToTableGenerationRequest req_body = new orderToTableGenerationRequest(
                us_contr.getUserID(),
                proxy_addr,
                "orderToTableGenerationRequest",
                "",
                "",
                tableID,
                roomNumber,
                param);
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(msg_body,proxy_addr);

    }

    public static void sendCancelOrderRequest(UserSessionController us_contr, String proxy_addr, int orderID){

        Gson gson = new Gson();
        cancelOrderRequest req_body = new cancelOrderRequest(
                us_contr.getUserID(),
                proxy_addr,
                "cancelOrderRequest",
                "",
                "",
                orderID);
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(msg_body,proxy_addr);
    }


    public static void sendCancelOrderedItemRequest(UserSessionController us_contr, String proxy_addr, int orderID, int lineNumber){

        Gson gson = new Gson();
        itemOpRequest req_body = new itemOpRequest(
                us_contr.getUserID(),
                proxy_addr,
                "cancelOrderedItemRequest",
                "",
                "",
                orderID,
                lineNumber);
        String msg_body = gson.toJson(req_body);
        ConnectivityController.sendPost(msg_body,proxy_addr);

    }

}


