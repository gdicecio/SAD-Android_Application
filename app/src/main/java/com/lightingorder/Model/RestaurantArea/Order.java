package com.lightingorder.Model.RestaurantArea;

import com.google.gson.annotations.Expose;

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

    //@Expose(serialize=false,deserialize=false)
    //private List<OrderedItem> orderedItems = new ArrayList<OrderedItem>();





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

    public Order(){}

}
