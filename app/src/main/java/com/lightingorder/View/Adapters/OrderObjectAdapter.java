package com.lightingorder.View.Adapters;

import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.Model.RestaurantArea.OrderedItem;

public class OrderObjectAdapter {

    private int line_order;
    private int priority;
    private String product;
    private String table_number;
    private int table_room_number;
    private String user_id;
    private int order_id;

    public OrderObjectAdapter(OrderedItem ordered_item, Order order){
        this.line_order = ordered_item.getLineNumber();
        this.priority = ordered_item.getPriority();
        this.product = ordered_item.getItem();
        this.table_room_number = order.getTableRoomNumber();
        this.table_number = order.getTableID();
        this.user_id = order.getUserID();
        this.order_id = order.getOrderID();
    }

    public int getLine_order() {
        return line_order;
    }

    public int getPriority() {
        return priority;
    }

    public String getProduct() {
        return product;
    }

    public String getTable_number() {
        return table_number;
    }

    public int getTable_room_number() {
        return table_room_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getOrder_id() {
        return order_id;
    }



}
