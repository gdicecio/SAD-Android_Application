package com.lightingorder;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TableActivity extends AppCompatActivity {

    GridView tables_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_map);
        tables_view = (GridView) findViewById(R.id.tables);

        ArrayList<Table> tables = new ArrayList<Table>();
        tables.add(new Table("1","free"));
        tables.add(new Table("2","reserved"));
        tables.add(new Table("3","waitingForOrders"));
        tables.add(new Table("4","Occupied"));

        TablesAdapter tab_adap = new TablesAdapter(this, tables);
        tables_view.setAdapter(tab_adap);
    }
}
