package com.lightingorder.Model;

import android.view.Menu;

import com.lightingorder.Model.MenuAndWareHouseArea.Goods;
import com.lightingorder.Model.MenuAndWareHouseArea.MenuItem;
import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.Model.RestaurantArea.Table;

import java.util.ArrayList;

//Singleton
public class Data {
    private static Data istanza = null;
    private ArrayList<Table> tablesList = new ArrayList<>();
    private ArrayList<Order> ordersList = new ArrayList<>();
    private ArrayList<MenuItem> menuList = new ArrayList<>();
    private ArrayList<Goods> goodsList = new ArrayList<>();


    private Data(){}
    public static synchronized Data getData(){
        if(istanza == null){
            istanza = new Data();
        }
        return istanza;
    }

    public ArrayList<Table> getTablesList() {return istanza.tablesList;}

    public ArrayList<Order> getOrdersList() {return istanza.ordersList;}

    public ArrayList<MenuItem> getMenuList(){ return istanza.menuList; }

    public void setTablesList(ArrayList<Table> tablesList) {istanza.tablesList = tablesList;}

    public void setOrdersList(ArrayList<Order> ordersList) {istanza.ordersList = ordersList;}

    public void setMenuList(ArrayList<MenuItem> menuList){ istanza.menuList = menuList; }


    public void updateTableState(String tableID, int roomNumber, String new_state){
        boolean find = false;
        int i = 0;
        while(!find && i<istanza.tablesList.size()){
            if(istanza.tablesList.get(i).getTableID().equals(tableID) && istanza.tablesList.get(i).getTableRoomNumber() == roomNumber) {
                find = true;
                istanza.tablesList.get(i).setTableState(new_state);
            }
            i = i + 1;
        }
    }


    public void removeOrderFromList(int orderID){
        boolean find = false;
        int i = 0;
        while(!find){
            if(istanza.ordersList.get(i).getOrderID() == orderID) {
                find = true;
                istanza.ordersList.remove(i);
            }
            i = i + 1;
        }
    }

    public ArrayList<Order> getTableOrders(String tableID, int roomNumber){
        boolean find = false;
        int i = 0;
        while(!find){
            if(istanza.tablesList.get(i).getTableID().equals(tableID) && istanza.tablesList.get(i).getTableRoomNumber() == roomNumber)
                find = true;
            else
                i = i + 1;
        }
        return istanza.tablesList.get(i).getOrderList();
    }

    public void addOrderToTable(String tableID, int roomNumber, Order new_order){
        boolean find = false;
        int i = 0;
        while(!find){
            if(istanza.tablesList.get(i).getTableID().equals(tableID) && istanza.tablesList.get(i).getTableRoomNumber() == roomNumber) {
                find = true;
                istanza.tablesList.get(i).addOrderToTable(new_order);
            }
            i = i + 1;
        }
        //aggiunta dell'ordine anche alla lista di ordini
        this.ordersList.add(new_order);
    }



    public void deleteOrderFromTable(String tableID, int roomNumber, int orderID){
        boolean find = false;
        int i = 0;
        while(!find){
            if(istanza.tablesList.get(i).getTableID().equals(tableID) && istanza.tablesList.get(i).getTableRoomNumber() == roomNumber) {
                find = true;
                istanza.tablesList.get(i).removeOrderFromTable(orderID);
                istanza.removeOrderFromList(orderID);
            }
            i = i + 1;
        }
    }


}
