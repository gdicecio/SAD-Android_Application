package com.lightingorder;

import android.graphics.Color;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StdTerms {

    public static final int blue = Color.rgb(0, 102, 255);//YELLOW
    public static final int green = Color.rgb(0, 204, 0);//GREEN
    public static final int red = Color.rgb(255, 0, 0);//RED;
    public static final int yellow = Color.rgb(255, 255, 26);//RED
    public static final int orange = Color.rgb(255, 102, 0);
    public static final int purple = Color.rgb(102, 0, 204);


    public static final String proxyLoginAddress = "http://192.168.1.115:8085/loginSend";
    public static final int server_port = 5000;


    static final public Map<String, String> UC_Role;
    static {
        Map <String, String> aMap = new HashMap<>();
        aMap.put(useCases.CreaOrdinazione.name(),roles.Cameriere.name() );
        aMap.put(useCases.VisualizzaOrdinazioniCucina.name(), roles.Cucina.name());
        aMap.put(useCases.VisualizzaOrdinazioniBar.name(), roles.Bar.name());
        aMap.put(useCases.AggiornaStatoTavolo.name(), roles.Accoglienza.name());
        UC_Role = Collections.unmodifiableMap(aMap);
    }


    public enum roles{
        Cameriere,
        Accoglienza,
        Cucina,
        Bar,
        Forno
    }

    public enum useCases {
        AggiornaStatoTavolo,
        CreaOrdinazione,
        VisualizzaOrdinazioniForno,
        VisualizzaOrdinazioniCucina,
        VisualizzaOrdinazioniBar
    }

    public enum statesList {
        free,
        waitingForOrders,
        Occupied,
        reserved
    }

    public enum messages{
        menuRequest,
        cancelOrderRequest,
        cancelOrderedItemRequest,
        orderToTableGenerationRequest,
        orderNotification,
        tableRequest,
        freeTableRequest,
        userWaitingForOrderRequest,
        itemCompleteRequest,
        itemWorkingRequest,
        orderRequest,
        userWaitingNotification,
        loginRequest,
        registerNotification
    }
}
