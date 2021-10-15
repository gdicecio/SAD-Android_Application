package com.lightingorder.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lightingorder.R;

public class OrderActivity extends AppCompatActivity {

    Spinner prodotto;
    Spinner merce_aggiuntiva;
    Spinner merce_sottrativa;
    TextView numero_tavolo;
    TextView numero_sala;
    EditText priorita;

    String items[] = {"Margherita", "Napoletana"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        prodotto = (Spinner) findViewById(R.id.prodotto);
        merce_aggiuntiva = (Spinner) findViewById(R.id.merce_aggiuntiva);
        merce_sottrativa= (Spinner) findViewById(R.id.merce_sottrattiva);
        numero_tavolo = (TextView) findViewById(R.id.table_number);
        numero_sala = (TextView) findViewById(R.id.room_number);
        priorita = (EditText) findViewById(R.id.priorita);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        prodotto.setAdapter(adapter);
    }

    public void addOrder(View view){
        Toast.makeText(getApplicationContext(), prodotto.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
    }
}
