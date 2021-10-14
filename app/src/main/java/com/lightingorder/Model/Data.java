package com.lightingorder.Model;

import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.Model.RestaurantArea.Table;

import java.util.ArrayList;

public class Data {
    private static Data istanza = null;
    private ArrayList<Table> tablesList = new ArrayList<>();
    private ArrayList<Order> ordersList = new ArrayList<>();


    private Data(){}
    public static synchronized Data getData(){
        if(istanza == null){
            istanza = new Data();
        }
        return istanza;
    }

    public ArrayList<Table> getTablesList() {return istanza.tablesList;}

    public ArrayList<Order> getOrdersList() {return istanza.ordersList;}

    public void setTablesList(ArrayList<Table> tablesList) {istanza.tablesList = tablesList;}

    public void setOrdersList(ArrayList<Order> ordersList) {istanza.ordersList = ordersList;}







}
