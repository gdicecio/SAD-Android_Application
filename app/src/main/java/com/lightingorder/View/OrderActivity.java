package com.lightingorder.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lightingorder.Controller.AppStateController;
import com.lightingorder.Controller.ConnectivityController;
import com.lightingorder.Controller.UserSessionController;
import com.lightingorder.R;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    Spinner prodotto;
    Spinner merce_aggiuntiva;
    Spinner merce_sottrativa;
    TextView numero_tavolo;
    TextView numero_sala;
    EditText priorita;
    UserSessionController user_contr = new UserSessionController();
    String tableID;
    int roomNumber;

    String items[] = {"Margherita", "Napoletana"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        tableID = getIntent().getStringExtra("tableID");
        roomNumber = getIntent().getIntExtra("roomNumber",1);

        prodotto = (Spinner) findViewById(R.id.prodotto);
        merce_aggiuntiva = (Spinner) findViewById(R.id.merce_aggiuntiva);
        merce_sottrativa= (Spinner) findViewById(R.id.merce_sottrattiva);
        numero_tavolo = (TextView) findViewById(R.id.table_number);
        numero_sala = (TextView) findViewById(R.id.room_number);
        priorita = (EditText) findViewById(R.id.priorita);
        numero_sala.setText(Integer.toString(roomNumber));
        numero_tavolo.setText(tableID);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        prodotto.setAdapter(adapter);
    }

    public void addOrder(View view){

        List<String> items = new ArrayList<>();
        items.add(prodotto.getSelectedItem().toString());

        List<Integer> priority = new ArrayList<Integer>();
        priority.add(Integer.parseInt(priorita.getText().toString()));

        ConnectivityController.sendAddOrderToTableRequest(
                AppStateController.getApplication().getCurrent_activity(),
                user_contr,
                user_contr.getCurrentProxy(),
                tableID,
                roomNumber,
                items,
                null,
                priority);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppStateController.getApplication().setCurrent_activity(this);
    }
}
