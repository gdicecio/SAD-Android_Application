package com.lightingorder.Model;

import com.lightingorder.Controller.ConnectivityController;

import java.util.HashMap;
//TODO Singleton
public class User {
    private static User istanza = null;
    private  String ID;
    private  HashMap<String,String> ruoli_proxy = new HashMap<String,String>();
    private  String ipAddress;

    private User() { }

    public static synchronized User getUser(){
        if(istanza == null){
            istanza = new User();
        }
        return istanza;
    }

    public String getID() {
        return istanza.ID;
    }

    public void addRole(String role, String proxy_add){
        istanza.ruoli_proxy.put(role,proxy_add);
    }

    public  void setID(String ID) {
        istanza.ID = ID;
    }

    public  HashMap<String, String> getRuoli_proxy() {
        return istanza.ruoli_proxy;
    }

    public  String getIpAddress() {
        return istanza.ipAddress;
    }

    public  void setIpAddress(String ipAddress) {
        istanza.ipAddress = ipAddress;
    }
}
