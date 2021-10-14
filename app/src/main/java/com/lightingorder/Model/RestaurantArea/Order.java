package com.lightingorder.Model.RestaurantArea;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Order {

    @Expose(serialize=true,deserialize=true)
    private int orderID;

    @Expose(serialize=true,deserialize=true)
    private String orderState;

    @Expose(serialize=true,deserialize=true)
    private int completedItemNumber=0;

    @Expose(serialize=true,deserialize=true)
    private String userID;

    @Expose(serialize = true, deserialize = true)
    private int tableRoomNumber;

    @Expose(serialize=true,deserialize=true)
    private List<OrderedItem> orderedItems = new ArrayList<OrderedItem>();


    public Order(){}

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public int getCompletedItemNumber() {
        return completedItemNumber;
    }

    public void setCompletedItemNumber(int completedItemNumber) {
        this.completedItemNumber = completedItemNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
