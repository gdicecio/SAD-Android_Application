package com.lightingorder.Model;

import com.lightingorder.Model.MenuAndWareHouseArea.Goods;
import com.lightingorder.Model.MenuAndWareHouseArea.MenuItem;
import com.lightingorder.Model.RestaurantArea.Order;
import com.lightingorder.Model.RestaurantArea.OrderedItem;
import com.lightingorder.Model.RestaurantArea.Table;
import com.lightingorder.StdTerms;

import java.util.ArrayList;
import java.util.List;

//Singleton
public class Data {
    private static Data istanza = null;
    private ArrayList<Table> tablesList = new ArrayList<>();
    private ArrayList<Order> kitchen_ordersList = new ArrayList<>();
    private ArrayList<Order> bar_ordersList = new ArrayList<>();
    private ArrayList<Order> pizza_ordersList = new ArrayList<>();
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

    public ArrayList<Order> getOrdersList(String role) {
        if(role.equals(StdTerms.roles.Forno.name()))
            return istanza.pizza_ordersList;
        else if(role.equals(StdTerms.roles.Cucina.name()))
            return istanza.kitchen_ordersList;
        else if(role.equals(StdTerms.roles.Bar.name()))
            return istanza.bar_ordersList;
        else
            return new ArrayList<Order>();
    }

    public ArrayList<MenuItem> getMenuList(){ return istanza.menuList; }

    public String[] getMenuItemsName(){
        String[] to_ret = new String[istanza.menuList.size()+1];
        to_ret[0] = "No selection";
        int i = 1;
        for(MenuItem m : istanza.menuList){
            to_ret[i] = m.getName();
            i += 1;
        }

        return to_ret;
    }

    public void setTablesList(ArrayList<Table> tablesList) {istanza.tablesList = tablesList;}

    public void setOrdersList(ArrayList<Order> ordersList, String role) {
        if(role.equals(StdTerms.roles.Forno.name()))
            istanza.pizza_ordersList = ordersList;
        else if(role.equals(StdTerms.roles.Cucina.name()))
            istanza.kitchen_ordersList = ordersList;
        else if(role.equals(StdTerms.roles.Bar.name()))
            istanza.bar_ordersList = ordersList;
    }

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

    public ArrayList<Order> getTableOrders(String tableID, int roomNumber){
        boolean find = false;
        int i = 0;
        while(!find){
            if(istanza.tablesList.get(i).getTableID().equals(tableID) && istanza.tablesList.get(i).getTableRoomNumber() == roomNumber)
                find = true;
            else
                i = i + 1;
        }
        return istanza.tablesList.get(i).getOrdersList();
    }


    public List<OrderedItem> getOrderedItemsForOrder(String tableID, int roomNumber, int orderID){
        ArrayList<Order> orders = getTableOrders(tableID,roomNumber);
        boolean find = false;
        int i = 0;
        while(!find){
            if(orders.get(i).getOrderID() == orderID){
                find = true;
            }
            else i = i + 1;
        }
        return orders.get(i).getOrderedItems();
    }


    public void removeOrderFromList(int orderID) {
        boolean findBar = false;
        boolean findKitch = false;
        boolean findPizza = false;
        int i = 0;
        while (!(findBar && findKitch && findPizza)) {
            if (!findBar) {
                if (istanza.bar_ordersList.get(i).getOrderID() == orderID) {
                    findBar = true;
                    istanza.bar_ordersList.remove(i);
                }
            }
            if (!findKitch) {
                if (istanza.kitchen_ordersList.get(i).getOrderID() == orderID) {
                    findKitch = true;
                    istanza.kitchen_ordersList.remove(i);
                }
            }
            if (!findKitch) {
                if (istanza.kitchen_ordersList.get(i).getOrderID() == orderID) {
                    findKitch = true;
                    istanza.kitchen_ordersList.remove(i);
                }
            }

            i = i+1;
        }
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
