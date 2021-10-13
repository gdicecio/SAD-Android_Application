package com.lightingorder.Model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Table {
    @Expose(serialize=true,deserialize=true)
    public String tableID;

    @Expose(serialize=true,deserialize=true)
    public String actualState;

    @Expose(serialize=true,deserialize=true)
    private int tableRoomNumber;

    @Expose(serialize=true,deserialize=true)
    private String orders;

    public Table(String tableID, String stato) {
        this.tableID = tableID;
        this.actualState = stato;
    }

    public String getActualState() {
        return actualState;
    }

    public void setActualState(String actualState) {
        this.actualState = actualState;
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
        switch (this.actualState) {
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
