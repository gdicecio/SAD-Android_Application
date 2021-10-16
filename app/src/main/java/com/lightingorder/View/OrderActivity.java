package com.lightingorder.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lightingorder.Controller.AppStateController;
import com.lightingorder.Controller.ConnectivityController;
import com.lightingorder.Controller.UserSessionController;
import com.lightingorder.Model.Data;
import com.lightingorder.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    Spinner prodotto;
    Spinner prodotto2;
    Spinner prodotto3;
    TextView numero_tavolo;
    TextView numero_sala;
    EditText priorita;
    UserSessionController user_contr = new UserSessionController();
    String tableID;
    int roomNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        String[] items = Data.getData().getMenuItemsName();

        tableID = getIntent().getStringExtra("tableID");
        roomNumber = getIntent().getIntExtra("roomNumber",1);

        prodotto = (Spinner) findViewById(R.id.prodotto);
        prodotto2 = (Spinner) findViewById(R.id.prodotto2);
        prodotto3= (Spinner) findViewById(R.id.prodotto3);
        numero_tavolo = (TextView) findViewById(R.id.table_number);
        numero_sala = (TextView) findViewById(R.id.room_number);
        priorita = (EditText) findViewById(R.id.priorita);
        numero_sala.setText(Integer.toString(roomNumber));
        numero_tavolo.setText(tableID);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        prodotto.setAdapter(adapter);
        prodotto2.setAdapter(adapter);
        prodotto3.setAdapter(adapter);
    }

    public void addOrder(View view){

        List<String> items_name = new ArrayList<>();
        if(!(prodotto.getSelectedItem().toString().equals("No selection")))
            items_name.add(prodotto.getSelectedItem().toString());

        if(!(prodotto2.getSelectedItem().toString().equals("No selection")))
            items_name.add(prodotto2.getSelectedItem().toString());

        if(!(prodotto3.getSelectedItem().toString().equals("No selection")))
            items_name.add(prodotto3.getSelectedItem().toString());

        List<List<String>> additive = Collections.<List<String>>emptyList();
        List<Integer> priority = new ArrayList<Integer>();
        priority.add(Integer.parseInt(priorita.getText().toString()));

        ConnectivityController.sendAddOrderToTableRequest(
                user_contr,
                user_contr.getCurrentProxy(),
                tableID,
                roomNumber,
                items_name,
                additive,
                priority);
        Log.d("ACTIVITY","ORDER ACTIVITY: Add order request sent");
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppStateController.getApplication().setCurrent_activity(this);
    }
}
