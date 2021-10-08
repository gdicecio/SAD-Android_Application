package com.lightingorder;

import android.annotation.SuppressLint;
import android.graphics.Color;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Table {
    public String ID;
    public String stato;
    public enum StatesList{
        free,
        waitingForOrders,
        Occupied,
        reserved;
    }

    static final private Map<String, Integer> color;

    static {
        Map <String, Integer> aMap = new HashMap<String, Integer>();
        aMap.put(StatesList.waitingForOrders.name(), Color.YELLOW);
        aMap.put(StatesList.Occupied.name(), Color.RED);
        aMap.put(StatesList.free.name(), Color.GREEN);
        aMap.put(StatesList.reserved.name(), Color.GRAY);
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

    public int getColor() {
        return color.get(this.stato);
    }
}
