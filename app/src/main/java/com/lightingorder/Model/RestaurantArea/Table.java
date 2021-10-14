package com.lightingorder.Model.RestaurantArea;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Table {
    @Expose(serialize=true,deserialize=true)
    public String tableID;

    @Expose(serialize=true,deserialize=true)
    public String tableState;

    @Expose(serialize=true,deserialize=true)
    private int tableRoomNumber;

    @Expose(serialize=false,deserialize=false)
    public List<Order> orders = new ArrayList<Order>();

    public Table(){};

    public Table(String tableID, String stato) {
        this.tableID = tableID;
        this.tableState = stato;
        this.orders = new ArrayList<Order>();
    }

    public String getTableState() {
        return tableState;
    }

    public void setOrderFromJson(String orders_json){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String temp = null;
        try {
            JSONArray j = new JSONArray(orders_json);
            for(int i=0; i<j.length(); i++){
                Order o = new Order();
                o = (Order) gson.fromJson(j.get(i).toString(), Order.class);
                //String ordered_item = j.getJSONObject(i).get("orderedItems").toString();
                //o.setOrderedItemFromJson(ordered_items);
                this.orders.add(o);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setTableState(String tableState) {
        this.tableState = tableState;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getText(){
        return this.tableID;
    }

    public String getIdAndState() {
        String to_ret;
        switch (this.tableState) {
            case "free":
                 to_ret = this.tableID + "\n\n" + "Free";
            break;
            case "waitingForOrders":
                to_ret = this.tableID + "\n\n" + "Waiting";
            break;
            case "Occupied":
                to_ret = this.tableID + "\n\n" + "Occupied";
            break;
            case "reserved":
                to_ret = this.tableID + "\n\n" + "Reserved";
            break;
            default:
                to_ret = "State not recognized";
        }
        return to_ret;
    }

}
