package com.lightingorder.View;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lightingorder.Controller.AppStateController;
import com.lightingorder.Controller.UserSessionController;
import com.lightingorder.R;
import com.lightingorder.StdTerms;
import com.lightingorder.Model.StateOp;
import com.lightingorder.View.Adapters.StateAdapter;
import com.lightingorder.Model.RestaurantArea.Table;
import com.lightingorder.View.Adapters.TablesAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {

    GridView tables_view;
    UserSessionController user_contr = new UserSessionController();

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ListView lv_states;
    private ArrayList<StateOp> operationsAvlbl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_map);
        tables_view = (GridView) findViewById(R.id.tables);

        String proxy_addr =  getIntent().getExtras().getString("proxy_corrente");
        String role =  getIntent().getExtras().getString("ruolo_corrente");
        String JSONtables = getIntent().getExtras().getString("JSONString_tavoli");

        /*TODO
            * Aggiunta tavoli alla gridview
        */
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ArrayList<Table> tables = new ArrayList<Table>();
        try {
            JSONArray j = new JSONArray(JSONtables);
            for(int i=0; i<j.length(); i++){
                Table single_table = gson.fromJson(j.get(i).toString(), Table.class);
             //   String order_json = j.getJSONObject(i).get("orders").toString();
             //   single_table.setOrderFromJson(order_json);
                tables.add(single_table);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



      /*  tables.add(new Table("1", "free"));
        tables.add(new Table("2", "reserved"));
        tables.add(new Table("3", "Occupied"));
        tables.add(new Table("4", "Occupied"));
        tables.add(new Table("5", "free"));
        tables.add(new Table("6", "free"));
        tables.add(new Table("7", "waitingForOrders"));
        tables.add(new Table("8", "Occupied"));
        tables.add(new Table("9", "free"));
        tables.add(new Table("10", "free"));*/

        TablesAdapter tab_adap = new TablesAdapter(this, tables);
        tables_view.setAdapter(tab_adap);

        tables_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Table tab = tables.get(position);

                if (role.equals(StdTerms.roles.Cameriere.name())) {
                    //TODO mostra la lista degli ordini del tavolo e nell'activity (SOLO SE il tavolo è occupato/in attesa).
                }
                else if(role.equals(StdTerms.roles.Accoglienza.name())) {
                    createNewContactDialog(role,proxy_addr,tab.tableID,tab.tableState);
                }
            }
        });
    }

    private void createNewContactDialog(String role, String proxy_addr, String tableID, String currentTableState){
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup, null);
        operationsAvlbl = new ArrayList<StateOp>();

        if(currentTableState.equals(StdTerms.statesList.free.name())){
            operationsAvlbl.add(new StateOp("Riserva tavolo", StdTerms.blue, StdTerms.statesList.reserved.name()));
            operationsAvlbl.add(new StateOp("Occupa tavolo", StdTerms.red, StdTerms.statesList.Occupied.name()));
        }
        else if(currentTableState.equals(StdTerms.statesList.reserved.name()) ||
                currentTableState.equals(StdTerms.statesList.Occupied.name()) ){
            operationsAvlbl.add(new StateOp("Libera tavolo", StdTerms.green, StdTerms.statesList.free.name()));
        }

        StateAdapter adapter = new StateAdapter(this, operationsAvlbl);
        lv_states = (ListView) contactPopupView.findViewById(R.id.states_list);
        lv_states.setAdapter(adapter);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        lv_states.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StateOp s = operationsAvlbl.get(position);



                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppStateController.getApplication().setCurrent_activity(this);

        //TODO riprenditi i dati dei tavoli
    }


}