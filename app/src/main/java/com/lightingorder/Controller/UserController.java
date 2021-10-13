package com.lightingorder.Controller;

import com.lightingorder.Model.User;

import java.util.HashMap;

public class UserController {
    private static User utente = new User();

    public UserController() {
    }

    public void addRoleAndProxy(String role, String proxy_address){
        utente.addRole(role,proxy_address);
    }

    public void setUserID(String ID){
        utente.setID(ID);
    }

    public void setUserIpAddress(String IP){
        utente.setIpAddress(IP);
    }

    public String getUserID(){
        return utente.getID();
    }

    public String getUserIpAddress(){
        return utente.getIpAddress();
    }

    public HashMap<String,String> getHashRuoli_Proxy(){
        return utente.getRuoli_proxy();
    }
}
