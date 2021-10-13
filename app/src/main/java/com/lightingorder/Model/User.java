package com.lightingorder.Model;

import java.util.HashMap;
//TODO Singleton
public class User {
    private  String ID;
    private  HashMap<String,String> ruoli_proxy = new HashMap<String,String>();
    private  String ipAddress;

    public User() { }

    public String getID() {
        return ID;
    }

    public void addRole(String role, String proxy_add){
        this.ruoli_proxy.put(role,proxy_add);
    }

    public  void setID(String ID) {
        this.ID = ID;
    }

    public  HashMap<String, String> getRuoli_proxy() {
        return ruoli_proxy;
    }

    public  String getIpAddress() {
        return this.ipAddress;
    }

    public  void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
