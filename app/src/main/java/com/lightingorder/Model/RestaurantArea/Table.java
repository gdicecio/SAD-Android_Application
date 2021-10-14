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

    @Expose(serialize=true,deserialize=true)
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
