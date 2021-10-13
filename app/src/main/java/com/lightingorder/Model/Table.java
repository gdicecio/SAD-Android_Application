package com.lightingorder.Model;

import com.lightingorder.StdTerms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Table {
    public String ID;
    public String stato;
    static final private Map<String, Integer> color;

    static {
        Map <String, Integer> aMap = new HashMap<String, Integer>();
        aMap.put(StdTerms.statesList.waitingForOrders.name(),StdTerms.orange);//YELLOW
        aMap.put(StdTerms.statesList.Occupied.name(), StdTerms.red);//RED
        aMap.put(StdTerms.statesList.free.name(), StdTerms.green);//GREEN
        aMap.put(StdTerms.statesList.reserved.name(), StdTerms.blue);//BLUE
        color = Collections.unmodifiableMap(aMap);
    }

    public Table(String ID, String stato) {
        this.ID = ID;
        this.stato = stato;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getText(){
        return this.ID;
    }

    public String getIdAndState() {
        String to_ret;
        switch (this.stato) {
            case "free":
                 to_ret = this.ID + "\n\n" + "Free";
            break;
            case "waitingForOrders":
                to_ret = this.ID + "\n\n" + "Waiting";
            break;
            case "Occupied":
                to_ret = this.ID + "\n\n" + "Occupied";
            break;
            case "reserved":
                to_ret = this.ID + "\n\n" + "Reserved";
            break;
            default:
                to_ret = "State not recognized";
        }
        return to_ret;
    }
            
    public int getColor() {
        return color.get(this.stato);
    }
}
