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
        while(!find){
            if(istanza.tablesList.get(i).getTableID().equals(tableID) && istanza.tablesList.get(i).getTableRoomNumber() == roomNumber) {
                find = true;
                istanza.tablesList.get(i).setTableState(new_state);
            }
            i = i + 1;
        }
    }







}
